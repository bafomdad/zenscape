package com.bafomdad.zenscape.worldgen;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenIslands implements IWorldGenerator {
	
	public WorldGenIslands() {
		
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		int xCh, yCh, zCh;
		int xChunk = chunkX * 16 + 8, zChunk = chunkZ * 16 + 8;

		WorldType type = world.getWorldInfo().getTerrainType();
		if (world.provider.dimensionId == 0 && type != type.FLAT && random.nextInt(230) == 0)
		{
			xCh = xChunk + random.nextInt(16);
			zCh = zChunk + random.nextInt(16);
			yCh = 150 + random.nextInt(70);
			int size = random.nextInt(11);

			if (size >= 0 && size <= 7) {
				new WorldGenSmallIsland().generate(world, random, xCh, yCh, zCh);
				System.out.println("small island");
			}
			else {
				new WorldGenLargeIsland(size).generate(world, random, xCh, yCh, zCh);
				System.out.println("large island");
			}
		}
	}
}
