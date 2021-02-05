package com.mcecraft.treegen.trees;

import net.minestom.server.instance.batch.BlockBatch;
import net.minestom.server.utils.BlockPosition;

import java.util.Random;

public abstract class Tree {

    public abstract void build(BlockBatch batch, BlockPosition bPos, Random rng);
}
