package com.bafomdad.zenscape.worldgen;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDeadTree extends WorldGenerator {

	public boolean canSpawn(World world, int x, int y, int z) {
		
		if (world.getBlock(x, y, z).isAir(world, x, y, z) &&
			world.getBlock(x, y + 1, z).isAir(world, x, y, z)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {

		if (canSpawn(world, i, j, k)) {
			
			world.setBlock(i, j + 0, k, Blocks.deadbush);
			return true;
		}
		return false;
	}
}
