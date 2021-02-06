package com.mcecraft.treegen.trees;

import com.mcecraft.treegen.utils.Batch;
import com.mcecraft.treegen.utils.GenerationContext;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;

import java.util.Random;

public abstract class Tree {

    public abstract void build(GenerationContext ctx);
}
