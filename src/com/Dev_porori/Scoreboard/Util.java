package com.Dev_porori.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class Util {

	public static final String PREFIX = "」b[ 」fPRScoreboard 」b] 」f";
	public static final String INFO = "」b[ 」fPRScoreboard Info 」b] 」f"; 
	public static final String WARN = "」b[ 」fPRScoreboard Warn 」b] 」f";

	public static String replaceColor(String s) {
		return s.replace("&1", "」1").replace("&2", "」2").replace("&3", "」3").replace("&4", "」4").replace("&5", "」5")
				.replace("&6", "」6").replace("&7", "」7").replace("&8", "」8").replace("&9", "」9").replace("&0", "」0")
				.replace("&a", "」a").replace("&b", "」b").replace("&c", "」c").replace("&d", "」d").replace("&e", "」e")
				.replace("&f", "」f").replace("&r", "」r").replace("&k", "」k").replace("&l", "」l").replace("&m", "」m")
				.replace("&n", "」n").replace("&o", "」o");
	}

	public static void log(String log) {
		Bukkit.getConsoleSender().sendMessage(INFO + log);
	}

	public static void warn(String log) {
		Bukkit.getConsoleSender().sendMessage(WARN + log);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(PREFIX + message);
	}

	public static void removeScoreboard(Player player) {
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}

}
