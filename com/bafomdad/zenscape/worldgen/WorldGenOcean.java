package com.bafomdad.zenscape.worldgen;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOcean implements IWorldGenerator {

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		int xCh, yCh, zCh;
		int xChunk = chunkX * 16 + 8, zChunk = chunkZ * 16 + 8;

		WorldType type = world.getWorldInfo().getTerrainType();
		if (world.provider.dimensionId == 0 && type != type.FLAT && random.nextInt(230) == 0)
		{
			xCh = xChunk + random.nextInt(16);
			zCh = zChunk + random.nextInt(16);
			yCh = 0;
			
			for (int i = world.getHeightValue(xCh, zCh); i > 10; i--)
			{
				if (world.getBlock(xCh, i, zCh) != null && world.getBlock(xCh, i, zCh).getMaterial() != Material.water && !world.isAirBlock(xCh, i, zCh))
				{
					yCh = i;
					break;
				}
			}
			BiomeGenBase genBase = world.getBiomeGenForCoords(xCh, zCh);
			if (genBase.biomeName.equals("Deep Ocean") && yCh != 0 && yCh < 65)
			{
				new WorldGenWaterStruc().generate(world, random, xCh, yCh, zCh);
//				System.out.println("Ocean structure generated at: " + xCh + ", " + yCh + ", " + zCh);
			}
		}
	}
}
