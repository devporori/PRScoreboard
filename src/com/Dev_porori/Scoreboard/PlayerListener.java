package com.Dev_porori.Scoreboard;

import static com.Dev_porori.Scoreboard.ScoreboardController.*;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		us.put(e.getPlayer(), true);
		setScoreboard(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		us.remove(e.getPlayer());
	}

}
