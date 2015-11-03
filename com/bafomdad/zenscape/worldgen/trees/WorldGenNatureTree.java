package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenNatureTree extends WorldGenerator {
	
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
			
			int trunk = rand.nextInt(4) + 4;
			
			for (int treeBase = 0; treeBase < trunk; treeBase++) {
				world.setBlock(x, y + treeBase, z, ZenScape.blockZenLog, 3, 2);
			}
			
			int topHeight = rand.nextInt(3) + 2;
			
			for (int i = -2; i < 2 + 1; i++) {
				for (int j = trunk; j < trunk + topHeight; j++) {
					for (int k = -2; k < 2 + 1; k++) {
						if (rand.nextInt(8) == 0)
							this.generateLeaves(world, x + i, y + j, z + k);
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private void generateLeaves(World world, int x, int y, int z) {
		
		if (world.isAirBlock(x, y, z) || world.getBlock(x, y, z).isLeaves(world, x, y, z))
			world.setBlock(x, y, z, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x + 1, y, z) || world.getBlock(x + 1, y, z).isLeaves(world, x + 1, y, z))
			world.setBlock(x + 1, y, z, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x, y, z + 1) || world.getBlock(x, y, z + 1).isLeaves(world, x, y, z + 1))
			world.setBlock(x, y, z + 1, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x + 1, y, z + 1) || world.getBlock(x + 1, y, z + 1).isLeaves(world, x + 1, y, z + 1))
			world.setBlock(x + 1, y, z + 1, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x, y + 1, z) || world.getBlock(x, y + 1, z).isLeaves(world, x, y + 1, z))
			world.setBlock(x, y + 1, z, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x + 1, y + 1, z) || world.getBlock(x + 1, y + 1, z).isLeaves(world, x + 1, y + 1, z))
			world.setBlock(x + 1, y + 1, z, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x, y + 1, z + 1) || world.getBlock(x, y + 1, z + 1).isLeaves(world, x, y + 1, z + 1))
			world.setBlock(x, y + 1, z + 1, Blocks.leaves, 1, 2);
		
		if (world.isAirBlock(x + 1, y + 1, z + 1) || world.getBlock(x, y + 1, z).isLeaves(world, x, y + 1, z))
			world.setBlock(x + 1, y + 1, z + 1, Blocks.leaves, 1, 2);
	}
}
