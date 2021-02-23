package net.minestom.treegen.trees;

import net.minestom.treegen.Treegen;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.worldgenUtils.GenerationContext;
import net.minestom.worldgenUtils.Interpolation;
import net.minestom.worldgenUtils.RandomUtils;

public class MediumTree extends Tree {

	@Override
	public void build(GenerationContext ctx) {
		// base will be 9x9
		double _00 = RandomUtils.randomDoubleBetween(ctx, -1.2, 1);
		double _01 = RandomUtils.randomDoubleBetween(ctx, 0, 1);
		double _02 = RandomUtils.randomDoubleBetween(ctx, -1.2, 1);

		double _10 = RandomUtils.randomDoubleBetween(ctx, 0, 0.8);
		double _11 = RandomUtils.randomDoubleBetween(ctx, 1, 2);
		double _12 = RandomUtils.randomDoubleBetween(ctx, 0, 1);

		double _20 = RandomUtils.randomDoubleBetween(ctx, -1.2, 1);
		double _21 = RandomUtils.randomDoubleBetween(ctx, 0, 1.5);
		double _22 = RandomUtils.randomDoubleBetween(ctx, -1.2, 1);

		double[] array = new double[] {_00, _01, _02, _10, _11, _12, _20, _21, _22};
		//double[] array = new double[] {_00, _10, _20, _01, _11, _21, _02, _12, _22};

		for (int x = 0; x < 5; x++) {
			for (int z = 0; z < 5; z++) {
				final double v = Interpolation.bilinearInterpolation(array, 3, 6, x, z);
				placeRoots(ctx, v, x, 0, z, Block.OAK_WOOD, Block.OAK_SLAB);
			}
		}

		Position position = new Position(2, (int) _11, 2);
		position.setPitch(-90); // a ray at an angle doesn't really look good on a tree this small
		position.setYaw(RandomUtils.randomFloatBetween(ctx, -180, 180));
		position = Treegen.placeRay(ctx, position, Block.OAK_LOG, RandomUtils.randomIntBetween(ctx, 3, 6));

		int count = RandomUtils.randomIntBetween(ctx, 4, 7);
		double frac = 1.0/count;
		double degPer = 360*frac;

		for (int i = 0; i < count; i++) {
			position.setPitch(RandomUtils.randomIntBetween(ctx, -15, 0));
			position.setYaw((float) (RandomUtils.randomDoubleBetween(ctx, 0, degPer) + degPer*i));
			Treegen.placeCanopyRay(ctx, position, Block.OAK_WOOD, Block.OAK_LEAVES, RandomUtils.randomIntBetween(ctx, 3, 5), 5);
		}
	}

	public static void placeRoots(GenerationContext ctx, double v, int x, int y, int z, Block log, Block slab) {
		if (v < 0) {
			return;
		}
		int i = (int) v;
		double dec = v - i;
		int count = i;
		boolean needsSlab = false;
		if (dec > 0.75) {
			count += 1;
		} else if (dec > 0.25 && ctx.rng.nextFloat() < 0.5) {
			needsSlab = true;
		}
		for (int j = 0; j < count; j++) {
			ctx.batch.setBlock(x, y + j, z, log);
		}
		if (needsSlab) {
			ctx.batch.setBlock(x, y + count, z, slab);
		}
	}
}
