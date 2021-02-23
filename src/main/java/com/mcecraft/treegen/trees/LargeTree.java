package com.mcecraft.treegen.trees;

import com.mcecraft.treegen.Treegen;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.worldgenUtils.GenerationContext;
import net.minestom.worldgenUtils.RandomUtils;
import net.minestom.worldgenUtils.VectorUtils;

public class LargeTree extends Tree {

	@Override
	public void build(GenerationContext ctx) {
		final Position focalPoint = ctx.origin.clone().add(0.5, 5, 0.5);

		final Position center = ctx.origin.clone().subtract(0, 3, 0);

		int count = RandomUtils.randomIntBetween(ctx, 3, 6);
		double frac = 1.0/count;
		double degPer = 360*frac;
		double distance = RandomUtils.randomDoubleBetween(ctx, 3, 6);
		for (int i = 0; i < count; i++) {
			final Position temp = center.clone();
			temp.setYaw((float) (RandomUtils.randomDoubleBetween(ctx, 0, degPer) + degPer*i));
			final Position positionA = temp.getDirection().multiply(distance).toPosition().add(temp);
			final Vector vec = VectorUtils.vectorBetweenTwoPoints(positionA, focalPoint);
			Treegen.placeRay(ctx, positionA.toVector(), vec, Block.OAK_LOG, (int) vec.length());
		}

		focalPoint.setPitch(RandomUtils.randomIntBetween(ctx, -85, -75));
		focalPoint.setYaw(RandomUtils.randomIntBetween(ctx, -180, 180));
		final Position position = Treegen.placeRay(ctx, focalPoint, Block.OAK_LOG, RandomUtils.randomIntBetween(ctx, 5, 12));


		count = RandomUtils.randomIntBetween(ctx, 7, 10);
		frac = 1.0/count;
		degPer = 360*frac;

		for (int i = 0; i < count; i++) {
			position.setPitch(RandomUtils.randomIntBetween(ctx, 10, 20));
			position.setYaw((float) (RandomUtils.randomDoubleBetween(ctx, 0, degPer) + degPer*i));
			Treegen.placeCanopyRay(ctx, position, Block.OAK_WOOD, Block.OAK_LEAVES, RandomUtils.randomIntBetween(ctx, 15, 25), 5);
		}
	}

}
