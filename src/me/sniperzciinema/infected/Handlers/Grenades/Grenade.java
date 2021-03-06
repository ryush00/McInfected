
package me.sniperzciinema.infected.Handlers.Grenades;

import java.util.ArrayList;

import me.sniperzciinema.infected.Handlers.Items.ItemHandler;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


public class Grenade {

	private String name;
	private ItemStack item;
	private ArrayList<PotionEffect> effects;
	private double damage;
	private int delay;
	private int range;
	private Player thrower;
	private boolean damageThrower;
	private int cost;

	public Grenade(String id, String name, double damage, int delay, int range,
			int cost, boolean damageThrower, ArrayList<PotionEffect> effects)
	{
		this.item = ItemHandler.getItemStack(id);
		this.name = name;
		this.damage = damage;
		this.delay = delay;
		this.range = range;
		this.cost = cost;
		this.damageThrower = damageThrower;
		this.effects = effects;
	}

	public void addPotionEffect(Player p) {
		for (PotionEffect PE : getEffects())
			p.addPotionEffect(PE);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getItem(){
		return item;
	}
	public void setItem(ItemStack item){
		this.item = item;
	}

	/**
	 * @return the effects
	 */
	public ArrayList<PotionEffect> getEffects() {
		return effects;
	}

	/**
	 * @param effects
	 *            the effects to set
	 */
	public void setEffects(ArrayList<PotionEffect> effects) {
		this.effects = effects;
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param delay
	 *            the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the thrower
	 */
	public Player getThrower() {
		return thrower;
	}

	/**
	 * @param thrower
	 *            the thrower to set
	 */
	public void setThrower(Player thrower) {
		this.thrower = thrower;
	}

	/**
	 * @return the range
	 */
	public int getRange() {
		return range;
	}

	/**
	 * @param range
	 *            the range to set
	 */
	public void setRange(int range) {
		this.range = range;
	}

	/**
	 * @return the damageThrower
	 */
	public boolean isDamageThrower() {
		return damageThrower;
	}

	/**
	 * @param damageThrower
	 *            the damageThrower to set
	 */
	public void setDamageThrower(boolean damageThrower) {
		this.damageThrower = damageThrower;
	}

	/**
	 * @return the damage
	 */
	public double getDamage() {
		return damage;
	}

	/**
	 * @param damage
	 *            the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}


	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

}
