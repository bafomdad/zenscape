package com.bafomdad.zenscape.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenDownUnder implements IWorldGenerator {

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		int xCh, yCh, zCh;
		int xChunk = chunkX * 16 + 8, zChunk = chunkZ * 16 + 8;

		WorldType type = world.getWorldInfo().getTerrainType();
		if (world.provider.dimensionId == 0 && type != type.FLAT && random.nextInt(230) == 0)
		{
			xCh = xChunk + random.nextInt(16);
			zCh = zChunk + random.nextInt(16);
			yCh = 10 + random.nextInt(5);
			
			boolean isGenerated = new WorldGenUnderground().generate(world, random, xCh, yCh, zCh);
			if (isGenerated)
				System.out.println("Underground structure generated at: " + xCh + ", " + yCh + ", " + zCh);
		}
	}
}
