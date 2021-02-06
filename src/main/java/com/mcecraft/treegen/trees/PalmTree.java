package com.mcecraft.treegen.trees;

import com.mcecraft.treegen.Treegen;
import com.mcecraft.treegen.utils.Batch;
import com.mcecraft.treegen.utils.GenerationContext;
import com.mcecraft.treegen.utils.RandomUtils;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

import java.util.Random;

public class PalmTree extends Tree{

    @Override
    public void build(GenerationContext ctx) {
        Position position = ctx.origin;

        //Place trunk
        position.setPitch(-RandomUtils.randomIntBetween(ctx, 45, 80));
        position.setYaw(RandomUtils.randomIntBetween(ctx, -180, 180));
        position = Treegen.placeRay(ctx, position, Block.OAK_LOG, RandomUtils.randomIntBetween(ctx, 6, 15));

        //Place leaves
        for (int i = 0; i < 6; i++) {
            position.setPitch(RandomUtils.randomIntBetween(ctx, -40, 60));
            position.setYaw(RandomUtils.randomIntBetween(ctx, 0, 60) + i*60);
            Treegen.placeRay(ctx, position, Block.OAK_LEAVES, 7);
        }
    }
}
