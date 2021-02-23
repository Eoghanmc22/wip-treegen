package com.mcecraft.treegen.utils;

import net.minestom.server.instance.Chunk;

public class SimpleBlockPosition {

	public static SimpleBlockPosition at(int x, int y, int z) {
		return new SimpleBlockPosition(x, y, z);
	}

	public final int x, y, z;

	public SimpleBlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public SimpleChunkPos getChunk() {
		return new SimpleChunkPos(Math.floorDiv(x, Chunk.CHUNK_SIZE_X), Math.floorDiv(z, Chunk.CHUNK_SIZE_Z));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SimpleBlockPosition that = (SimpleBlockPosition) o;

		if (x != that.x) return false;
		if (y != that.y) return false;
		return z == that.z;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}

}
