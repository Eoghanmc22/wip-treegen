package com.mcecraft.treegen.utils;

import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

public class VectorUtils {

	public static Vector vectorBetweenTwoPoints(Position a, Position b) {
		return b.clone().subtract(a.getX(), a.getY(), a.getZ()).toVector();
	}

}
