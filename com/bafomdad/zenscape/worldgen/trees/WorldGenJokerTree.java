package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bafomdad.zenscape.ZenScape;

public class WorldGenJokerTree extends WorldGenerator {

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
			
			int trunk = rand.nextInt(2) + 6;
			
			for (int treeBase = 0; treeBase <= trunk; treeBase++) {
				world.setBlock(x, y + treeBase, z, ZenScape.blockZenLog3, 0, 3);
				
				int leafStart = trunk - 1;
				
				this.generateLeaves(world, x, y + leafStart, z);
				
				for (int i = -3; i <= 3; i++) {
					for (int j = -3; j <= 3; j++) {
						for (int k = leafStart; k <= leafStart + 5; k++) {
							if (world.getBlock(x + i, y + (k - 1), z + j) != null && world.getBlock(x + i, y + (k - 1), z + j) == ZenScape.blockZenLeaves && world.getBlockMetadata(x + i, y + (k - 1), z + j) == 5)
							{
								this.setBlockIfAir(world, x, y, z, ZenScape.blockJoker, 0, 3);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private void generateLeaves(World world, int x, int y, int z) {
		
		world.setBlock(x - 3, y + 4, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 3, y + 5, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 3, y + 6, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 3, y + 6, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 3, y + 7, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 3, y + 7, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 2, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 3, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 3, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 3, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 4, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 4, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 4, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 5, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 5, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 5, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 5, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 6, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 6, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 7, z - 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 2, y + 7, z + 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 0, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 1, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 1, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 1, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 2, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 2, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 2, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 3, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 3, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 3, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 3, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 3, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 4, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 4, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 4, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 4, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 5, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 5, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 6, z - 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x - 1, y + 6, z + 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 0, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 0, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 1, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 1, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 2, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 2, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 2, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 2, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 3, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 3, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 3, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 3, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 3, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 4, z - 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 4, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 4, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 4, z + 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 5, z - 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 0, y + 5, z + 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 0, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 1, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 1, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 1, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 2, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 2, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 2, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 3, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 3, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 3, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 3, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 3, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 4, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 4, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 4, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 4, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 5, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 5, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 6, z - 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 1, y + 6, z + 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 2, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 3, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 3, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 3, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 4, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 4, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 4, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 5, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 5, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 5, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 5, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 6, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 6, z + 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 7, z - 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 2, y + 7, z + 3, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 3, y + 4, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 3, y + 5, z + 0, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 3, y + 6, z - 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 3, y + 6, z + 1, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 3, y + 7, z - 2, ZenScape.blockZenLeaves, 5, 3);
		world.setBlock(x + 3, y + 7, z + 2, ZenScape.blockZenLeaves, 5, 3);
	}
	
	private void setBlockIfAir(World world, int x, int y, int z, Block block, int metadata, int flag) {
		
		if (world.isAirBlock(x, y, z))
			world.setBlock(x, y, z, block, metadata, flag);
	}
}
