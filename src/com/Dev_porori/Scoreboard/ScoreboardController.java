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
		}, 0L, (20 * Main.cf.getLong("loadTime")));
	}

	static void setScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard b = m.getNewScoreboard();
		Objective o = b.registerNewObjective("playerinfo", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(Util.replaceColor(Main.cf.getString("title")));
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

	private static String setPlaceholder(String str, Player player) {
		return replaceColor(str.replace("<playername>", player.getName())
				.replace("<online>", String.valueOf(Bukkit.getOnlinePlayers().size()))
				.replace("<health>", String.valueOf(player.getHealth()))
				.replace("<maxhealth>", String.valueOf(player.getHealthScale()))
				.replace("<food>", String.valueOf(player.getFoodLevel()))
				.replace("<level>", String.valueOf(player.getLevel()))
				.replace("<loc:x>", String.valueOf(player.getLocation().getX()))
				.replace("<loc:y>", String.valueOf(player.getLocation().getY()))
				.replace("<loc:z>", String.valueOf(player.getLocation().getZ())));
	}

}
