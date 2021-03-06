
package me.sniperzciinema.infected.GameMechanics;

import me.sniperzciinema.infected.Handlers.Classes.InfClass;
import me.sniperzciinema.infected.Handlers.Player.InfPlayer;
import me.sniperzciinema.infected.Handlers.Player.InfPlayerManager;
import me.sniperzciinema.infected.Handlers.Player.Team;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class Equip {

	/**
	 * Equip the player to match with what their class says they should have,
	 * any items in the inventory that aren't from that class will be ignored.
	 * 
	 * Also does their armor
	 * 
	 * @param p
	 *            - The player we're equipping
	 */
	@SuppressWarnings("deprecation")
	public static void equip(Player p) {
		InfPlayer IP = InfPlayerManager.getInfPlayer(p);
		Team team = IP.getTeam();
		InfClass Class = IP.getInfClass(team);
		p.playSound(p.getLocation(), Sound.ANVIL_USE, 1, 1);

		// Reset their inventory by: Going through and removing any old items
		// from the class
		// and add the new ones, this way we don't remove purchased/grenades
		if (!Class.getItems().isEmpty())
			for (ItemStack is : Class.getItems())
			{
				if (p.getInventory().contains(is.getType()))
					p.getInventory().remove(is);
				if (!p.getInventory().contains(is.getType()))
					p.getInventory().addItem(is);
			}

		// Only replace the armor if the player hasn't changed it(So if
		// its none, or if it is the same as default)
		if (p.getInventory().getHelmet() == Class.getHelmet() || p.getInventory().getHelmet() == null)
			p.getInventory().setHelmet(Class.getHelmet());
		if (p.getInventory().getChestplate() == Class.getChestplate() || p.getInventory().getChestplate() == null)
			p.getInventory().setChestplate(Class.getChestplate());
		if (p.getInventory().getLeggings() == Class.getLeggings() || p.getInventory().getLeggings() == null)
			p.getInventory().setLeggings(Class.getLeggings());
		if (p.getInventory().getBoots() == Class.getBoots() || p.getInventory().getBoots() == null)
			p.getInventory().setBoots(Class.getBoots());

		p.updateInventory();

	}

	/**
	 * Used to change a players armor and items to their zombie class's items
	 * and armor. Once again ignoring items that aren't from either
	 * class(Grenades or Purchased Items)
	 * 
	 * @param p
	 *            - The player who just became a Zombie
	 */
	@SuppressWarnings("deprecation")
	public static void equipToZombie(Player p) {
		InfPlayer IP = InfPlayerManager.getInfPlayer(p);

		InfClass humanClass = IP.getInfClass(Team.Human);
		InfClass zombieClass = IP.getInfClass(Team.Zombie);

		p.playSound(p.getLocation(), Sound.ANVIL_USE, 1, 1);

		// Reset their inventory by: Going through and removing any old items
		// from the class
		// and add the new ones, this way we don't remove purchased/grenades
		if (!humanClass.getItems().isEmpty())
			for (ItemStack is : humanClass.getItems())
				p.getInventory().remove(is.getType());

		if (p.getInventory().getContents().length != 0)
			for (ItemStack is : p.getInventory().getContents())
				if (is != null && (is.getType() == humanClass.getBoots().getType() || is.getType() == humanClass.getChestplate().getType() || is.getType() == humanClass.getHelmet().getType() || is.getType() == humanClass.getLeggings().getType()))
					p.getInventory().remove(is);

		if (!zombieClass.getItems().isEmpty())
			for (ItemStack is : zombieClass.getItems())
				p.getInventory().addItem(is);

		// Only replace the armor if the player hasn't changed it(So if
		// its none, or if it is the same as default)
		if (p.getInventory().getHelmet() == null || p.getInventory().getHelmet().getType() == humanClass.getHelmet().getType())
			p.getInventory().setHelmet(zombieClass.getHelmet());
		if (p.getInventory().getChestplate() == null || p.getInventory().getChestplate().getType() == humanClass.getChestplate().getType())
			p.getInventory().setChestplate(zombieClass.getChestplate());
		if (p.getInventory().getLeggings() == null || p.getInventory().getLeggings().getType() == humanClass.getLeggings().getType())
			p.getInventory().setLeggings(zombieClass.getLeggings());
		if (p.getInventory().getBoots() == null || p.getInventory().getBoots().getType() == humanClass.getBoots().getType())
			p.getInventory().setBoots(zombieClass.getBoots());

		p.updateInventory();

	}

}
