package com.bafomdad.zenscape.worldgen.trees;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHungerTree extends WorldGenerator {
	
	private boolean canSpawn(World world, int x, int y, int z) {
		
		for (int i = 0; i < 7; i++) {
			if (world.isAirBlock(x, y + i, z))
				return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		if (this.canSpawn(world, x, y, z))
		{
			int treeBase = rand.nextInt(2) + 3;
			
			for (int i = 0; i <= treeBase; i++) {
				world.setBlock(x, y + i, z, ZenScape.blockZenLog3, 2, 3);
			}
			
			int leavesBase = treeBase;
			for (int j = leavesBase; j < leavesBase + 3; j++)
			{
				for (int k = -4; k <= 4; k++) {
					for (int l = -4; l <= 4; l++) {
						if (k * k + l * l < (4 + 0.5) * (4 + 0.5)) {
							if (world.isAirBlock(x + k, y + j, z + l))
							{
								world.setBlock(x + k, y + j, z + l, ZenScape.blockZenLeaves, 8, 3);
							}
							if (rand.nextInt(20) == 0 && world.isAirBlock(x + k, y + j - 1, z + l))
							{
								world.setBlock(x + k, y + j - 1, z + l, ZenScape.blockPotion);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}
