package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDokuTree extends WorldGenerator {
	
	public boolean canSpawn(World world, int x, int y, int z) {
		
		if (world.getBlock(x, y, z).isAir(world, x, y, z) &&
			world.getBlock(x, y + 1, z).isAir(world, x, y, z) &&
			world.getBlock(x, y + 2, z).isAir(world, x, y, z) &&
			world.getBlock(x, y + 3, z).isAir(world, x, y, z) &&
			world.getBlock(x, y + 4, z).isAir(world, x, y, z) &&
			world.getBlock(x, y + 5, z).isAir(world, x, y, z)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		if (canSpawn(world, x, y, z)) {

			int trunk = rand.nextInt(5) + 2;
			
			if (trunk < 6) {
				for (int i = 0; i < trunk; i++) {
					world.setBlock(x, y + i, z, ZenScape.blockZenLog2, 1, 2);
				}
				int r = rand.nextInt(3);
				int branch = trunk + (rand.nextInt(2) + 2);
				for (int j = trunk; j < branch; j++) {
					if (r == 0)
					{
						world.setBlock(x + 1, y + j, z, ZenScape.blockZenLog2, 1, 2);
					}
					if (r == 1)
					{
						world.setBlock(x, y + j, z + 1, ZenScape.blockZenLog2, 1, 2);
					}
					if (r == 2)
					{
						world.setBlock(x - 1, y + j, z, ZenScape.blockZenLog2, 1, 2);
					}
					if (r == 3)
					{
						world.setBlock(x, y + j, z - 1, ZenScape.blockZenLog2, 1, 2);
					}
					this.generateLeaves(world, x, y + branch, z);
				}
			}
			else
			{
				for (int i = 0; i < trunk; i++)
				{
					world.setBlock(x, y + i, z, ZenScape.blockZenLog2, 1, 2);
				}
				this.generateLeaves(world, x, y + trunk, z);
			}
			return true;
		}
		return false;
	}
	
	private void generateLeaves(World world, int x, int y, int z) {
		
		int radius = 2;
		int radiusSquared = radius * radius;
		
		for (int i = radius * -1; i <= radius; ++i) {
			for (int j = radius * -1; j <= radius; ++j) {
				if (((i * i) + (j * j)) <= radiusSquared) {
					if (world.isAirBlock(x + i, y, z + j))
					{
						world.setBlock(x + i, y, z + j, ZenScape.blockZenLeaves, 2, 2);
					}
				}
			}
		}
		world.setBlock(x, y + 1, z, ZenScape.blockZenLeaves, 2, 2);
		world.setBlock(x + 1, y + 1, z, ZenScape.blockZenLeaves, 2, 2);
		world.setBlock(x - 1, y + 1, z, ZenScape.blockZenLeaves, 2, 2);
		world.setBlock(x, y + 1, z + 1, ZenScape.blockZenLeaves, 2, 2);
		world.setBlock(x, y + 1, z - 1, ZenScape.blockZenLeaves, 2, 2);
		
		int k = world.rand.nextInt(1) + 2;
		
		for (int i = 0; i < k; i++) {
			world.setBlock(x - 2, y - i, z - 1, ZenScape.blockZenLeaves, 2, 2);
			world.setBlock(x + 2, y - i, z + 1, ZenScape.blockZenLeaves, 2, 2);
			world.setBlock(x + 2, y - i, z - 1, ZenScape.blockZenLeaves, 2, 2);
			world.setBlock(x - 2, y - i, z + 1, ZenScape.blockZenLeaves, 2, 2);
			
			world.setBlock(x - 1, y - i, z - 2, ZenScape.blockZenLeaves, 2, 2);
			world.setBlock(x + 1, y - i, z + 2, ZenScape.blockZenLeaves, 2, 2);
			world.setBlock(x + 1, y - i, z - 2, ZenScape.blockZenLeaves, 2, 2);
			world.setBlock(x - 1, y - i, z + 2, ZenScape.blockZenLeaves, 2, 2);
		}
	}
}
