package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDizzyTree extends WorldGenerator {
	
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

		if (canSpawn(world, x, y, z))
		{
			int trunk = rand.nextInt(4) + 3;
			
			for (int treeBase = 0; treeBase < trunk; treeBase++) {
				world.setBlock(x, y + treeBase, z, ZenScape.blockZenLog2, 2, 2);
			}
			
			int topHeight = rand.nextInt(4) + 3;
			
			for (int i = -2; i < 2 + 1; i++) {
				for (int j = trunk; j < trunk + topHeight; j++) {
					for (int k = -2; k < 2 + 1; k++) {
						if (rand.nextInt(7) == 0)
							this.generateLeaves(world, x + i, y + j, z + k);
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private void generateLeaves(World world, int x, int y, int z) {
		
		int radius = 1;
		
		for (int i = x - radius; i <= x + radius; ++i) {
			for (int j = z - radius; j <= z + radius; ++j) {
				if (world.isAirBlock(i, y, j) || world.getBlock(i, y, z).isLeaves(world, i, y, j))
					world.setBlock(i, y, j, ZenScape.blockZenLeaves, 4, 2);
			}
		}
	}
}
