package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.Util.*;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.clip.placeholderapi.PlaceholderAPI;

public class ScoreboardController {

	public static int stop = -1;

	public static HashMap<Player, Boolean> us = new HashMap<>();
	public static HashMap<Player, Integer> tn = new HashMap<>();
	public static HashMap<String, Integer> delay = new HashMap<>();
	public static HashMap<Player, Integer> pdelay = new HashMap<>();
	public static List<String> titles;

	static void enableScoreboard() {
		stop = Main.instance.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(player -> {
					if (!us.get(player))
						return;
					setScoreboard(player);
				});
			}
		}, 0L, delay.get("scoreboard"));
	}

	static void setScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard b = m.getNewScoreboard();
		Objective o = b.registerNewObjective("playerinfo", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(Util.replaceColor(getTitle(player)));
		List<String> score = Main.cf.getStringList("scoreboard");
		Score s;
		for (int i = 0; i < score.size(); i++) {
			if (i >= 15)
				break;
			s = o.getScore(score.get(i).equals("") ? String.valueOf(ChatColor.values()[i])
					: setPlaceholder(score.get(i), player));
			s.setScore(14 - i);
		}
		player.setScoreboard(b);
	}

	static void toggleScoreboard(Player player, boolean useScoreboard) {
		us.put(player, useScoreboard);
		if (!useScoreboard)
			removeScoreboard(player);
	}

	private static String getTitle(Player player) {
		if (pdelay.get(player) < delay.get("title")) {
			pdelay.put(player, pdelay.get(player) + 1);
			return titles.get(tn.get(player));
		}
		if (tn.get(player) + 1 >= titles.size() || tn.get(player) < 0) {
			tn.put(player, 0);
			pdelay.put(player, 1);
		} else {
			tn.put(player, tn.get(player) + 1);
			pdelay.put(player, 1);
		}
		return titles.get(tn.get(player));
	}

	private static String setPlaceholder(String str, Player player) {
		return replaceColor(str.replaceAll("(?i)<playername>", player.getName())
				.replaceAll("(?i)<online>", String.valueOf(Bukkit.getOnlinePlayers().size()))
				.replaceAll("(?i)<maxplayer>", String.valueOf(Bukkit.getMaxPlayers()))
				.replaceAll("(?i)<ip>", player.getAddress().getAddress().getHostAddress())
				.replaceAll("(?i)<health>", String.valueOf(Math.floor(player.getHealth())))
				.replaceAll("(?i)<maxhealth>", String.valueOf(player.getHealthScale()))
				.replaceAll("(?i)<food>", String.valueOf(player.getFoodLevel()))
				.replaceAll("(?i)<level>", String.valueOf(player.getLevel()))
				.replaceAll("(?i)<loc:x>",
						getXloc(player) == -1 ? "-0"
								: getXloc(player) < 0 ? String.valueOf(getXloc(player) + 1)
										: String.valueOf(getXloc(player)))
				.replaceAll("(?i)<loc:y>", String.valueOf(Math.floor(player.getLocation().getY())))
				.replaceAll("(?i)<loc:z>", String.valueOf(Math.floor(player.getLocation().getZ())))
				.replaceAll("(?i)<world>", player.getWorld().getName())
				.replaceAll("(?i)<money>",
						Main.pl.get("vault") == true ? String.valueOf(Main.eco.getBalance(player)) : "vault가 필요합니다.")
				.replaceAll("<papi_%.+%>",
						Main.pl.get("papi") == true
								? PlaceholderAPI.setPlaceholders(player, str.replaceAll(".*<papi_|>.*", ""))
								: "papi가 필요합니다."));
	}

	private static double getXloc(Player player) {
		return Math.floor(player.getLocation().getX());
	}

}
