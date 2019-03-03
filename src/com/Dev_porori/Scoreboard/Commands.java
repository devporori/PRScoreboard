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
				if (!sender.hasPermission("PRScoreboard.help")) {
					sendMessage(sender, "����� ������ �����ϴ�.");
					return false;
				}
				sendMessage(sender, "/prboard toggle  ��8|  ��7���ھ�带 Ȱ��ȭ �ϰų� ��Ȱ��ȭ�մϴ�.");
				if (sender.hasPermission("PRScoreboard.reload"))
					sendMessage(sender, "/prboard reload  ��8|  ��7���Ǳ� ������ �ٽ� �ҷ��ɴϴ�.");
				return false;
			}
			if (args[0].equals("toggle")) {
				if (!sender.hasPermission("PRScoreboard.toggle")) {
					sendMessage(sender, "����� ������ �����ϴ�.");
					return false;
				}
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
				Main.instance.reloadcf(true);
				return false;
			} else {
				sendMessage(sender, "�˼����� ��ɾ��Դϴ�.");
				return false;
			}
		}
		return false;
	}

}
