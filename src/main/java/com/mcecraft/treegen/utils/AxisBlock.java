package com.mcecraft.treegen.utils;

import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

public class AxisBlock {

	public static short placeOn(Position surface, Position newBlock, Block b) {
		final Vector vector = VectorUtils.vectorBetweenTwoPoints(newBlock, surface);
		final Position pos = new Position();
		pos.setDirection(vector);
		String axis;
		if (pos.getPitch() > 45 || pos.getPitch() < -45) {
			axis = "y";
		} else {
			if (pos.getYaw() > 135 || pos.getYaw() < -135 || (pos.getYaw() < 45 && pos.getYaw() > -45)) {
				axis = "z";
			} else {
				axis = "x";
			}
		}
		return b.withProperties("axis=" + axis);
	}
}
