package com.mcecraft.treegen.utils;

import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

import java.util.Random;

public class GenerationContext {

	public final Random rng;
	public final Batch batch;
	public Position origin;

	public GenerationContext() {
		this.rng = new Random();
		batch = new Batch();
		origin = new Position();
	}

	public void complete(Instance instance, BlockPosition offset) {
		batch.apply(instance, offset);
	}

}
