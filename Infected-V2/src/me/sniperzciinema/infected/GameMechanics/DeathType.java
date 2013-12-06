
package me.sniperzciinema.infected.GameMechanics;

public enum DeathType
{

	Arrow("Arrow"), Melee("Melee"), Grenade("Grenade"), Gun("Gun"), Other("Other");

	private String string;

	private DeathType(String s)
	{
		string = s;
	}
	
	@Override
	public String toString(){
		return string;
	}
};