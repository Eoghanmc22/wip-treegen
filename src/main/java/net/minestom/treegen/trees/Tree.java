package net.minestom.treegen.trees;

import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.worldgenUtils.GenerationContext;

import java.util.Random;

public abstract class Tree {

    public abstract void build(GenerationContext ctx);
}
