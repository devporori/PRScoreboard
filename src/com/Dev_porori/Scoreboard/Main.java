package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.Util.*;
import static com.Dev_porori.Scoreboard.ScoreboardController.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main instance;
	public static FileConfiguration cf;

	public void onEnable() {
		instance = this;
		createConfig();
		registerEvents();
		setExecutors();
		enableMessage();
		checkPlayers();
		enableScoreboard();
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
		log("플러그인이 활성화 되었습니다!");
		log("현재 버전 : " + getDescription().getVersion());
	}

}
