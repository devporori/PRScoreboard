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
		log("플러그인이 활성화 되었습니다!");
		log("현재 버전 : " + getDescription().getVersion());
	}

	private void hook() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> EconomyProvider = (RegisteredServiceProvider<Economy>) getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (EconomyProvider != null) {
				eco = EconomyProvider.getProvider();
				pl.put("Vault", true);
				log("Vault 플러그인을 확인하였습니다. [ 관련된 홀더가 활성화 됩니다. ]");
			} else {
				pl.put("Vault", false);
				warn("Vault 플러그인을 사용하는 플러그인이 없습니다. [ 관련된 홀더가 비활성화 됩니다. ]");
			}
		} else {
			pl.put("Vault", false);
			warn("Vault 플러그인을 찾을수 없습니다. [ 관련된 홀더가 비활성화 됩니다. ]");
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			pl.put("papi", true);
			log("PlaceholderAPI 플러그인을 확인하였습니다. [ 관련된 홀더가 활성화 됩니다. ]");
		} else {
			pl.put("papi", false);
			warn("PlaceholderAPI 플러그인을 찾을수없습니다. [ 관련된 홀더가 비활성화 됩니다. ]");
		}
	}

	private void checkDelayTime(String content, String cfPath, int checkint, int warnValue) {
		if (cf.getInt(cfPath) < checkint) {
			warn(cfPath + "(이)가 " + checkint + " 미만이기 때문에 " + warnValue + "(으)로 설정됩니다.");
			delay.put(content, warnValue);
		}
		delay.put(content, cf.getInt(cfPath));
	}

}
