package me.xxsniperzzxx_sd.infected.GameMechanics;

import java.util.Random;

import me.xxsniperzzxx_sd.infected.Infected;
import me.xxsniperzzxx_sd.infected.Main;
import me.xxsniperzzxx_sd.infected.Enums.Teams;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Abilities {
	@SuppressWarnings("deprecation")
	public static void applyAbilities(Player player) {
		Random r = new Random();
		int n = 0;
		for (String abilities : Infected.filesGetAbilities().getConfigurationSection("Abilities").getKeys(true))
		{
			if (!abilities.contains("."))
			{
				n += Infected.filesGetAbilities().getInt("Abilities." + abilities + ".Chance");
			}
		}
		int i = r.nextInt(n);
		n = 0;
		for (String abilities : Infected.filesGetAbilities().getConfigurationSection("Abilities").getKeys(true))
		{
			n += Infected.filesGetAbilities().getInt("Abilities." + abilities + ".Chance");
			if (n > i)
			{
				Integer id = 0;
				Integer time = 0;
				Integer power = 0;
				int max = Infected.filesGetAbilities().getStringList("Abilities." + abilities + ".Potion Effects").size();
				for (int x = 0; x < max; x++)
				{
					String path = Infected.filesGetAbilities().getStringList("Abilities." + abilities + ".Potion Effects").get(x);
					String[] strings = path.split(":");
					id = Integer.valueOf(strings[0]);
					time = Integer.valueOf(strings[1]) * 20;
					power = Integer.valueOf(strings[2]) - 1;
					player.addPotionEffect(new PotionEffect(
							PotionEffectType.getById(id), time, power));
				}
				player.sendMessage(Main.I + abilities);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void applyClassEffects(Player player) {

		for (PotionEffect reffect : player.getActivePotionEffects())
		{
			player.removePotionEffect(reffect.getType());
		}
		Integer id = 0;
		Integer time = 0;
		Integer power = 0;
		int max = 0;
		String path = "";
		if (Infected.isPlayerZombie(player))
		{
			max = Infected.filesGetClasses().getStringList("Classes.Zombie." + Main.zombieClasses.get(player.getName()) + ".Potion Effects").size();
		} else
		{
			max = Infected.filesGetClasses().getStringList("Classes.Human." + Main.humanClasses.get(player.getName()) + ".Potion Effects").size();
		}
		for (int x = 0; x < max; x++)
		{
			if (Infected.isPlayerZombie(player))
				path = Infected.filesGetClasses().getStringList("Classes.Zombie." + Main.zombieClasses.get(player.getName()) + ".Potion Effects").get(x);
			else
				path = Infected.filesGetClasses().getStringList("Classes.Human." + Main.humanClasses.get(player.getName()) + ".Potion Effects").get(x);

			String[] strings = path.split(":");
			id = Integer.valueOf(strings[0]);
			time = Integer.valueOf(strings[1]) * 20;
			power = Integer.valueOf(strings[2]) - 1;
			player.addPotionEffect(new PotionEffect(
					PotionEffectType.getById(id), time, power));
		}
	}

	@SuppressWarnings("deprecation")
	public static void addEffectOnContact(Player attacker, Player victim) {
		Integer id = 0;
		Integer time = 0;
		Integer power = 0;
		String Class = Infected.playergetZombieClass(attacker);
		if(Infected.playerGetGroup(attacker) == Teams.Human)
			Class = Infected.playergetHumanClass(attacker);
		int max = Infected.filesGetClasses().getStringList(Infected.playerGetGroup(attacker) +"." + Class + ".Effects on Contact").size();
		for (int x = 0; x < max; x = x + 1)
		{
			String path = Infected.filesGetClasses().getStringList(Infected.playerGetGroup(attacker) +"." + Class + ".Effects on Contact").get(x);
			String[] strings = path.split(":");
			id = Integer.valueOf(strings[0]);
			time = Integer.valueOf(strings[1]) * 20;
			power = Integer.valueOf(strings[2]);
			victim.addPotionEffect(new PotionEffect(
					PotionEffectType.getById(id), time, power));
		}

	}



}
