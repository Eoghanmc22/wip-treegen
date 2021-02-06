package com.mcecraft.treegen.utils;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.chunk.ChunkUtils;

import java.util.HashMap;
import java.util.HashSet;

import static com.mcecraft.treegen.utils.Batch.SimpleBlockData.block;
import static com.mcecraft.treegen.utils.Batch.SimpleBlockPosition.at;


/**
 * A faster, more optimized, BlockBatch that allows you to retrieve placed blocks in O(1) without hacky stuff.
 */
public class Batch {

	private final HashMap<SimpleBlockPosition, SimpleBlockData> data = new HashMap<>();

	public void setBlock(Position pos, Block b) {
		setBlock((int)pos.getX(), (int)pos.getY(), (int)pos.getZ(), b);
	}

	public void setBlock(BlockPosition bpos, Block b) {
		setBlock(bpos.getX(), bpos.getY(), bpos.getZ(), b);
	}

	public void setBlock(int x, int y, int z, Block b) {
		final SimpleBlockPosition at = at(x, y, z);
		data.put(at, block(at, b));
	}

	public void setBlockId(Position pos, short b) {
		setBlockId((int)pos.getX(), (int)pos.getY(), (int)pos.getZ(), b);
	}

	public void setBlockId(BlockPosition bpos, short b) {
		setBlockId(bpos.getX(), bpos.getY(), bpos.getZ(), b);
	}

	public void setBlockId(int x, int y, int z, short b) {
		final SimpleBlockPosition at = at(x, y, z);
		data.put(at, block(at, b));
	}

	public boolean hasBlockAt(Position pos) {
		return hasBlockAt((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
	}

	public boolean hasBlockAt(BlockPosition bpos) {
		return hasBlockAt(bpos.getX(), bpos.getY(), bpos.getZ());
	}

	public boolean hasBlockAt(int x, int y, int z) {
		return data.containsKey(at(x, y, z));
	}

	public short getBlockIdAt(Position pos) {
		return getBlockIdAt((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
	}

	public short getBlockIdAt(BlockPosition bpos) {
		return getBlockIdAt(bpos.getX(), bpos.getY(), bpos.getZ());
	}

	public short getBlockIdAt(int x, int y, int z) {
		return data.get(at(x, y, z)).blockStateId;
	}

	public Block getBlockAt(Position pos) {
		return Block.fromStateId(getBlockIdAt(pos));
	}

	public Block getBlockAt(BlockPosition bpos) {
		return Block.fromStateId(getBlockIdAt(bpos));
	}

	public Block getBlockAt(int x, int y, int z) {
		return Block.fromStateId(getBlockIdAt(x, y, z));
	}

	public void apply(Instance instance, BlockPosition offset) {
		HashSet<SimpleChunkPos> chunks = new HashSet<>();
		for (final SimpleBlockData data : this.data.values()) {
			data.apply(instance, chunks, offset.getX(), offset.getY(), offset.getZ());
		}
		for (final SimpleChunkPos cPos : chunks) {
			cPos.refreshChunk(instance);
		}
	}

	public HashMap<SimpleBlockPosition, SimpleBlockData> getData() {
		return data;
	}

	public static class SimpleBlockData {

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

		public void apply(Instance instance, HashSet<SimpleChunkPos> chunks, int xOffset, int yOffset, int zOffset) {
			int rX = x+xOffset;
			int rY = y+yOffset;
			int rZ = z+zOffset;
			final Chunk chunk = instance.getChunkAt(rX, rZ);
			if (ChunkUtils.isLoaded(chunk)) {
				synchronized (chunk) {
					chunk.UNSAFE_setBlock(rX, rY, rZ, blockStateId, (short) 0, null, false);
					chunks.add(new SimpleChunkPos(chunk.getChunkX(), chunk.getChunkZ()));
				}
			}
		}
	}

	public static class SimpleChunkPos {

		public final int chunkX, chunkZ;

		public SimpleChunkPos(int chunkX, int chunkZ) {
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
		}

		public void refreshChunk(Instance instance) {
			final Chunk chunk = instance.getChunk(chunkX, chunkZ);
			if (chunk != null) {
				chunk.sendChunk();
			}
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

	public static class SimpleBlockPosition {

		public static SimpleBlockPosition at(int x, int y, int z) {
			return new SimpleBlockPosition(x,y,z);
		}

		public final int x,y,z;

		public SimpleBlockPosition(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
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
}
