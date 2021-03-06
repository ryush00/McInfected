
package me.sniperzciinema.infected;

import java.util.ArrayList;
import java.util.Random;

import me.sniperzciinema.infected.Enums.Events;
import me.sniperzciinema.infected.Events.InfectedEndGame;
import me.sniperzciinema.infected.Extras.Pictures;
import me.sniperzciinema.infected.GameMechanics.KillStreaks;
import me.sniperzciinema.infected.GameMechanics.Settings;
import me.sniperzciinema.infected.GameMechanics.Stats;
import me.sniperzciinema.infected.Handlers.Lobby;
import me.sniperzciinema.infected.Handlers.Lobby.GameState;
import me.sniperzciinema.infected.Handlers.Player.InfPlayer;
import me.sniperzciinema.infected.Handlers.Player.InfPlayerManager;
import me.sniperzciinema.infected.Handlers.Player.Team;
import me.sniperzciinema.infected.Messages.Msgs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


public class Game {

	/**
	 * End the game and depending on who won, do things...
	 * 
	 * @param DidHumansWin
	 *            - Did the humans win
	 */
	public static void endGame(Boolean DidHumansWin) {

		Lobby.setGameState(GameState.GameOver);

		InfectedEndGame e = new InfectedEndGame(DidHumansWin);
		Bukkit.getPluginManager().callEvent(e);

		for (String name : Lobby.getInGame())
		{
			Player u = Bukkit.getPlayer(name);
			InfPlayer IP = InfPlayerManager.getInfPlayer(u);
			IP.getScoreBoard().showProperBoard();
			Stats.setPlayingTime(u.getName(), Stats.getPlayingTime(u.getName()) + InfPlayerManager.getInfPlayer(u).getPlayingTime());
			KillStreaks.handle(true, u);
		}
		if (DidHumansWin)
		{

			ArrayList<String> winners = new ArrayList<String>();

			for (String name : Lobby.getInGame())
			{
				Player u = Bukkit.getPlayer(name);
				InfPlayer IP = InfPlayerManager.getInfPlayer(u);

				Inventory IV = Bukkit.getServer().createInventory(null, InventoryType.PLAYER);
				for (ItemStack stack : IP.getInventory())
					if (stack != null)
						IV.addItem(stack);

				for (ItemStack is : Lobby.getActiveArena().getSettings().getRewordItems())
					IV.addItem(is);

				if (InfPlayerManager.getInfPlayer(u).isWinner())
				{
					if (Settings.VaultEnabled())
						Infected.economy.depositPlayer(u.getName(), Settings.getVaultReward());
					winners.add(u.getName());
				}

				Stats.setScore(u.getName(), Stats.getScore(u.getName()) + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.GameEnds));
				Stats.setPoints(u.getName(), Stats.getPoints(u.getName(), Settings.VaultEnabled()) + Lobby.getActiveArena().getSettings().getPointsPer(IP, Events.GameEnds), Settings.VaultEnabled());
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Header.getString("<title>", "Game Over"));
				u.sendMessage("");
				u.sendMessage(Msgs.Game_Over_Humans_Win.getString());
				StringBuilder winnersS = new StringBuilder();
				int i = 0;
				for (String s : winners)
				{
					i++;
					winnersS.append(s);
					if (i == winners.size())
						winnersS.append(".");
					else
						winnersS.append(", ");
				}
				u.sendMessage(Msgs.Game_Over_Winners.getString("<winners>", winnersS.toString()));
				u.sendMessage("");
				u.sendMessage(Lobby.getActiveArena().getName() == null ? "Really? You couldn't even wait for a map to be voted for?" : Msgs.Game_Info_Arena.getString("<arena>", Lobby.getActiveArena().getName(), "<creator>", Lobby.getActiveArena().getCreator()));
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Line.getString());
			}
		} else
		{
			for (String name : Lobby.getInGame())
			{
				Player u = Bukkit.getPlayer(name);
				InfPlayer IP = InfPlayerManager.getInfPlayer(u);

				Stats.setScore(u.getName(), Stats.getScore(u.getName()) + Lobby.getActiveArena().getSettings().getScorePer(IP, Events.GameEnds));
				Stats.setPoints(u.getName(), Stats.getPoints(u.getName(), Settings.VaultEnabled()) + Lobby.getActiveArena().getSettings().getPointsPer(IP, Events.GameEnds), Settings.VaultEnabled());
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Header.getString("<title>", "Game Over"));
				u.sendMessage("");
				u.sendMessage(Msgs.Game_Over_Zombies_Win.getString());
				u.sendMessage("");
				u.sendMessage(Msgs.Game_Info_Arena.getString("<arena>", Lobby.getActiveArena().getName(), "<creator>", Lobby.getActiveArena().getCreator()));
				u.sendMessage("");
				u.sendMessage(Msgs.Format_Line.getString());
			}
		}
		Lobby.reset();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Infected.me, new Runnable()
		{

			@Override
			public void run() {

				Lobby.setGameState(GameState.InLobby);
				for (String name : Lobby.getInGame())
					InfPlayerManager.getInfPlayer(name).tpToLobby();

				Bukkit.getScheduler().scheduleSyncDelayedTask(Infected.me, new Runnable()
				{

					@Override
					public void run() {

						if (Lobby.getInGame().size() >= Settings.getRequiredPlayers() && Lobby.getGameState() == GameState.InLobby)
							Lobby.timerStartVote();
					}
				}, 10 * 60);
			}
		}, 100L);

	}

	/**
	 * 
	 * @param p
	 *            - The Player
	 */
	public static void leaveGame(Player p) {
		InfPlayerManager.getInfPlayer(p).leaveInfected();
	}

	/**
	 * Choose the Alpha zombies(Apperently this feature doesn't work yet, will
	 * fix it when I get internet and can actually test on a live server)
	 * 
	 * TODO: Fix the choosing percent of Alpha Zombies
	 */
	public static void chooseAlphas() {

		int toInfect = 1;
		int inGame = Lobby.getInGame().size();
		float percent = Lobby.getActiveArena().getSettings().getAlphaPercent() / 100;
		toInfect = (int) (inGame * percent);
		if (toInfect == 0)
		{
			toInfect = 1;
		}
		String[] face = null;

		while (!(Lobby.getTeam(Team.Zombie).size() >= toInfect))
		{
			String alphaName = Lobby.getInGame().get(new Random().nextInt(Lobby.getInGame().size()));
			Player alpha = Bukkit.getPlayer(alphaName);
			if (Settings.PictureEnabled())
			{
				face = Pictures.getZombie();
				face[2] = face[2] + "     " + Msgs.Game_Alpha_You.getString();
				face[3] = face[3] + "     " + Msgs.Picture_Infected_To_Win.getString();

				alpha.sendMessage(face);
			} else
				alpha.sendMessage(Msgs.Game_Alpha_You.getString());

			InfPlayerManager.getInfPlayer(alpha).Infect();

			if (Settings.PictureEnabled())
			{
				face = Pictures.getHuman();
				face[2] = face[2] + "     " + Msgs.Picture_Survivor_You.getString();
				face[3] = face[3] + "     " + Msgs.Picture_Survivor_To_Win.getString();
			}
			for (String name : Lobby.getInGame())
				if (!name.equals(alphaName))
				{
					if (Settings.PictureEnabled())
						Bukkit.getPlayer(name).sendMessage(face);
					else
						Bukkit.getPlayer(name).sendMessage(Msgs.Game_Alpha_They.getString("<player>", alpha.getName()));
				}

			for (PotionEffect PE : Lobby.getActiveArena().getSettings().getAlphaPotionEffects())
				alpha.addPotionEffect(PE);
		}
		for (String name : Lobby.getInGame())
		{
			Player u = Bukkit.getPlayer(name);
			InfPlayer up = InfPlayerManager.getInfPlayer(u);
			up.getScoreBoard().showProperBoard();
		}
	}
}