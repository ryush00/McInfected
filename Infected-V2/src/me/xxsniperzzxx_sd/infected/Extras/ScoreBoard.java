package me.xxsniperzzxx_sd.infected.Extras;

import me.xxsniperzzxx_sd.infected.Infected;
import me.xxsniperzzxx_sd.infected.Main;
import me.xxsniperzzxx_sd.infected.Handlers.Lobby.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public class ScoreBoard {


	public static void updateScoreBoard() {

		if (Main.config.getBoolean("ScoreBoard Support"))
		{

			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard infectedBoard = manager.getNewScoreboard();
			
			Objective infectedList = infectedBoard.registerNewObjective("InfectedBoard", "dummy");
			infectedList.setDisplaySlot(DisplaySlot.SIDEBAR);

			if (Main.Lobby.getGameState() == GameState.Started || Main.Lobby.getGameState() == GameState.Infecting)
			{
				infectedList.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Teams");
				Score score = infectedList.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + ChatColor.ITALIC + "Humans:"));
				if (Main.humans.size() != 0)
					score.setScore(Main.humans.size());
				else
				{
					score.setScore(1);
					score.setScore(0);
				}
				Score score2 = infectedList.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + ChatColor.ITALIC + "Zombies:"));
				if (Main.zombies.size() != 0)
					score2.setScore(Main.zombies.size());
				else
				{
					score2.setScore(1);
					score2.setScore(0);
				}
				
			} 
			else if(Main.Lobby.getGameState() == GameState.InLobby || Main.Lobby.getGameState() == GameState.Voting)
			{
				infectedList.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Vote for your map!");
				Main.possibleArenas.clear();
				for (String parenas : Infected.filesGetArenas().getConfigurationSection("Arenas").getKeys(true))
				{
					// Check if the string matchs an arena

					if (Main.possibleArenas.contains(parenas))
					{
						Main.possibleArenas.remove(parenas);
					}
					if (!Infected.filesGetArenas().contains("Arenas." + parenas + ".Spawns"))
					{
						Main.possibleArenas.remove(parenas);
					} else if (!parenas.contains("."))
					{
						Score score;
						if(parenas.equalsIgnoreCase(Main.playingin))
							score = infectedList.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD +">"+ parenas));
						else
							score = infectedList.getScore(Bukkit.getOfflinePlayer(""+ChatColor.YELLOW + ChatColor.ITALIC + parenas));
						
							
						if (Main.Votes.get(parenas) != null)
							score.setScore(Main.Votes.get(parenas));
						else
						{
							score.setScore(1);
							score.setScore(0);
						}

					}
				}
			}
			for (String s : Infected.listInGame())
			{
				Player player = Bukkit.getPlayer(s);
				player.setScoreboard(infectedBoard);
			}

		}
	}

}