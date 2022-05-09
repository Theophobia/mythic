package me.theophobia.mythic;

import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;

public class Util {

	private static RandomGenerator random = new SecureRandom();

	private static RandomGenerator getRandom() {
		return random;
	}

	public static class Vectors {
		public static Vector getRandomUnitVectorXZ() {
			Vector v = new Vector(1, 0, 0);
			v.rotateAroundY(getRandom().nextDouble() * 2 * Math.PI);
			return v;
		}

		public static Vector getRandomUnitVector() {
			Vector v = new Vector(1, 0, 0);
			v.rotateAroundX(getRandom().nextDouble() * 2 * Math.PI);
			v.rotateAroundY(getRandom().nextDouble() * 2 * Math.PI);
			return v;
		}
	}
}
