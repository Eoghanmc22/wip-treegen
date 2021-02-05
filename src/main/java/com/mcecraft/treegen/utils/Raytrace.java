package com.mcecraft.treegen.utils;

import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import java.util.ArrayList;
import java.util.List;

public class Raytrace {

    public static List<Vector> rayTrace(final Position pos, final double step, final int steps) {
        return rayTrace(pos.toVector(), pos.getDirection(), step, steps);
    }

    public static List<Vector> rayTrace(final Vector startingPos, final Vector direction, final double step, final int steps) {
        direction.normalize().multiply(step);
        List<Vector> positions = new ArrayList<>(steps);
        for (int i = 0; i < steps; i++) {
            positions.add(startingPos.clone().add(direction.clone().multiply(i)));
        }
        return positions;
    }
}
