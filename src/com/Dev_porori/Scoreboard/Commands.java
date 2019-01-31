package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.Util.*;
import static com.Dev_porori.Scoreboard.ScoreboardController.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equals("prboard")) {
			if (args.length == 0) {
				sendMessage(sender, "/prboard toggle");
				if (sender.isOp())
					sendMessage(sender, "/prboard reload");
				return false;
			}
			if (args[0].equals("toggle")) {
				if (!(sender instanceof Player)) {
					sendMessage(sender, "플레이어만 사용할수있는 명령어입니다.");
					return false;
				}
				if (us.get((Player) sender)) {
					sendMessage(sender, "스코어보드가 비활성화 되었습니다.");
					toggleScoreboard((Player) sender, false);
					return false;
				}
				sendMessage(sender, "스코어보드가 활성화 되었습니다.");
				toggleScoreboard((Player) sender, true);
				return false;
			}
			if (args[0].equals("reload")) {
				if (!sender.hasPermission("PRScoreboard.reload")) {
					sendMessage(sender, "당신은 권한이 없습니다.");
					return false;
				}
				sendMessage(sender, "리로드 되었습니다.");
				Main.instance.reloadConfig();
				Main.cf = Main.instance.getConfig();
				return false;
			} else {
				sendMessage(sender, "알수없는 명령어입니다.");
				return false;
			}
		}
		return false;
	}

}
