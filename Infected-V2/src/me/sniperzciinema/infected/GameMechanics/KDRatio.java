
package me.sniperzciinema.infected.GameMechanics;


public class KDRatio {

	public static Double KD(String user) {
		int kills = Stats.getKills(user);
		int deaths = Stats.getDeaths(user);
		double ratio = Math.round(((double) kills / (double) deaths) * 100.0D) / 100.0D;
		if (deaths == 0)
			ratio = kills;
		else if (kills == 0)
			ratio = 0.00;
		return ratio;
	}

}
