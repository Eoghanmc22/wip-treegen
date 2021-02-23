package com.mcecraft.treegen.utils;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;

public class SimpleChunkPos {

	public final int chunkX, chunkZ;

	public SimpleChunkPos(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public Chunk getChunk(Instance instance) {
		return instance.getChunk(chunkX, chunkZ);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SimpleChunkPos that = (SimpleChunkPos) o;

		if (chunkX != that.chunkX) return false;
		return chunkZ == that.chunkZ;
	}

	@Override
	public int hashCode() {
		int result = chunkX;
		result = 31 * result + chunkZ;
		return result;
	}

}
