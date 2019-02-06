package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.Util.*;

import java.util.HashMap;

import static com.Dev_porori.Scoreboard.ScoreboardController.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main instance;
	public static FileConfiguration cf;

	static HashMap<String, Boolean> pl = new HashMap<>();

	public void onEnable() {
		instance = this;
		createConfig();
		registerEvents();
		setExecutors();
		hook();
		enableMessage();
		checkPlayers();
		enableScoreboard();
	}

	private void hook() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			pl.put("vault", true);
			log("Vault �÷������� Ȯ���Ͽ����ϴ�. [ ���� Ȧ���� ����Ҽ� �ֽ��ϴ�! ]");
		} else {
			pl.put("vault", false);
			log("Vault �÷������� ã���������ϴ�. [ ���� Ȧ���� ������� ���մϴ�. ]");
		}
	}

	private void checkPlayers() {
		if (Bukkit.getOnlinePlayers().size() != 0)
			Bukkit.getOnlinePlayers().forEach(player -> toggleScoreboard(player, true));
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
