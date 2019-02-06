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

public class ScoreboardController {

	public static HashMap<Player, Boolean> us = new HashMap<>();
	public static HashMap<Player, Integer> tn = new HashMap<>();

	static void enableScoreboard() {
		Main.instance.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(player -> {
					if (!us.get(player))
						return;
					setScoreboard(player);
				});
			}
		}, 0L, 3L);
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
		List<String> titles = Main.cf.getStringList("title");
		if (tn.get(player) + 1 >= titles.size() || tn.get(player) < 0)
			tn.put(player, 0);
		else
			tn.put(player, tn.get(player) + 1);
		return titles.get(tn.get(player));
	}

	private static String setPlaceholder(String str, Player player) {
		return replaceColor(str.replace("<playername>", player.getName())
				.replace("<online>", String.valueOf(Bukkit.getOnlinePlayers().size()))
				.replace("<maxplayer>", String.valueOf(Bukkit.getMaxPlayers()))
				.replace("<ip>", player.getAddress().getAddress().getHostAddress())
				.replace("<health>", String.valueOf(Math.floor(player.getHealth())))
				.replace("<maxhealth>", String.valueOf(player.getHealthScale()))
				.replace("<food>", String.valueOf(player.getFoodLevel()))
				.replace("<level>", String.valueOf(player.getLevel()))
				.replace("<loc:x>",
						getXloc(player) == -1 ? "-0.0"
								: getXloc(player) < 0 ? String.valueOf(getXloc(player) + 1)
										: String.valueOf(getXloc(player)))
				.replace("<loc:y>", String.valueOf(Math.floor(player.getLocation().getY())))
				.replace("<loc:z>", String.valueOf(Math.floor(player.getLocation().getZ())))
				.replace("<world>", player.getWorld().getName()).replace("<money>",
						Main.pl.get("vault") == true ? String.valueOf(Main.eco.getBalance(player)) : "vault가 필요합니다."));
	}

	private static double getXloc(Player player) {
		return Math.floor(player.getLocation().getX());
	}

}
