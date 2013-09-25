package me.xxsniperzzxx_sd.infected.GameMechanics;

import java.util.Random;

import me.xxsniperzzxx_sd.infected.Infected;
import me.xxsniperzzxx_sd.infected.Main;
import me.xxsniperzzxx_sd.infected.Methods;
import me.xxsniperzzxx_sd.infected.Disguise.DisguisePlayer;
import me.xxsniperzzxx_sd.infected.Events.InfectedGameStartEvent;
import me.xxsniperzzxx_sd.infected.Events.InfectedVoteStartEvent;
import me.xxsniperzzxx_sd.infected.Main.GameState;
import me.xxsniperzzxx_sd.infected.Tools.Files;
import me.xxsniperzzxx_sd.infected.Tools.ItemHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Game {

	public static void START() {

		Main.Winners.clear();
		Infected.filesReloadArenas();
		Infected.arenaReset();
		
		for (Player playing : Bukkit.getServer().getOnlinePlayers())
			if (Main.inGame.contains(playing.getName()))
			{

				if (Main.config.getBoolean("ScoreBoard Support"))
				{
					ScoreBoard.updateScoreBoard();
				}
				playing.sendMessage(Methods.sendMessage("Vote_Time", null, Methods.getTime(Long.valueOf(Main.voteTime)), null));
				playing.sendMessage(Methods.sendMessage("Vote_HowToVote", null, null, null));

				Infected.filesReloadArenas();
				Main.possibleArenas.clear();

				for (String parenas : Infected.filesGetArenas().getConfigurationSection("Arenas").getKeys(true))
				{
					// Check if the string matchs an arena

					if (Main.possibleArenas.contains(parenas))
					{
						Main.possibleArenas.remove(parenas);
					}
					if (!parenas.contains("."))
					{
						Main.possibleArenas.add(parenas);
					}
					if (!Infected.filesGetArenas().contains("Arenas." + parenas + ".Spawns"))
					{
						Main.possibleArenas.remove(parenas);
					}
					if (!Infected.filesGetArenas().contains("Arenas." + parenas + ".Spawns") && !parenas.contains("."))
					{
						Main.possibleArenasU.add(parenas);
					}
				}

				StringBuilder possible = new StringBuilder();
				for (Object o : Main.possibleArenas)
				{
					possible.append(o.toString());
					if (Main.possibleArenas.size() > 1)
						possible.append(", ");
				}
				playing.sendMessage(Main.I + ChatColor.GRAY + "Arenas: " + ChatColor.GREEN + possible.toString());

				playing.sendMessage(Main.I + ChatColor.DARK_RED + "Possible Arenas: " + ChatColor.GOLD + possible.toString());
				playing.sendMessage(Main.I + ChatColor.YELLOW + "Or you could just vote for \"/Inf Vote Random\"");
				Main.Winners.add(playing.getName());
				ScoreBoard.updateScoreBoard();
			}

		// VOTEING TIME
		Bukkit.getServer().getPluginManager().callEvent(new InfectedVoteStartEvent(
				Main.inGame, Main.voteTime));

		Infected.setGameState(GameState.VOTING);
		Main.timeVote = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable()
		{
			int timeleft = Main.voteTime;

			boolean allVoted = false;
			boolean divided = false;
			@Override
			public void run() {
				if (timeleft != -1)
				{
					if(Main.Voted4.size() == Main.inGame.size())
						allVoted = true;
						if(allVoted && !divided){
							divided = true;
							timeleft /= 2 ;
							allVoted = false;
							for (Player playing : Bukkit.getServer().getOnlinePlayers())
								if (Main.inGame.contains(playing.getName()))
								{
									playing.playSound(playing.getLocation(), Sound.ORB_PICKUP, 1, 1);
									playing.sendMessage(Methods.sendMessage("Vote_TimeLeft", null, Methods.getTime(Long.valueOf(timeleft)), null));
								}
						}
						
					timeleft -= 1;
					Main.currentTime = timeleft;
					
					for (Player playing : Bukkit.getServer().getOnlinePlayers())
						if (Main.inGame.contains(playing.getName()))
							playing.setLevel(timeleft);

					if (timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2|| timeleft == 1)
					{
						for (Player playing : Bukkit.getServer().getOnlinePlayers())
						{
							if (Main.inGame.contains(playing.getName()))
							{
								playing.playSound(playing.getLocation(), Sound.NOTE_STICKS, 1, 1);
								playing.playSound(playing.getLocation(), Sound.NOTE_BASS_DRUM, 1, 1);
								playing.playSound(playing.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
							}
						}
					}
					if (timeleft == 0)
					{
						for (Player playing : Bukkit.getServer().getOnlinePlayers())
						{
							if (Main.inGame.contains(playing.getName()))
							{
								playing.playSound(playing.getLocation(), Sound.NOTE_STICKS, 1, 5);
								playing.playSound(playing.getLocation(), Sound.NOTE_BASS_DRUM, 1, 5);
								playing.playSound(playing.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 5);
							}
						}
					}
					if (timeleft == 60 || timeleft == 50 || timeleft == 40 || timeleft == 30 || timeleft == 20 || timeleft == 10 || timeleft == 9 || timeleft == 8 || timeleft == 7 || timeleft == 6 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
					{
						for (Player playing : Bukkit.getServer().getOnlinePlayers())
							if (Main.inGame.contains(playing.getName()))
							{
								playing.sendMessage(Methods.sendMessage("Vote_TimeLeft", null, Methods.getTime(Long.valueOf(timeleft)), null));
							}

					} else if (timeleft == -1)
					{
						Main.possibleArenas.clear();

						for (String parenas : Infected.filesGetArenas().getConfigurationSection("Arenas").getKeys(true))
						{
							// Check if the string matchs an arena

							if (Main.possibleArenas.contains(parenas))
							{
								Main.possibleArenas.remove(parenas);
							}
							if (!parenas.contains("."))
							{
								Main.possibleArenas.add(parenas);
							}
							if (!Infected.filesGetArenas().contains("Arenas." + parenas + ".Spawns"))
							{
								Main.possibleArenas.remove(parenas);
							}
							if (!Infected.filesGetArenas().contains("Arenas." + parenas + ".Spawns") && !parenas.contains("."))
							{
								Main.possibleArenasU.add(parenas);
							}
						}

						if (!Main.config.getBoolean("Allow Votes"))
						{
							if (Main.arenaNumber >= Main.possibleArenas.size())
								Main.arenaNumber = 0;
							Main.playingin = Main.possibleArenas.get(Main.arenaNumber);
							Main.arenaNumber++;
						} else if (Main.Votes.isEmpty())
						{
							Random r = new Random();
							int i = r.nextInt(Main.possibleArenas.size());
							Main.playingin = Main.possibleArenas.get(i);
						} else
						{
							// Determine the most voted for map
							Main.playingin = Methods.countdown(Main.Votes);
						}
						if (Main.playingin.equalsIgnoreCase("random"))
						{
							Main.possibleArenas.remove("random");
							Random r = new Random();
							int i = r.nextInt(Main.possibleArenas.size());
							Main.playingin = Main.possibleArenas.get(i);
						}
						for (Player p : Bukkit.getServer().getOnlinePlayers())
							if (Main.inGame.contains(p.getName()))
							{
								p.sendMessage(Main.I + ChatColor.GOLD + "Map: " + ChatColor.WHITE + Main.playingin);
								p.sendMessage(Main.I + "Game Starting in 5 Seconds.");
							}
						for (String loc : Infected.filesGetArenas().getStringList("Arenas." + Main.playingin + ".Spawns"))
						{
							String[] floc = loc.split(",");
							World world = Bukkit.getServer().getWorld(floc[0]);
							if (new Location(world, Integer.valueOf(floc[1]),
									Integer.valueOf(floc[2]),
									Integer.valueOf(floc[3])) == null)
							{
								
								for (Player p : Bukkit.getServer().getOnlinePlayers())
								{
									if (Main.inGame.contains(p.getName()))
									{
										p.sendMessage(Main.I + " The arena's location was unable to be found. Please get an admin to reset it.");
										Reset.tp2LobbyAfter(p);
									}

								}
							} else
							{
								Location Loc = new Location(world,
										Integer.valueOf(floc[1]),
										Integer.valueOf(floc[2]),
										Integer.valueOf(floc[3]));
								if (!Bukkit.getServer().getWorld(world.getName()).getChunkAt(Loc).isLoaded())
									Bukkit.getServer().getWorld(world.getName()).getChunkAt(Loc).load();
							}
						}
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable()
							{
								@Override
								public void run() {
									// Get Players in lobby setup
									Main.inLobby.clear();
									for (Player p : Bukkit.getServer().getOnlinePlayers())
									{
										if (Main.inGame.contains(p.getName()))
										{
											p.setHealth(20);
											p.setFoodLevel(20);
											p.sendMessage(Methods.sendMessage("Game_FirstInfectedIn", null, Methods.getTime(Long.valueOf(Main.Wait)), null));
											if (Main.config.getBoolean("ScoreBoard Support"))
											{
												ScoreBoard.updateScoreBoard();
											}
											Methods.respawn(p);
											Equip.equipHumans(p);
											if (Main.config.getBoolean("Allow Breaking Certain Blocks"))
												p.setGameMode(GameMode.SURVIVAL);
											else
												p.setGameMode(GameMode.ADVENTURE);
											if (!Main.KillStreaks.containsKey(p.getName()))
												Main.KillStreaks.put(p.getName(), Integer.valueOf("0"));
										}
									}
									Main.Voted4.clear();
									Main.Votes.clear();
									Infected.setGameState(GameState.BEFOREINFECTED);
									Main.timestart = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable()
									{
										int timeleft = Main.Wait;

										@Override
										public void run() {
											if (timeleft != -1)
											{
												timeleft -= 1;
												for (Player playing : Bukkit.getServer().getOnlinePlayers())
													if (Main.inGame.contains(playing.getName()))
														playing.setLevel(timeleft);
												
												Main.currentTime = timeleft;
												if (timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
												{
													for (Player playing : Bukkit.getServer().getOnlinePlayers())
													{
														if (Main.inGame.contains(playing.getName()))
														{
															playing.playSound(playing.getLocation(), Sound.NOTE_STICKS, 1, 1);
															playing.playSound(playing.getLocation(), Sound.NOTE_BASS_DRUM, 1, 1);
															playing.playSound(playing.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
														}
													}
												}
												if (timeleft == 0)
												{
													for (Player playing : Bukkit.getServer().getOnlinePlayers())
													{
														if (Main.inGame.contains(playing.getName()))
														{
															playing.playSound(playing.getLocation(), Sound.ZOMBIE_INFECT, 1, 5);
															playing.playSound(playing.getLocation(), Sound.NOTE_BASS_DRUM, 1, 1);
															playing.playSound(playing.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
														}
													}
												}
												if (timeleft == 60 || timeleft == 50 || timeleft == 40 || timeleft == 30 || timeleft == 20 || timeleft == 10 || timeleft == 9 || timeleft == 8 || timeleft == 7 || timeleft == 6 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
												{
													for (Player playing : Bukkit.getServer().getOnlinePlayers())
													{
														if (Main.inGame.contains(playing.getName()))
														{
															playing.sendMessage(Methods.sendMessage("Game_InfectionTimer", null, Methods.getTime(Long.valueOf(timeleft)), null));
														}
													}
												} else if (timeleft == -1)
												{
													// Choose the first infected

													Infected.setGameState(GameState.STARTED);
													for (Player players : Bukkit.getServer().getOnlinePlayers())
													{
														if (Main.inGame.contains(players.getName()))
														{
															if (!Main.Winners.contains(players.getName()))
															{
																Main.Winners.add(players.getName());
															}
															Main.Timein.put(players.getName(), System.currentTimeMillis() / 1000);
															Main.inLobby.remove(players.getName());
														}
													}
													Methods.newZombieSetUpEveryOne();
													Bukkit.getServer().getPluginManager().callEvent(new InfectedGameStartEvent(
															Main.inGame,
															Main.Wait,
															Bukkit.getPlayer(Main.zombies.get(0))));
													if (Main.config.getBoolean("Debug"))
													{
														System.out.println("humans: " + Main.humans.toString());
														System.out.println("zombies: " + Main.zombies.toString());
														System.out.println("InGame " + Main.inGame.toString());
													}
													// Set the game's time limit
													Main.timeLimit = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable()
													{
														int timeleft = Main.GtimeLimit;

														@Override
														public void run() {
															if (timeleft != -1)
															{
																if(Infected.getGameState() != GameState.GAMEOVER){
																timeleft -= 1;
																Main.currentTime = timeleft;
																}
																for (Player playing : Bukkit.getServer().getOnlinePlayers())
																	if (Main.inGame.contains(playing.getName()))
																		playing.setLevel(timeleft);
																
																if (Main.GtimeLimit - timeleft == 10)
																	for (final Player playing : Bukkit.getOnlinePlayers())
																	{
																		if (Main.inGame.contains(playing.getName()) || Main.inLobby.contains(playing.getName()))
																		{
																			playing.teleport(playing.getLocation());
																		}
																	}
																if (timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
																{
																	for (Player playing : Bukkit.getServer().getOnlinePlayers())
																	{
																		if (Main.inGame.contains(playing.getName()))
																		{
																			playing.playSound(playing.getLocation(), Sound.NOTE_STICKS, 1, 1);
																			playing.playSound(playing.getLocation(), Sound.NOTE_BASS_DRUM, 1, 1);
																			playing.playSound(playing.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
																		}
																	}
																}
																if (timeleft == 0)
																{
																	for (Player playing : Bukkit.getServer().getOnlinePlayers())
																	{
																		if (Main.inGame.contains(playing.getName()))
																		{
																			playing.playSound(playing.getLocation(), Sound.ORB_PICKUP, 1, 5);
																			playing.playSound(playing.getLocation(), Sound.LEVEL_UP, 1, 5);
																		}
																	}
																}
																if (timeleft == (Main.GtimeLimit / 4) * 3 || timeleft == Main.GtimeLimit / 2 || timeleft == Main.GtimeLimit / 4 || timeleft == 60 || timeleft == 10 || timeleft == 9 || timeleft == 8 || timeleft == 7 || timeleft == 6 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
																{
																	for (Player playing : Bukkit.getServer().getOnlinePlayers())
																	{
																		if (Main.inGame.contains(playing.getName()))
																			if (timeleft > 61)
																			{
																				playing.sendMessage(Methods.sendMessage("Game_TimeLeft", null, Methods.getTime(Long.valueOf(timeleft)), null));
																				playing.sendMessage(Main.I + ChatColor.GREEN + "Humans Left: " + ChatColor.YELLOW + Main.humans.size() + ChatColor.GREEN + " |" + ChatColor.RED + "| Total Zombies: " + ChatColor.YELLOW + Main.zombies.size());
																			} else
																			{
																				playing.sendMessage(Methods.sendMessage("Game_TimeLeft", null, Methods.getTime(Long.valueOf(timeleft)), null));
																			}
																	}
																} else if (timeleft == -1)
																{
																	if(Infected.getGameState() == GameState.STARTED)
																		endGame(true);

																}
															}
														}
													}, 0L, 20L);
												}
											}
										}
									}, 0L, 20L);
									
								}
							}, 100L);
					}
				}
			}
		}, 0L, 20L);
	}
	

	@SuppressWarnings("deprecation")
	public static void endGame(Boolean DidHumansWin) {
		if(Infected.getGameState() == GameState.STARTED){
			Infected.setGameState(GameState.GAMEOVER);
			
			for (Player players : Bukkit.getServer().getOnlinePlayers())
			{
				if (Infected.isPlayerInGame(players))
				{
					if (Main.KillStreaks.containsKey(players.getName()))
					{
						if (Main.KillStreaks.get(players.getName()) > Files.getPlayers().getInt("Players." + players.getName().toLowerCase() + ".KillStreak"))
						{
							Files.getPlayers().set("Players." + players.getName().toLowerCase() + ".KillStreak", Main.KillStreaks.get(players.getName()));
							Files.savePlayers();
						}
					}
				}
			}
			if (DidHumansWin)
			{
				if (Main.config.getBoolean("Vault Support.Enable"))
				{
					int rewardMoney = Main.config.getInt("Vault Support.Reward");
	
					for (Player players : Bukkit.getOnlinePlayers())
						if (Main.Winners.contains(players.getName()))
	
							Main.economy.depositPlayer(players.getName(), rewardMoney);
				}
				if (!(Main.config.getString("Command Reward").equalsIgnoreCase(null) || Main.config.getString("Command Reward").equalsIgnoreCase("[]")))
				{
					for (Player players : Bukkit.getOnlinePlayers())
					{
						if (Main.Winners.contains(players.getName()))
						{
							String s = Main.config.getString("Command Reward").replaceAll("<player>", players.getName());
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
						}
					}
				}
				for (String s : Main.config.getStringList("Rewards"))
				{
					for (Player players : Bukkit.getOnlinePlayers())
					{
						if (Main.Winners.contains(players.getName()))
						{
							players.getInventory().setContents(Main.Inventory.get(players.getName()));
							players.updateInventory();
							players.getInventory().addItem(ItemHandler.getItemStack(s));
							players.updateInventory();
							Main.Inventory.put(players.getName(), players.getInventory().getContents());
							Methods.resetPlayersInventory(players);
							players.updateInventory();
						}
					}
				}
				for (final Player players : Bukkit.getServer().getOnlinePlayers())
				{
					if (Main.inGame.contains(players.getName()))
					{
						Methods.rewardPointsAndScore(players, "Game Over");
						players.sendMessage(Methods.sendMessage("AfterGame_HumansWin", null, null, null));
						StringBuilder winners = new StringBuilder();
						for (Object o : Main.Winners)
						{
							winners.append(o.toString());
							winners.append(", ");
						}
						players.sendMessage(Main.I + "Winners: " + winners.toString());
						players.sendMessage(Main.I + "Total Points: " + Files.getPlayers().getInt("Players." + players.getName().toLowerCase() + ".Points"));
						Methods.SetOnlineTime(players);
						Files.savePlayers();
						if (Main.config.getBoolean("Disguise Support.Enabled"))
							if (DisguisePlayer.isPlayerDisguised(players))
								DisguisePlayer.unDisguisePlayer(players);

						for (PotionEffect reffect : players.getActivePotionEffects())
						{
							players.removePotionEffect(reffect.getType());
						}
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable()
						{
							@Override
							public void run() {
								Reset.tp2LobbyAfter(players);
							}
						}, 100L);
					}
				}
			} else
			{
				for (final Player players : Bukkit.getServer().getOnlinePlayers())
					if (Main.inGame.contains(players.getName()))
					{
						if (Main.config.getBoolean("Debug"))
							System.out.println(players.getName() + " KillStreaks: " + Main.KillStreaks.get(players.getName()));
						Methods.rewardPointsAndScore(players, "Game Over");
						Methods.SetOnlineTime(players);
						if (Main.config.getBoolean("Debug"))
						{
							System.out.println("Zombie Kills Human");
							System.out.println("Humans: " + Main.humans.toString());
							System.out.println("Zombies: " + Main.zombies.toString());
							System.out.println("InGame:" + Main.inGame.toString());
						}
						Files.savePlayers();
						players.sendMessage(Methods.sendMessage("AfterGame_ZombiesWin", null, null, null));
						players.sendMessage(Main.I + "Total Points: " + Files.getPlayers().getInt("Players." + players.getName().toLowerCase() + ".Points"));
						for (PotionEffect reffect : players.getActivePotionEffects())
							players.removePotionEffect(reffect.getType());
	
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable()
						{
							@Override
							public void run() {
								Reset.tp2LobbyAfter(players);
							}
						}, 100L);
					}
			}
			ScoreBoard.updateScoreBoard();
			Main.Winners.clear();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable()
			{
				@Override
				public void run() {
	
					Infected.setGameState(GameState.INLOBBY);
					if (Main.inGame.size() >= Main.config.getInt("Automatic Start.Minimum Players") && Infected.getGameState() == GameState.INLOBBY && Main.config.getBoolean("Automatic Start.Use"))
					{
						Game.restartGame();
					}
				}
			}, 10 * 60);
		}
	}


	public static void restartGame() {
		Bukkit.getScheduler().cancelTask(Main.timeVote);
		START();
	}
}