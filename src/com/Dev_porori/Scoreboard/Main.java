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
			warn(cfValue + "(이)가 " + checkint + " 미만이기 때문에 " + warnValue + "(으)로 설정됩니다.");
			delay.put(content, warnValue);
		}
		delay.put(content, cf.getInt(cfValue));
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
			warn("Vault 플러그인을 찾을수없습니다. [ 관련 홀더를 사용하지 못합니다. ]");
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			pl.put("papi", true);
			log("PlaceholderAPI 플러그인을 확인하였습니다. [ 관련 홀더를 사용할수 있습니다! ]");
		} else {
			pl.put("papi", false);
			warn("PlaceholderAPI 플러그인을 찾을수없습니다. [ 관련 홀더를 사용하지 못합니다. ]");
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
		log("플러그인이 활성화 되었습니다!");
		log("현재 버전 : " + getDescription().getVersion());
	}

}
