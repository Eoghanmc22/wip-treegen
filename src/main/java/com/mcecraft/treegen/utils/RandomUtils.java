package com.mcecraft.treegen.utils;

import java.util.Random;

public class RandomUtils {

    public static int randomIntBetween(Random rng, int min, int max) {
        int range = max - min;
        return rng.nextInt(range) + min;
    }

    public static float randomFloatBetween(Random rng, float min, float max) {
        float range = max - min;
        return rng.nextFloat() * range + min;
    }
}
