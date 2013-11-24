package me.sniperzciinema.infectedv2.Tools;

import java.util.HashMap;
import java.util.List;


public class Settings {

	public static boolean DisguisesEnabled(){
		return Files.getConfig().getBoolean("Addons.Disguise Support.Enabled");
	}
	public static boolean VaultEnabled(){
		return Files.getConfig().getBoolean("Addons.Vault Support.Enabled");
	}
	public static boolean mcMMOEnabled(){
		return Files.getConfig().getBoolean("Addons.mcMMO Support.Enabled");
	}
	public static boolean FactionsEnabled(){
		return Files.getConfig().getBoolean("Addons.Factions Support.Enabled");
	}
	public static boolean TagAPIEnabled(){
		return Files.getConfig().getBoolean("Addons.TagAPI Support.Enabled");
	}
	public static boolean CrackShotEnabled(){
		return Files.getConfig().getBoolean("Addons.CrackShot Support.Enabled");
	}
	public static int getRequiredPlayers(){
		return Files.getConfig().getInt("Settings.Misc.Automatic Start.Minimum Players");
	}
	public static boolean MySQLEnabled(){
		return Files.getConfig().getBoolean("MySQL.Enabled");
	}
	public static int InfoSignsUpdateTime(){
		return Files.getConfig().getInt("Settings.Misc.Info Signs.Refresh Time");
	}
	public static List<String> AllowedCommands(){
		return Files.getConfig().getStringList("Settings.Misc.Allowed Commands");
	}
	public static HashMap<String, Integer> getExtraVoteNodes(){
		HashMap<String, Integer> nodes = new HashMap<String, Integer>();
		
		for(String node : Files.getConfig().getConfigurationSection("Settings.Misc.Votes.Extra Votes").getKeys(true))
			if(!node.contains("."))
				nodes.put(node, Files.getConfig().getInt("Settings.Misc.Votes.Extra Votes"));
			
		return nodes;
	}
}