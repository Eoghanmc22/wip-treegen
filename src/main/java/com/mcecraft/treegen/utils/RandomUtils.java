package com.mcecraft.treegen.utils;

import java.util.Random;

public class RandomUtils {

    public static int randomIntBetween(GenerationContext ctx, int min, int max) {
        int range = max - min;
        return ctx.rng.nextInt(range) + min;
    }

    public static float randomFloatBetween(GenerationContext ctx, float min, float max) {
        float range = max - min;
        return ctx.rng.nextFloat() * range + min;
    }

    public static double randomDoubleBetween(GenerationContext ctx, double min, double max) {
        double range = max - min;
        return ctx.rng.nextDouble() * range + min;
    }
}
