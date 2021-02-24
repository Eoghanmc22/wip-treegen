package net.minestom.treegen;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.worldgenUtils.Batch;
import net.minestom.worldgenUtils.Context;

import java.util.Random;

public class TreeGenerationContext implements Context {

	public final Random rng;
	public final Batch batch;
	public BlockPosition offset;
	private final InstanceContainer instance;

	public TreeGenerationContext(InstanceContainer instance, BlockPosition offset) {
		this.instance = instance;
		this.rng = new Random();
		this.batch = new Batch(offset);
		this.offset = offset;
	}

	public void apply() {
		batch.apply(this);
	}

	public InstanceContainer getInstance() {
		return instance;
	}

	@Override
	public Random getRNG() {
		return rng;
	}

}
