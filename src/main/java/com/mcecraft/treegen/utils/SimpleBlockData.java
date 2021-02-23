package com.mcecraft.treegen.utils;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.chunk.ChunkUtils;

import java.util.HashSet;

public class SimpleBlockData {

	public static SimpleBlockData block(SimpleBlockPosition pos, Block b) {
		return new SimpleBlockData(pos, b.getBlockId());
	}

	public static SimpleBlockData block(SimpleBlockPosition pos, short b) {
		return new SimpleBlockData(pos, b);
	}

	public final int x, y, z;
	public final short blockStateId;

	public SimpleBlockData(SimpleBlockPosition pos, short blockStateId) {
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
		this.blockStateId = blockStateId;
	}

	public SimpleBlockData(int x, int y, int z, short blockStateId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockStateId = blockStateId;
	}

	public void apply(Instance instance, int xOffset, int yOffset, int zOffset) {
		int rX = x + xOffset;
		int rY = y + yOffset;
		int rZ = z + zOffset;
		final Chunk chunk = instance.getChunkAt(rX, rZ);
		if (ChunkUtils.isLoaded(chunk)) {
			synchronized (chunk) {
				chunk.UNSAFE_setBlock(rX, rY, rZ, blockStateId, (short) 0, null, false);
			}
		}
	}

}
