package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.Util.*;

import java.util.HashMap;

import static com.Dev_porori.Scoreboard.ScoreboardController.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static Main instance;
	public static FileConfiguration cf;
	public static Economy eco;

	static HashMap<String, Boolean> pl = new HashMap<>();

	public void onEnable() {
		instance = this;
		createConfig();
		registerEvents();
		setExecutors();
		enableMessage();
		hook();
		setupSystems();
		enableScoreboard();
	}

	public void reloadcf() {
		reloadConfig();
		cf = getConfig();
		for (Player p : Bukkit.getOnlinePlayers()) {
			tn.put(p, 0);
			pdelay.put(p, 1);
		}
		titles = cf.getStringList("title.contents");
		checkbelowDelayTime("scoreboard", "loadTime", 1, 1);
		checkbelowDelayTime("title", "title.delay", 1, 1);
		Bukkit.getScheduler().cancelTask(stop);
		enableScoreboard();
	}

	private void checkbelowDelayTime(String content, String cfValue, int checkint, int warnValue) {
		if (cf.getInt(cfValue) < checkint) {
			warn(cfValue + "(��)�� " + checkint + " �̸��̱� ������ " + warnValue + "(��)�� �����˴ϴ�.");
			delay.put(content, warnValue);
		}
		delay.put(content, cf.getInt(cfValue));
	}

	private void hook() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			pl.put("vault", true);
			log("Vault �÷������� Ȯ���Ͽ����ϴ�. [ ���� Ȧ���� ����Ҽ� �ֽ��ϴ�! ]");
			RegisteredServiceProvider<Economy> EconomyProvider = (RegisteredServiceProvider<Economy>) getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (EconomyProvider != null)
				eco = EconomyProvider.getProvider();

		} else {
			pl.put("vault", false);
			warn("Vault �÷������� ã���������ϴ�. [ ���� Ȧ���� ������� ���մϴ�. ]");
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			pl.put("papi", true);
			log("PlaceholderAPI �÷������� Ȯ���Ͽ����ϴ�. [ ���� Ȧ���� ����Ҽ� �ֽ��ϴ�! ]");
		} else {
			pl.put("papi", false);
			warn("PlaceholderAPI �÷������� ã���������ϴ�. [ ���� Ȧ���� ������� ���մϴ�. ]");
		}
	}

	private void setupSystems() {
		if (Bukkit.getOnlinePlayers().size() != 0)
			for (Player p : Bukkit.getOnlinePlayers()) {
				toggleScoreboard(p, true);
				tn.put(p, 0);
				pdelay.put(p, 1);
			}
		titles = cf.getStringList("title.contents");
		checkbelowDelayTime("scoreboard", "loadTime", 1, 1);
		checkbelowDelayTime("title", "title.delay", 1, 1);
	}

	private void createConfig() {
		saveDefaultConfig();
		cf = getConfig();
	}

	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	private void setExecutors() {
		getCommand("prboard").setExecutor(new Commands());
	}

	private void enableMessage() {
		log("�÷������� Ȱ��ȭ �Ǿ����ϴ�!");
		log("���� ���� : " + getDescription().getVersion());
	}

}
