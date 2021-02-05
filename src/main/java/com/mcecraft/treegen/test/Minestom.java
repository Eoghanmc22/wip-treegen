package com.mcecraft.treegen.test;

import com.mcecraft.treegen.Treegen;
import com.mcecraft.treegen.trees.PalmTree;
import com.mcecraft.treegen.trees.Tree;
import com.mcecraft.treegen.utils.RandomUtils;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.BlockBatch;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.world.biomes.Biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Minestom {

    public static void main(String[] args) {
        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // Set the ChunkGenerator
        instanceContainer.setChunkGenerator(new GeneratorDemo());
        // Enable the auto chunk loading (when players come close)
        instanceContainer.enableAutoChunkLoad(true);

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addEventCallback(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Position(0, 42, 0));
        });

        globalEventHandler.addEventCallback(PlayerSpawnEvent.class, event -> {
           event.getPlayer().setGameMode(GameMode.CREATIVE);
        });

        Tree testTree = new PalmTree();

        globalEventHandler.addEventCallback(PlayerBlockPlaceEvent.class, event -> {
            Random rng = new Random();
            BlockBatch blockBatch = event.getPlayer().getInstance().createBlockBatch();
            testTree.build(blockBatch, event.getBlockPosition(), rng);
            blockBatch.flush(null);
        });

        // Start the server on port 25565
        minecraftServer.start("localhost", 25565);
    }

    private static class GeneratorDemo implements ChunkGenerator {

        @Override
        public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
            // Set chunk blocks
            for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++)
                for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                    for (byte y = 0; y < 40; y++) {
                        batch.setBlock(x, y, z, Block.STONE);
                    }
                }
        }

        @Override
        public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
            Arrays.fill(biomes, Biome.PLAINS);
        }

        @Override
        public List<ChunkPopulator> getPopulators() {
            return null;
        }
    }

}
