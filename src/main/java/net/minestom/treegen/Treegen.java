package net.minestom.treegen;

import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.worldgenUtils.*;

import java.util.List;

public class Treegen {

    public static Position placeRay(TreeGenerationContext ctx, final Position startingPos, Block block, final int length) {
        List<Vector> positions = Raytrace.rayTrace(startingPos, 0.1, length*10);
        return collectPosList(ctx.batch, positions, block);
    }

    public static Position placeRay(TreeGenerationContext ctx, final Vector startingPos, final Vector direction, Block block, final int length) {
        List<Vector> positions = Raytrace.rayTrace(startingPos, direction, 0.1, length*10);
        return collectPosList(ctx.batch, positions, block);
    }

    private static Position collectPosList(final Batch batch, final List<Vector> positions, final Block b) {
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

    public static void placeLeaves(TreeGenerationContext ctx, BlockPosition bpos, Block leaf, int dist) {
        if (dist <= 0) {
            return;
        }
        ctx.batch.setBlock(bpos, leaf);
        BlockPosition bpos2 = bpos.clone().add(1, 0, 0);
        if (ctx.rng.nextFloat() < 0.6 && !ctx.batch.hasBlockAt(bpos2)) {
            placeLeaves(ctx, bpos2, leaf, dist - 1);
        }
        bpos2 = bpos.clone().add(-1, 0, 0);
        if (ctx.rng.nextFloat() < 0.6 && !ctx.batch.hasBlockAt(bpos2)) {
            placeLeaves(ctx, bpos2, leaf, dist - 1);
        }
        bpos2 = bpos.clone().add(0, 1, 0);
        if (ctx.rng.nextFloat() < 0.6 && !ctx.batch.hasBlockAt(bpos2)) {
            placeLeaves(ctx, bpos2, leaf, dist - 1);
        }
        bpos2 = bpos.clone().add(0, -1, 0);
        if (ctx.rng.nextFloat() < 0.6 && !ctx.batch.hasBlockAt(bpos2)) {
            placeLeaves(ctx, bpos2, leaf, dist - 1);
        }
        bpos2 = bpos.clone().add(0, 0, 1);
        if (ctx.rng.nextFloat() < 0.6 && !ctx.batch.hasBlockAt(bpos2)) {
            placeLeaves(ctx, bpos2, leaf, dist - 1);
        }
        bpos2 = bpos.clone().add(0, 0, -1);
        if (ctx.rng.nextFloat() < 0.6 && !ctx.batch.hasBlockAt(bpos2)) {
            placeLeaves(ctx, bpos2, leaf, dist - 1);
        }
    }

    public static void placeCanopyRay(final TreeGenerationContext ctx, final Position startingPos, final Block logBlock, final Block leafBlock, final int length, final int leafSpread) {
        List<Vector> positions = Raytrace.rayTrace(startingPos, 0.1, length*10);
        BlockPosition last = startingPos.toBlockPosition();
        for (Vector vec : positions) {
            BlockPosition bpos = new BlockPosition(vec.getX(), vec.getY(), vec.getZ());
            if (!bpos.equals(last)) {
                ctx.batch.setBlockId(bpos, AxisBlock.placeOn(last.toPosition(), bpos.toPosition(), logBlock));
                last = bpos;

                if (ctx.rng.nextFloat() < 0.6) {
                    placeLeaves(ctx, bpos.clone().add(0, 1, 0), leafBlock, leafSpread);
                }

                if (ctx.rng.nextFloat() < 0.2) {
                    placeLeaves(ctx, bpos.clone().add(0, -1, 0), leafBlock, leafSpread);
                }

                //Should fork
                if (ctx.rng.nextFloat() < 0.2 && length > 4) {
                    Position pos = vec.toPosition();
                    pos.setDirection(startingPos.getDirection());
                    int difference = RandomUtils.randomIntBetween(ctx, 30, 70);
                    difference = ctx.rng.nextBoolean() ? difference : -difference;

                    int len = length / 2;//(positions.size() - i)/20;

                    pos.setYaw(pos.getYaw() + difference);
                    placeCanopyRay(ctx, pos, logBlock, leafBlock, len, leafSpread);

                    pos.setYaw(pos.getYaw() - difference);
                    placeCanopyRay(ctx, pos, logBlock, leafBlock, len, leafSpread);
                    break;
                }
            }
        }
    }
}
