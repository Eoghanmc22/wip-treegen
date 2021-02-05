package com.mcecraft.treegen.trees;

import com.mcecraft.treegen.Treegen;
import com.mcecraft.treegen.utils.RandomUtils;
import net.minestom.server.instance.batch.BlockBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

import java.util.Random;

public class PalmTree extends Tree{

    @Override
    public void build(BlockBatch blockBatch, BlockPosition bPos, Random rng) {
        Position position = bPos.toPosition();

        //Place trunk
        position.setPitch(-RandomUtils.randomIntBetween(rng, 45, 80));
        position.setYaw(RandomUtils.randomIntBetween(rng, -180, 180));
        position = Treegen.placeRay(blockBatch, position, Block.OAK_LOG, RandomUtils.randomIntBetween(rng, 6, 15));

        //Place leaves
        for (int i = 0; i < 6; i++) {
            position.setPitch(RandomUtils.randomIntBetween(rng, -40, 60));
            position.setYaw(RandomUtils.randomIntBetween(rng, 0, 60) + i*60);
            Treegen.placeRay(blockBatch, position, Block.OAK_LEAVES, 7);
        }
    }
}
