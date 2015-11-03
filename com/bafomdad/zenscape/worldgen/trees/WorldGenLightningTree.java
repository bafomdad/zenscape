package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bafomdad.zenscape.ZenScape;

public class WorldGenLightningTree extends WorldGenerator {
	
	boolean isPowered;

	public WorldGenLightningTree(boolean flag) {
		
		this.isPowered = flag;
	}
	
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
	public boolean generate(World world, Random rand, int i, int j, int k) {

		if (canSpawn(world, i, j, k)) {
			
			world.setBlock(i, j + 0, k, ZenScape.blockZenLog, 0, 3);
			if (isPowered)
				world.setBlock(i, j + 1, k, ZenScape.blockPowerLog, 0, 3);
			else
				world.setBlock(i, j + 1, k, ZenScape.blockZenLog, 0, 3);
			world.setBlock(i, j + 2, k, ZenScape.blockZenLog, 0, 3);
			world.setBlock(i, j + 3, k, ZenScape.blockZenLog, 0, 3);
		
			world.setBlock(i, j + 4, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 5, k, ZenScape.blockZenLeaves, 0, 3);
		
			world.setBlock(i + 1, j + 3, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 3, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 3, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 3, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 4, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 4, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 4, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 4, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 5, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 5, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 5, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 5, k + 1, ZenScape.blockZenLeaves, 0, 3);
		
			world.setBlock(i + 1, j + 3, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 3, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 3, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 3, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 4, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 4, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 4, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 4, k - 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i + 1, j + 5, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i - 1, j + 5, k, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 5, k + 1, ZenScape.blockZenLeaves, 0, 3);
			world.setBlock(i, j + 5, k - 1, ZenScape.blockZenLeaves, 0, 3);

			return true;
		}
		return false;
	}
}
