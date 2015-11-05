package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGearTree extends WorldGenerator {

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
		
		if (this.canSpawn(world, x, y, z)) {
			
			int trunk = rand.nextInt(2) + 3;
			
			for (int treeBase = 0; treeBase <= trunk; treeBase++) {
				
				world.setBlock(x, y + treeBase, z, ZenScape.blockGearLog);
			}
			int leafHeight = trunk;
			
			this.generateLeaves(world, x, y + leafHeight, z);
			
			return true;
		}
		return false;
	}
	
	private void generateLeaves(World world, int x, int y, int z) {
		
		this.setBlockIfAir(world, x - 4, y + 0, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 3, y - 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 3, y + 0, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 3, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 2, y + 0, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 2, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 2, y + 2, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 2, y + 3, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 1, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x - 1, y + 2, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y - 1, z - 3, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y - 1, z + 3, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 0, z - 4, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 0, z - 3, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 0, z - 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 0, z + 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 0, z + 3, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 0, z + 4, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z - 3, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z - 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z - 1, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z + 1, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z + 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 1, z + 3, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 2, z - 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 2, z - 1, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 2, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 2, z + 1, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 2, z + 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 3, z - 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 0, y + 3, z + 2, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 1, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 1, y + 2, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 2, y + 0, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 2, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 2, y + 2, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 2, y + 3, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 3, y - 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 3, y + 0, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 3, y + 1, z + 0, ZenScape.blockZenLeaves, 6, 3);
		this.setBlockIfAir(world, x + 4, y + 0, z + 0, ZenScape.blockZenLeaves, 6, 3);

	}
	
	private void setBlockIfAir(World world, int x, int y, int z, Block block, int metadata, int flag) {
		
		if (world.isAirBlock(x, y, z))
			world.setBlock(x, y, z, block, metadata, flag);
	}
}
