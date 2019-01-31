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
					sendMessage(sender, "�÷��̾ ����Ҽ��ִ� ��ɾ��Դϴ�.");
					return false;
				}
				if (us.get((Player) sender)) {
					sendMessage(sender, "���ھ�尡 ��Ȱ��ȭ �Ǿ����ϴ�.");
					toggleScoreboard((Player) sender, false);
					return false;
				}
				sendMessage(sender, "���ھ�尡 Ȱ��ȭ �Ǿ����ϴ�.");
				toggleScoreboard((Player) sender, true);
				return false;
			}
			if (args[0].equals("reload")) {
				if (!sender.hasPermission("PRScoreboard.reload")) {
					sendMessage(sender, "����� ������ �����ϴ�.");
					return false;
				}
				sendMessage(sender, "���ε� �Ǿ����ϴ�.");
				Main.instance.reloadConfig();
				Main.cf = Main.instance.getConfig();
				return false;
			} else {
				sendMessage(sender, "�˼����� ��ɾ��Դϴ�.");
				return false;
			}
		}
		return false;
	}

}
