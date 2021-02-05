package com.mcecraft.treegen;

import com.mcecraft.treegen.utils.RandomUtils;
import com.mcecraft.treegen.utils.Raytrace;
import net.minestom.server.instance.batch.BlockBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import java.util.List;
import java.util.Random;

public class Treegen {

    public static Position placeRay(BlockBatch batch, Position startingPos, Block b, int length) {
        List<Vector> positions = Raytrace.rayTrace(startingPos, 0.1, length*10);
        BlockPosition last = null;
        for (Vector vec : positions) {
            BlockPosition bpos = new BlockPosition(vec.getX(), vec.getY(), vec.getZ());
            if (!bpos.equals(last)) {
                last = bpos;
                batch.setBlock(bpos, b);
            }
        }
        return positions.get(positions.size()-1).toPosition();
    }
}
