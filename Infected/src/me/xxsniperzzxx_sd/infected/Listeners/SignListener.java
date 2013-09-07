package me.xxsniperzzxx_sd.infected.Listeners;

import java.util.ArrayList;

import me.xxsniperzzxx_sd.infected.Infected;
import me.xxsniperzzxx_sd.infected.Main;
import me.xxsniperzzxx_sd.infected.Methods;
import me.xxsniperzzxx_sd.infected.Tools.Files;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@
SuppressWarnings("static-access")

public class SignListener implements Listener
{

	public Main Main = new Main();
	public ArrayList < String > item = new ArrayList < String > ();

	public Main plugin;
	public SignListener(Main instance)
	{
		this.plugin = instance;
	}




	@ EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerClickClassSign(PlayerInteractEvent event)
	{
		if(!event.isCancelled()){
			Player player = event.getPlayer();
			if (player.getItemInHand().getType() == Material.BOW)
			{
				event.setUseItemInHand(Result.DEFAULT);
				event.setCancelled(false);
			}
			else
			{

				if (Infected.isPlayerInLobby(player) || Infected.isPlayerInGame(player))
				{
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
					{
						Block b = event.getClickedBlock();
						if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
						{
							Sign sign = ((Sign) b.getState());
							if (sign.getLine(0).contains("[Infected]"))
							{
								if(sign.getLine(1).contains("Class"))
								{

									if(!plugin.getConfig().getBoolean("Class Support")) {
										player.sendMessage(Methods.sendMessage("Error_NoClassSupport", player, null, null));
									} else{
										String className = sign.getLine(2).replaceAll("�a", "");
										if(sign.getLine(3).contains("Human"))
										{
											if(className.equalsIgnoreCase("None"))
											{
												Main.humanClasses.remove(player.getName());
												player.sendMessage(Main.I+ChatColor.DARK_AQUA+"You no longer have a selected human class");
											}
											else if(player.hasPermission("Infected.Classes.Human") || player.hasPermission("Infected.Classes.Human."+className))
											{
												Main.humanClasses.put(player.getName(), className);
												player.sendMessage(Main.I+ChatColor.DARK_AQUA+"Your current human class is: "+sign.getLine(2));

											} else {
												player.sendMessage(Main.I + ChatColor.RED + "You don't have permission to buy this item!");
											}
										}
										else if(sign.getLine(3).contains("Zombie"))
										{
											if(className.equalsIgnoreCase("None"))
											{
												Main.zombieClasses.remove(player.getName());
												player.sendMessage(Main.I+ChatColor.DARK_AQUA+"You no longer have a selected zombie class");
											}
											else if(player.hasPermission("Infected.Classes.Zombie") || player.hasPermission("Infected.Classes.Zombie."+className))
											{
												Main.zombieClasses.put(player.getName(), className);
												player.sendMessage(Main.I+ChatColor.DARK_AQUA+"Your current zombie class is: "+sign.getLine(2));

											} else {
												player.sendMessage(Main.I + ChatColor.RED + "You don't have permission to buy this item!");
											}
										}
									}
								}
							}
							else if ((event.getClickedBlock().getTypeId() == 330 || event.getClickedBlock().getTypeId() == 96 || event.getClickedBlock().getTypeId() == 324 || event.getClickedBlock().getTypeId() == 69 || event.getClickedBlock().getTypeId() == 77 || event.getClickedBlock().getTypeId() == 143 || event.getClickedBlock().getTypeId() == 147 || event.getClickedBlock().getTypeId() == 148 || event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() == 72) && !Files.getGrenades().contains(String.valueOf(player.getItemInHand().getTypeId())) && !plugin.getConfig().getBoolean("Allow Interacting"))
							{
								event.setCancelled(true);
							}
						}
						else if ((event.getClickedBlock().getTypeId() == 330 || event.getClickedBlock().getTypeId() == 96 || event.getClickedBlock().getTypeId() == 324 || event.getClickedBlock().getTypeId() == 69 || event.getClickedBlock().getTypeId() == 77 || event.getClickedBlock().getTypeId() == 143 || event.getClickedBlock().getTypeId() == 147 || event.getClickedBlock().getTypeId() == 148 || event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() == 72) && !Files.getGrenades().contains(String.valueOf(player.getItemInHand().getTypeId())) && !plugin.getConfig().getBoolean("Allow Interacting"))
						{
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}



	@ SuppressWarnings("deprecation")
	@ EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerClickShopSign(PlayerInteractEvent event)
	{
		if(!event.isCancelled()){
			Player player = event.getPlayer();
			if (player.getItemInHand().getType() == Material.BOW)
			{
				event.setUseItemInHand(Result.DEFAULT);
				event.setCancelled(false);
			}
			else
			{

				if (Infected.isPlayerInLobby(player) || Infected.isPlayerInGame(player))
				{
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
					{
						Block b = event.getClickedBlock();
						if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
						{
							if (Files.getShop().getBoolean("Use"))
							{
								Sign sign = ((Sign) b.getState());
								if (sign.getLine(0).contains("[Infected]"))
								{
									String prices = sign.getLine(3).replaceAll("�4Cost: ", "");
									if(prices.contains(plugin.getConfig().getString("Vault Support.Symbol")) && plugin.getConfig().getBoolean("Vault Support.Enable")){
										///////////////////////////////////////////////////   VAULT MONEY SUPPORT
										

										int points = (int)plugin.economy.getBalance(player.getName());
										int price = Integer.valueOf(prices.replace(plugin.getConfig().getString("Vault Support.Symbol"), ""));
										String itemstring = sign.getLine(1).replaceAll("�f", "").replaceAll("�7", "");
										String itemname = null;
										short itemdata = 0;
										String s = itemstring;
										if (s.contains(":"))
										{
											String[] s1 = s.split(":");
											itemname = s1[0];
											itemdata = Short.valueOf(s1[1]);
										}
										else
										{
											itemname = s;
											itemdata = 0;
										}
										Material im = null;
										for (Material item: Material.values())
											if (item.toString().equalsIgnoreCase(itemname))
											{
												im = item;
												break;
											}
										if (im != null)
										{
											Material item = Material.getMaterial(itemname);
											if (player.hasPermission("Infected.Shop") || player.hasPermission("Infected.Shop." + item.getId()))
											{
												if (price < points + 1)
												{
													Main.economy.withdrawPlayer(player.getName(), price);
													ItemStack stack = new ItemStack(item);
													stack.setDurability(itemdata);
													if (!player.getInventory().contains(stack))
													{
														player.getInventory().addItem(stack);
														if (Files.getShop().getBoolean("Save Items")) {
															Infected.playerAddToShopInventory(player, stack);
														}
													} else {
														player.getInventory().addItem(new ItemStack(item, +1));
													}
													player.sendMessage(Main.I + ChatColor.DARK_AQUA + "You have bought a " + item);
													if (Files.getShop().getBoolean("Save Items") || Files.getShop().getIntegerList("Save These Items No Matter What").contains(item.getId())) {
														Infected.playerSaveShopInventory(player);
													}
												} else {
													player.sendMessage(Main.I + "Not enough points!");
												}
												Files.savePlayers();
												player.updateInventory();
											} else {
												player.sendMessage(Main.I + ChatColor.RED + "You don't have permission to buy this item!");
											}
										}
										else
										{
											if (Files.getShop().contains(itemname))
											{
												ItemStack is = Methods.getItemStack(Files.getShop().getString(itemname));
												for (Material items: Material.values())
												{
													if (items == is.getType())
													{
														im = items;
														break;
													}
												}
												if (price < points + 1)
												{
													if (player.hasPermission("Infected.Shop") || player.hasPermission("Infected.Shop." + itemname))
													{
														Main.economy.withdrawPlayer(player.getName(), price);
														ItemMeta i = is.getItemMeta();
														if (!player.getInventory().contains(is))
														{
															i.setDisplayName("�e" + itemname);
															is.setItemMeta(i);
															player.getInventory().addItem(is);
															if ((Files.getShop().getBoolean("Save Items") || Files.getShop().getIntegerList("Save These Items No Matter What").contains(is.getTypeId())) && (!Infected.filesGetGrenades().contains(String.valueOf(is.getTypeId())))) {
																Infected.playerSaveShopInventory(player);
															}
														}
														else
														{
															i.setDisplayName("�e" + itemname);
															is.setItemMeta(i);
															player.getInventory().addItem(is);
														}
														player.sendMessage(Main.I + ChatColor.DARK_AQUA + "You have bought a " + itemname);
														if (Files.getShop().getBoolean("Save Items") && (!Infected.filesGetGrenades().contains(String.valueOf(is.getTypeId())))) {
															Infected.playerSaveShopInventory(player);
														}
													}
												}
												else
												{
													player.sendMessage(Main.I + "Not enough points!");
												}
												Files.savePlayers();
												player.updateInventory();
												event.setCancelled(true);
											}
											Location loc = event.getClickedBlock().getLocation();
											if (Main.db.isSign(loc))
											{
												String i = Main.db.getSignsItem(loc);
												String itemi = null;
												short itemd = 0;
												if (i.contains(":"))
												{
													String[] i1 = i.split(":");
													itemi = i1[0];
													itemdata = Short.valueOf(i1[1]);
												}
												else
												{
													itemi = i;
													itemd = 0;
												}
												Material item = Material.getMaterial(Integer.valueOf(itemi));
												if (price < points + 1)
												{
													Main.economy.withdrawPlayer(player.getName(), price);
													ItemStack stack = new ItemStack(Material.getMaterial(Integer.valueOf(itemi)));
													stack.setDurability(itemd);
													if (!player.getInventory().contains(stack))
													{
														player.getInventory().addItem(stack);
														if (Files.getShop().getBoolean("Save Items") && (!Infected.filesGetGrenades().contains(String.valueOf(item.getId())))) {
															Infected.playerSaveShopInventory(player);
														}
													} else {
														player.getInventory().addItem(new ItemStack(item, +1));
													}
													player.sendMessage(Main.I + ChatColor.DARK_AQUA + "You have bought a " + item);
													if (Files.getShop().getBoolean("Save Items") && (!Infected.filesGetGrenades().contains(String.valueOf(item.getId())))) {
														Infected.playerSaveShopInventory(player);
													}

												} else {
													player.sendMessage(Main.I + "Not enough points!");
												}
												Files.savePlayers();
												player.updateInventory();
											}
										}
										
										
									}else{
										///////////////////////////////////////////////////////////////   REGULAR POINTS
										int points = Infected.playerGetPoints(player);
										int price = Integer.valueOf(prices);
										String itemstring = sign.getLine(1).replaceAll("�f", "").replaceAll("�7", "");
										String itemname = null;
										short itemdata = 0;
										String s = itemstring;
										if (s.contains(":"))
										{
											String[] s1 = s.split(":");
											itemname = s1[0];
											itemdata = Short.valueOf(s1[1]);
										}
										else
										{
											itemname = s;
											itemdata = 0;
										}
										Material im = null;
										for (Material item: Material.values())
											if (item.toString().equalsIgnoreCase(itemname))
											{
												im = item;
												break;
											}
										if (im != null)
										{
											Material item = Material.getMaterial(itemname);
											if (player.hasPermission("Infected.Shop") || player.hasPermission("Infected.Shop." + item.getId()))
											{
												if (price < points + 1)
												{
													Infected.playerSetPoints(player, points - price);
													ItemStack stack = new ItemStack(item);
													stack.setDurability(itemdata);
													if (!player.getInventory().contains(stack))
													{
														player.getInventory().addItem(stack);
														if (Files.getShop().getBoolean("Save Items")) {
															Infected.playerAddToShopInventory(player, stack);
														}
													} else {
														player.getInventory().addItem(new ItemStack(item, +1));
													}
													player.sendMessage(Main.I + ChatColor.DARK_AQUA + "You have bought a " + item);
													if (Files.getShop().getBoolean("Save Items") || Files.getShop().getIntegerList("Save These Items No Matter What").contains(item.getId())) {
														Infected.playerSaveShopInventory(player);
													}
												} else {
													player.sendMessage(Main.I + "Not enough points!");
												}
												Files.savePlayers();
												player.updateInventory();
											} else {
												player.sendMessage(Main.I + ChatColor.RED + "You don't have permission to buy this item!");
											}
										}
										else
										{
											if (Files.getShop().contains(itemname))
											{
												ItemStack is = Methods.getItemStack(Files.getShop().getString(itemname));
												for (Material items: Material.values())
												{
													if (items == is.getType())
													{
														im = items;
														break;
													}
												}
												if (price < points + 1)
												{
													if (player.hasPermission("Infected.Shop") || player.hasPermission("Infected.Shop." + itemname))
													{
														Infected.playerSetPoints(player, points - price);
														ItemMeta i = is.getItemMeta();
														if (!player.getInventory().contains(is))
														{
															i.setDisplayName("�e" + itemname);
															is.setItemMeta(i);
															player.getInventory().addItem(is);
															if ((Files.getShop().getBoolean("Save Items") || Files.getShop().getIntegerList("Save These Items No Matter What").contains(is.getTypeId())) && (!Infected.filesGetGrenades().contains(String.valueOf(is.getTypeId())))) {
																Infected.playerSaveShopInventory(player);
															}
														}
														else
														{
															i.setDisplayName("�e" + itemname);
															is.setItemMeta(i);
															player.getInventory().addItem(is);
														}
														player.sendMessage(Main.I + ChatColor.DARK_AQUA + "You have bought a " + itemname);
														if (Files.getShop().getBoolean("Save Items") && (!Infected.filesGetGrenades().contains(String.valueOf(is.getTypeId())))) {
															Infected.playerSaveShopInventory(player);
														}
													}
												}
												else
												{
													player.sendMessage(Main.I + "Not enough points!");
												}
												Files.savePlayers();
												player.updateInventory();
												event.setCancelled(true);
											}
											Location loc = event.getClickedBlock().getLocation();
											if (Main.db.isSign(loc))
											{
												String i = Main.db.getSignsItem(loc);
												String itemi = null;
												short itemd = 0;
												if (i.contains(":"))
												{
													String[] i1 = i.split(":");
													itemi = i1[0];
													itemdata = Short.valueOf(i1[1]);
												}
												else
												{
													itemi = i;
													itemd = 0;
												}
												Material item = Material.getMaterial(Integer.valueOf(itemi));
												if (price < points + 1)
												{
													Infected.playerSetPoints(player, points - price);
													ItemStack stack = new ItemStack(Material.getMaterial(Integer.valueOf(itemi)));
													stack.setDurability(itemd);
													if (!player.getInventory().contains(stack))
													{
														player.getInventory().addItem(stack);
														if (Files.getShop().getBoolean("Save Items") && (!Infected.filesGetGrenades().contains(String.valueOf(item.getId())))) {
															Infected.playerSaveShopInventory(player);
														}
													} else {
														player.getInventory().addItem(new ItemStack(item, +1));
													}
													player.sendMessage(Main.I + ChatColor.DARK_AQUA + "You have bought a " + item);
													if (Files.getShop().getBoolean("Save Items") && (!Infected.filesGetGrenades().contains(String.valueOf(item.getId())))) {
														Infected.playerSaveShopInventory(player);
													}
	
												} else {
													player.sendMessage(Main.I + "Not enough points!");
												}
												Files.savePlayers();
												player.updateInventory();
											}
										}
								}
							}
							else if ((event.getClickedBlock().getTypeId() == 330 || event.getClickedBlock().getTypeId() == 96 || event.getClickedBlock().getTypeId() == 324 || event.getClickedBlock().getTypeId() == 69 || event.getClickedBlock().getTypeId() == 77 || event.getClickedBlock().getTypeId() == 143 || event.getClickedBlock().getTypeId() == 147 || event.getClickedBlock().getTypeId() == 148 || event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() == 72) && !Files.getGrenades().contains(String.valueOf(player.getItemInHand().getTypeId())) && !plugin.getConfig().getBoolean("Allow Interacting"))
							{
								event.setCancelled(true);
							}
						}
						else if ((event.getClickedBlock().getTypeId() == 330 || event.getClickedBlock().getTypeId() == 96 || event.getClickedBlock().getTypeId() == 324 || event.getClickedBlock().getTypeId() == 69 || event.getClickedBlock().getTypeId() == 77 || event.getClickedBlock().getTypeId() == 143 || event.getClickedBlock().getTypeId() == 147 || event.getClickedBlock().getTypeId() == 148 || event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() == 72) && !Files.getGrenades().contains(String.valueOf(player.getItemInHand().getTypeId())) && !plugin.getConfig().getBoolean("Allow Interacting"))
						{
							event.setCancelled(true);
						}
					}
				}
			}
			}
		}
	}




	@ EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerClickCMDSign(PlayerInteractEvent event)
	{
		if(!event.isCancelled()){
			Player player = event.getPlayer();
			if (player.getItemInHand().getType() == Material.BOW)
			{
				event.setUseItemInHand(Result.DEFAULT);
				event.setCancelled(false);
			}
			else
			{
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					Block b = event.getClickedBlock();
					if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
					{
						Sign sign = ((Sign) b.getState());
						if (sign.getLine(0).contains("[Infected]") && sign.getLine(1).toLowerCase().contains("click to use"))
						{
							String command = sign.getLine(2).replaceAll("�6", "");
							String commandarg = sign.getLine(3).replaceAll("�6", "");
							event.getPlayer().performCommand("Infected " + command + " " + commandarg);
						}
					}
				}
			}
		}
	}






	//======================================================================================================================








































	@
	EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCreateShop(SignChangeEvent event)
	{
		if (!event.isCancelled())
		{
			if (Files.getShop().getBoolean("Use"))
			{
				Player player = event.getPlayer();
				if (event.getLine(0).equalsIgnoreCase("[Infected]") && !event.getLine(1).equalsIgnoreCase("Info") && !event.getLine(1).equalsIgnoreCase("cmd") && !event.getLine(1).equalsIgnoreCase("Class"))
				{
					if (!player.hasPermission("Infected.Setup"))
					{
						player.sendMessage(Main.I + "Invalid Permissions.");
						event.setCancelled(true);
					}

					if (event.getLine(1).isEmpty())
					{
						event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Line 2 is empty");
						event.getBlock().breakNaturally();
						event.setCancelled(true);
					}
					else if (event.getLine(2).isEmpty())
					{
						event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Line 3 is empty!");
						event.getBlock().breakNaturally();
						event.setCancelled(true);
					}
					else
					{
						if (Files.getShop().contains(event.getLine(1)))
						{
							String s = Files.getShop().getString(event.getLine(1));
							Material im = null;
							ItemStack is = new ItemStack(Methods.getItem(s));
							for (Material items: Material.values())
							{
								if (items == is.getType())
								{
									im = items;
									break;
								}
							}
							if (im != null)
							{
								String price = event.getLine(2);
								boolean vault = false;
								if(plugin.getConfig().getBoolean("Vault Support.Enable") && price.contains(plugin.getConfig().getString("Vault Support.Symbol")))
									vault = true;
								price = price.replaceAll(plugin.getConfig().getString("Vault Support.Symbol"), "");
								System.out.println(vault);
								try
								{
									Integer.valueOf(price);
								}
								catch (NumberFormatException nfe)
								{
									event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Cost must be a number!");
									event.getBlock().breakNaturally();
									event.setCancelled(true);
								}
								
								event.setLine(0, ChatColor.DARK_RED + "" + "[Infected]");
								event.setLine(1, ChatColor.GRAY + event.getLine(1));
								event.setLine(2, ChatColor.GREEN + "Click To Buy");
								if(vault)	event.setLine(3, ChatColor.DARK_RED + "Cost: " + plugin.getConfig().getString("Vault Support.Symbol") + price);
								else		event.setLine(3, ChatColor.DARK_RED + "Cost: " + price);
								
								}
						}
						else
						{
							try
							{
								String s = event.getLine(1);
								String[] s1 = s.split(":");
								@SuppressWarnings("unused")
								int itemid = Integer.valueOf(s1[0]);
							}
							catch (NumberFormatException nfe)
							{
								event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Invalid Item");
								event.getBlock().breakNaturally();
								event.setCancelled(true);
								return;
							}
							Material im = null;
							String itemid = null;
							String itemdata = null;
							String s = event.getLine(1);

							if (s.contains(":"))
							{
								String[] s1 = s.split(":");
								itemid = s1[0];
								itemdata = s1[1];
							}
							else
							{
								itemid = s;
								itemdata = "";
							}
							for (Material item: Material.values())
								if (item.getId() == Integer.valueOf(itemid))
								{
									im = item;
									break;
								}
							if (im != null)
							{
								boolean vault = false;
								String price = event.getLine(2);
								if(plugin.getConfig().getBoolean("Vault Support.Enable") && event.getLine(2).startsWith(plugin.getConfig().getString("Vault Support.Symbol")))
									vault = true;	
								price = price.replaceAll(plugin.getConfig().getString("Vault Support.Symbol"), "");
								try{
									Integer.valueOf(price);	
								}
								catch (NumberFormatException nfe)
								{
									event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Cost must be a number!");
									event.getBlock().breakNaturally();
									event.setCancelled(true);
								}
								Material item = Material.getMaterial(Integer.valueOf(itemid));
								event.setLine(0, ChatColor.DARK_RED + "" + "[Infected]");
								event.setLine(1, ChatColor.GRAY + item.name().toUpperCase() + ":" + itemdata);
								if (itemdata == "") {
									event.setLine(1, ChatColor.GRAY + item.name().toUpperCase());
								}
								event.setLine(2, ChatColor.GREEN + "Click To Buy");
								if(vault)	event.setLine(3, ChatColor.DARK_RED + "Cost: "+ plugin.getConfig().getString("Vault Support.Symbol") + price);
								else		event.setLine(3, ChatColor.DARK_RED + "Cost: " + price);
								if (itemdata == "")
								{
									itemdata = "0";
								}
								Main.db.setSigns(event.getBlock().getLocation(), itemid + ":" + Integer.valueOf(itemdata));
							}
						}
					}
				}
			}
		}
	}

	@
	EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCreateInfoSign(SignChangeEvent event)
	{
		if (!event.isCancelled())
		{

			Player player = event.getPlayer();
			if (event.getLine(0).equalsIgnoreCase("[Infected]") && event.getLine(1).equalsIgnoreCase("Info"))
			{
				if (!player.hasPermission("Infected.Setup"))
				{
					player.sendMessage(Main.I + "Invalid Permissions.");
					event.setCancelled(true);
				}
				if (event.getLine(1).isEmpty())
				{
					event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Line 2 is empty");
					event.getBlock().breakNaturally();
					event.setCancelled(true);
				}
				else
				{
						if (plugin.getConfig().getBoolean("Info Signs.Enabled"))
						{
							if (!player.hasPermission("Infected.Setup"))
							{
								player.sendMessage(Main.I + "Invalid Permissions.");
								event.setCancelled(true);
							}
							event.setLine(0, ChatColor.DARK_RED + "" + "[Infected]");
							String status;
							if (Infected.booleanIsBeforeGame()) {
								status = "Voting";
							}
							if (Infected.booleanIsBeforeInfected()) {
								status = "B4 Infected";
							}
							if (Infected.booleanIsStarted()) {
								status = "Started";
							} else {
								status = "In Lobby";
							}
							int time = Main.currentTime;
							event.setLine(1, ChatColor.GREEN + "Playing: " + ChatColor.DARK_GREEN + String.valueOf(Infected.listInGame().size()));
							event.setLine(2, ChatColor.GOLD + status);
							event.setLine(3, ChatColor.GRAY + "Time: " + ChatColor.YELLOW + String.valueOf(time));
							Main.db.setInfoSigns(event.getBlock().getLocation());
						}
				}
			}
		}
	}


	@
	EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCreateCMDSign(SignChangeEvent event)
	{
		if (!event.isCancelled())
		{

			Player player = event.getPlayer();
			if (event.getLine(0).equalsIgnoreCase("[Infected]") && event.getLine(1).equalsIgnoreCase("Cmd"))
			{
				if (!player.hasPermission("Infected.Setup"))
				{
					player.sendMessage(Main.I + "Invalid Permissions.");
					event.setCancelled(true);
				}
				if (event.getLine(1).isEmpty())
				{
					event.getPlayer().sendMessage(Main.I + ChatColor.RED + "Line 2 is empty");
					event.getBlock().breakNaturally();
					event.setCancelled(true);
				}
				else{
					event.setLine(0, ChatColor.DARK_RED + "" + "[Infected]");
					event.setLine(1, ChatColor.GREEN + "Click to use CMD");
					event.setLine(2, ChatColor.GOLD + event.getLine(2).toUpperCase());
					event.setLine(3, ChatColor.GOLD + event.getLine(3).toUpperCase());
					
				}
			}
		}
	}

	@
	EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCreateClassSign(SignChangeEvent event)
	{
		if (!event.isCancelled())
		{
			Player player = event.getPlayer();
			if (event.getLine(0).equalsIgnoreCase("[Infected]") && event.getLine(1).equalsIgnoreCase("Class"))
			{
				if (!player.hasPermission("Infected.Setup"))
				{
					player.sendMessage(Main.I + "Invalid Permissions.");
					event.setCancelled(true);
				}
					if(!plugin.getConfig().getBoolean("Class Support")) {
						player.sendMessage(Methods.sendMessage("Error_NoClassSupport", player, null, null));
					} else{
						if(event.getLine(3).equalsIgnoreCase("Zombie"))
						{
							if (Infected.filesGetClasses().getConfigurationSection("Classes.Zombie") == null)
							{
								player.sendMessage(plugin.I + ChatColor.RED + " Missing classes... wtf?");
								event.setCancelled(true);
								event.getBlock().breakNaturally();
							}
							boolean classFound = false;
							String className = "None";
							for (String classes: Infected.filesGetClasses().getConfigurationSection("Classes.Zombie").getKeys(true))
							{
								if((!classes.contains(".")) && event.getLine(2).equalsIgnoreCase(classes)){
									classFound = true;
									className = classes;
									break;
								}
							}
							if(classFound || event.getLine(2).equalsIgnoreCase("None")){

								event.setLine(0, ChatColor.DARK_RED + "" + "[Infected]");
								event.setLine(1, ChatColor.GRAY + "Class");
								event.setLine(2, ChatColor.GREEN + className);
								event.setLine(3, ChatColor.RED+"-> Zombie <-");
							}
						}
						else if(event.getLine(3).equalsIgnoreCase("Human"))
						{
							if (Infected.filesGetClasses().getConfigurationSection("Classes.Human") == null)
							{
								player.sendMessage(plugin.I + ChatColor.RED + " Missing classes... wtf?");
								event.setCancelled(true);
								event.getBlock().breakNaturally();
							}
							boolean classFound = false;
							String className = "None";
							for (String classes: Infected.filesGetClasses().getConfigurationSection("Classes.Human").getKeys(true))
							{

								if((!classes.contains(".")) && event.getLine(2).equalsIgnoreCase(classes)){
									classFound = true;
									className = classes;
									break;
								}
							}
							if(classFound || event.getLine(2).equalsIgnoreCase("None")){
								event.setLine(0, ChatColor.DARK_RED + "" + "[Infected]");
								event.setLine(1, ChatColor.GRAY + "Class");
								event.setLine(2, ChatColor.GREEN + className);
								event.setLine(3, ChatColor.DARK_GREEN+"-> Human <-");
							}
						}else{
							player.sendMessage(plugin.I + ChatColor.RED + " Well we managed to see you attempt to make a class sign, but thats not a class...");
							event.setCancelled(true);
							event.getBlock().breakNaturally();
						}
					}
				}

		}
	}
}