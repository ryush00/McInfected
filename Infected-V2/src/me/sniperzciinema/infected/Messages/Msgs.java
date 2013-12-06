
package me.sniperzciinema.infected.Messages;

import me.sniperzciinema.infected.Tools.Files;

import org.bukkit.ChatColor;


public enum Msgs
{
	// List of all Messages, and beside are some variables for them.
	Format_Header("Format.Header"),	// <title>>
	Format_Line("Format.Line"), 
	Format_Prefix("Format.Prefix"),
	Format_List("Format.List")/*<player>*/,
	Format_InfChat("Format.InfChat")/*<team>, <player>, <message>*/,
	Format_Grenades_List("Format.Grenades List")/*<id>, <name>, <cost>*/,
	Command_Arena_List("Command.Arena.List")/*<valid>, <invalid>*/,
	Command_Arena_Created("Command.Arena.Created")/*<arena>*/,
	Command_Arena_Removed("CommandArena.Removed")/*<arena>*/,
	Command_Arena_Set("Command.Arena.Set")/*<arena>*/,
	Command_Lobby_Set("Command.Lobby.Set"),
	Command_Lobby_Tp("Command.Lobby.Tp"),
	Command_Spawn_Set("Command.Spawn.Set")/*<spawn>*/,
	Command_Spawn_Tp("Command.Spawn.Tp")/*<spawn>*/,
	Command_Spawn_Spawns("Command.Spawn.Spawns")/*<spawns>*/,
	Command_Spawn_Deleted("Command.Spawn.Deleted")/*<spawn>*/,
	Command_Vote("Command.Vote")/*<player>, <arena>*/,
	Command_InfChat("Command.InfChat")/*<state>*/,
	Command_Info_Players("Command.Info.Players")/*<players>*/,
	Command_Info_State("Command.Info.State")/*<state>*/,
	Command_Info_Time_Left("Command.Info.Time Left")/*<time>*/,
	Command_Admin_Shutdown("Command.Admin.Shutdown")/*<state>*/,
	Command_Admin_Reload("Command.Admin.Reload"),
	Command_Admin_Kicked_You("Command.Admin.Kicked.You"),
	Command_Admin_Kicked_Them("Command.Admin.Kicked.Them")/*<player>*/,
	Command_Admin_Changed_Stat("Command.Admin.Changed Stat")/*<player>, <stat>, <value>*/,
	Command_Files_Value("Command.Files.Value")/*<path> <value>*/,
	Command_Files_Changed("Command.Files.Changed")/*<path> <newvalue> <value>*/,
	Error_Misc_No_Permission("Error.Misc.No Permission"),
	Error_Misc_Plugin_Unloaded("Error.Misc.Plugin Unloaded"), 
	Error_Misc_Plugin_Disabled("Error.Misc.Plugin Disabled"), 
	Error_Misc_Use_Command("Error.Misc.Cant Use Command"),
	Error_Misc_Not_Player("Error.Misc.Not Player"), 
	Error_Misc_Unkown_Command("Error.Misc.Unkown Command"), 
	Error_Misc_Not_A_File("Error.Misc.Not A File")/*<files>*/,
	Error_Game_Started("Error.Game.Started"),
	Error_Game_Not_Started("Error.Game.Not Started"),
	Error_Game_Not_In("Error.Game.Not In"),
	Error_Game_In("Error.Game.In"), 
	Error_Top_Not_Stat("Error.Top.Not Stat")/*<stats>*/, 
	Error_Lobby_Doesnt_Exist("Error.Lobby.Doesnt Exist"),
	Error_Arena_Doesnt_Exist("Error.Arena.Doesnt Exist")/*<arena>*/,
	Error_Arena_Not_Valid("Error.Arena.Not Valid")/*<arena>*/,
	Error_Arena_Already_Exists("Error.Arena.Already Exist"), 
	Error_Arena_No_Spawns("Error.Arena.No Spawns"),
	Error_Sign_Not_Valid("Error.Sign.Not Valid"),
	Error_Already_Voted("Error.Already Voted"),
	Menu_Classes_None("Menu.Classes.None"),
	Menu_Classes_Click_To_Choose("Menu.Classes.Click To Choose"),
	Menu_Team_Choose("Menu.Team.Choose")/*<team>*/,
	Menu_Vote_Choose("Menu.Vote.Choose"), 
	Menu_Vote_Random("Menu.Vote.Random"), 
	Menu_Shop_Click_To_Buy("Menu.Shop.Click To Buy") /*<Cost>*/,
	Shop_Bought_Item("Shop.Bought Item")/*<item>*/,
	Shop_Cost_Not_Enough("Shop.Cost.Not Enough"),
	Shop_Cost_Needed("Shop.Cost.Needed") /*<needed>*/,
	Game_KillStreak_Value("Game.KillStreak.Value")/*<player>, <killstreak>*/,
	Game_KillStreak_Reward("Game.KillStreak.Reward")/*<item>*/,
	Game_Time_Left_Voting("Game.Time Left.Voting")/*<time>*/,
	Game_Time_Left_Infecting("Game.Time Left.Infecting")/*<time>*/,
	Game_Time_Left_Game("Game.Time Left.Game")/*<time>*/,
	Game_Death_Before_Game("Game.Death.Before Game"),
	Game_Over_Humans_Win("Game.Over.Humans Win"),
	Game_Over_Winners("Game.Over.Winners")/*<winners>*/,
	Game_Over_Zombies_Win("Game.Over.Zombies Win"),
	Game_Players_Left("Game.Players Left")/*<humans>, <zombies>*/,
	Game_Alpha_You("Game.Alpha.You"),
	Game_Alpha_They("Game.Alpha.They")/*<player>*/,
	Game_Joined_You("Game.Joined.You"),
	Game_Joined_They("Game.Joined.They")/*<player>*/,
	Game_Left_You("Game.Left.You"),
	Game_Left_They("Game.Left.They")/*<player>*/,
	Game_Info_Arena("Game.Info.Arena")/*<arena>, <creator>*/,
	Game_End_Not_Enough_Players("Game.End.Not Enough Players"),
	Help_Vote("Help.Vote"),
	Help_Grenades("Help.Grenades"),
	Help_Lists("Help.Lists")/*<lists>*/,
	Help_TpSpawn("Help.TpSpawn"),
	Help_DelSpawn("Help.DelSpawn"),
	Help_SetSpawn("Help.SetSpawn"),
	Help_Create("Help.Create"),
	Help_Remove("Help.Remove"),
	Help_SetArena("Help.SetArena"),
	Help_Top("Help.Top")/*<stats>*/,
	Help_Files("Help.Files")/*Files*/,
	Classes_None("Classes.None"), 
	Classes_Chosen("Classes.Chosen")/*<class>*/,
	Sign_CmdSet_Not_Enough("Sign.CmdSet.Cost.Not Enough"),
	Sign_CmdSet_Cost_Needed("Sign.CmdSet.Cost.Needed") /*<needed>*/, 
	Grenades_Bought("Grenades.Bought"),
	Grenades_Cost_Not_Enough("Grenades.Cost.Not Enough"),
	Grenades_Invalid_Id("Grenades.Invalid Id");
			
	private String string;

	private Msgs(String s)
	{
		string = s;
	}
	public String getString(String... variables) {
		String prefix = ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString("Format.Prefix").replaceAll("&x", "&"+String.valueOf(RandomChatColor.getColor().getChar())).replaceAll("&y", "&"+String.valueOf(RandomChatColor.getFormat().getChar()))) + " ";
		try
		{
			String message = (string.startsWith("Format") || string.startsWith("Menu") ? "" : prefix) + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll("&x", "&"+String.valueOf(RandomChatColor.getColor().getChar())).replaceAll("&y", "&"+String.valueOf(RandomChatColor.getFormat().getChar())));
			int i = 0;
			String replace = null;
			for (String variable : variables)
			{
				if (i == 0)
				{
					replace = variable;
					i++;
				} else
				{
					message = message.replaceAll(replace, variable);
					i = 0;
				}
			}
			return message;
		} catch (NullPointerException npe)
		{
			return (string.startsWith("Format") || string.startsWith("Menu") ? "" : prefix) + "Unable to find message: " + string;
		}
	}

};