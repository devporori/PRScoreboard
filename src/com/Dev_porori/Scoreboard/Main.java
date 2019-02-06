package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.Util.*;

import java.util.HashMap;

import static com.Dev_porori.Scoreboard.ScoreboardController.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
		hook();
		enableMessage();
		checkSystems();
		enableScoreboard();
	}

	private void hook() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			pl.put("vault", true);
			log("Vault 플러그인을 확인하였습니다. [ 관련 홀더를 사용할수 있습니다! ]");
			RegisteredServiceProvider<Economy> EconomyProvider = (RegisteredServiceProvider<Economy>) getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (EconomyProvider != null)
				eco = EconomyProvider.getProvider();

		} else {
			pl.put("vault", false);
			log("Vault 플러그인을 찾을수없습니다. [ 관련 홀더를 사용하지 못합니다. ]");
		}
	}

	private void checkSystems() {
		if (Bukkit.getOnlinePlayers().size() != 0)
			Bukkit.getOnlinePlayers().forEach(player -> toggleScoreboard(player, true));
		titlenum = 0;
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
