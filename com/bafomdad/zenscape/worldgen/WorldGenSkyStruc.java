package com.bafomdad.zenscape.worldgen;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSkyStruc extends WorldGenerator {
	
	public WorldGenSkyStruc() {
		
	}
	
	public boolean canSpawn(World world, int x, int y, int z) {
		
		if (world.getActualHeight() <= 200)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		world.setBlock(x, y, z, ZenScape.blockSpawnBlock);
		world.setBlock(x, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x, y + 1, z + 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x, y + 1, z - 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x, y + 1, z + 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x, y + 1, z - 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x, y + 2, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 2, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 2, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 2, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 3, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 3, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 3, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 3, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x, y + 4, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 1, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 1, y + 1, z + 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x + 1, y + 1, z - 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x - 1, y + 1, z + 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x - 1, y + 1, z - 3, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x + 1, y + 2, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 2, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 2, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 2, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 3, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 3, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 3, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 3, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x + 1, y + 4, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x - 1, y + 4, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y + 1, z + 3, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y + 1, z - 3, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y + 1, z + 3, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 2, y + 1, z - 3, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 2, y + 2, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 2, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 2, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 2, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 3, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 3, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 3, z + 3, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 3, z - 3, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 4, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x + 2, y + 4, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 4, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x - 2, y + 4, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z + 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y, z - 1, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 3, y + 1, z, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x + 3, y + 1, z, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x + 3, y + 1, z + 1, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x + 3, y + 1, z - 1, ZenScape.blockZenBrick, 3, 3);
		world.setBlock(x + 3, y + 1, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 3, y + 1, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y + 1, z + 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x - 3, y + 1, z - 2, ZenScape.blockZenBrick, 2, 3);
		world.setBlock(x + 3, y + 2, z, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 2, z, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 2, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 2, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 2, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 2, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 2, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 2, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 3, z, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 3, z, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 3, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 3, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 3, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 3, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 3, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 3, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 3, z + 2, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 3, z - 2, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 4, z, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x + 3, y + 4, z - 1, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 4, z + 1, ZenScape.blockZenBrick);
		world.setBlock(x - 3, y + 4, z - 1, ZenScape.blockZenBrick);
		
		ageTheStones(world, x, y, z, rand);

		return true;
	}
	
	private void ageTheStones(World world, int x, int y, int z, Random rand) {
		
		int radius = 9;
		int radiusSquared = radius * radius;
		
		for (int i = radius * -1; i <= radius; ++i) {
			for (int j = radius * -1; j <= radius; ++j) {
				for (int k = radius * -1; k <= radius; ++k)
				{
					if (((i * i) + (j * j) + (k * k)) <= radiusSquared)
					{
						int meta = world.getBlockMetadata(x + i, y + j, z + k);
						if (rand.nextInt(4) == 0 && world.getBlock(x + i, y + j, z + k) == ZenScape.blockZenBrick && meta == 0)
							world.setBlock(x + i, y + j, z + k, ZenScape.blockZenBrick, 1, 3);
					}
				}
			}
		}
	}
}
