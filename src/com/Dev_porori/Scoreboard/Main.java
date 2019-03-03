package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.ScoreboardController.*;
import static com.Dev_porori.Scoreboard.Util.*;

import java.util.HashMap;
import java.util.Map;

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

	static Map<String, Boolean> pl = new HashMap<>();

	public void onEnable() {
		instance = this;
		createConfig();
		registerEvents();
		setExecutors();
		enableMessage();
		hook();
		reloadcf(false);
		enableScoreboard();
	}

	public void reloadcf(boolean enable) {
		if (enable) {
			Bukkit.getScheduler().cancelTask(stop);
			reloadConfig();
			cf = getConfig();
		}
		if (Bukkit.getOnlinePlayers().size() != 0)
			for (Player p : Bukkit.getOnlinePlayers()) {
				tn.put(p, 0);
				pdelay.put(p, 1);
				if (!enable) 
					us.put(p, true);
			}
		titles = cf.getStringList("title.contents");
		checkDelayTime("scoreboard", "loadTime", 1, 1);
		checkDelayTime("title", "title.delay", 1, 1);
		enableScoreboard();
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

	private void hook() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> EconomyProvider = (RegisteredServiceProvider<Economy>) getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (EconomyProvider != null) {
				eco = EconomyProvider.getProvider();
				pl.put("Vault", true);
				log("Vault �÷������� Ȯ���Ͽ����ϴ�. [ ���õ� Ȧ���� Ȱ��ȭ �˴ϴ�. ]");
			} else {
				pl.put("Vault", false);
				warn("Vault �÷������� ����ϴ� �÷������� �����ϴ�. [ ���õ� Ȧ���� ��Ȱ��ȭ �˴ϴ�. ]");
			}
		} else {
			pl.put("Vault", false);
			warn("Vault �÷������� ã���� �����ϴ�. [ ���õ� Ȧ���� ��Ȱ��ȭ �˴ϴ�. ]");
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			pl.put("papi", true);
			log("PlaceholderAPI �÷������� Ȯ���Ͽ����ϴ�. [ ���õ� Ȧ���� Ȱ��ȭ �˴ϴ�. ]");
		} else {
			pl.put("papi", false);
			warn("PlaceholderAPI �÷������� ã���������ϴ�. [ ���õ� Ȧ���� ��Ȱ��ȭ �˴ϴ�. ]");
		}
	}

	private void checkDelayTime(String content, String cfPath, int checkint, int warnValue) {
		if (cf.getInt(cfPath) < checkint) {
			warn(cfPath + "(��)�� " + checkint + " �̸��̱� ������ " + warnValue + "(��)�� �����˴ϴ�.");
			delay.put(content, warnValue);
		}
		delay.put(content, cf.getInt(cfPath));
	}

}
