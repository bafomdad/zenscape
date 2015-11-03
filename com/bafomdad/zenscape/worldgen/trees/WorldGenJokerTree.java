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
				
				int leafStart = trunk;
				
				setBlockIfAir(world, x + 1, y + leafStart, z, ZenScape.blockZenLeaves, 5, 3);
				setBlockIfAir(world, x - 1, y + leafStart, z, ZenScape.blockZenLeaves, 5, 3);
				setBlockIfAir(world, x, y + leafStart, z + 1, ZenScape.blockZenLeaves, 5, 3);
				setBlockIfAir(world, x, y + leafStart, z - 1, ZenScape.blockZenLeaves, 5, 3);
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						for (int leafHeight = leafStart + 1; leafHeight <= leafStart + 3; leafHeight++) {
							setBlockIfAir(world, x + i, y + leafHeight, z, ZenScape.blockZenLeaves, 5, 3);
							setBlockIfAir(world, x, y + leafHeight, z + j, ZenScape.blockZenLeaves, 5, 3);
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private void setBlockIfAir(World world, int x, int y, int z, Block block, int metadata, int flag) {
		
		if (world.isAirBlock(x, y, z))
			world.setBlock(x, y, z, block, metadata, flag);
	}
}
