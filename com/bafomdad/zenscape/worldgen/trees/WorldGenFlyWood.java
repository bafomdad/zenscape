package com.bafomdad.zenscape.worldgen.trees;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFlyWood extends WorldGenerator {
	
	boolean isSkyTree;
	
	public WorldGenFlyWood(boolean bool) {
		
		this.isSkyTree = bool;
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
	public boolean generate(World world, Random rand, int x, int y, int z) {

		if (canSpawn(world, x, y, z)) 
		{
			int trunk = rand.nextInt(4) + 3;
		
			for (int i = 0; i < trunk; i++) {
			
				if (isSkyTree)
					world.setBlock(x, y + i, z, ZenScape.blockZenTree);
				else
					world.setBlock(x, y + i, z, ZenScape.blockZenLog2, 0, 2);
			}
			int i = rand.nextInt(2) + 2;
			int j = rand.nextInt(2) + 2;
			int k = rand.nextInt(2) + 2;
			
			this.generateLeaves(world, x, y + trunk + 1, z);
			this.generateLeaves(world, x, y + trunk + 3, z);
			this.generateLeaves(world, x + i, y + trunk + 2, z + k);
			this.generateLeaves(world, x - i, y + trunk + 2, z - k);
			this.generateLeaves(world, x + i, y + trunk + 2, z);
			this.generateLeaves(world, x - i, y + trunk + 2, z);
			this.generateLeaves(world, x + i, y + trunk + 2, z + k);
			this.generateLeaves(world, x - i, y + trunk + 2, z - k);
			this.generateLeaves(world, x + k, y + trunk + j, z);
			this.generateLeaves(world, x - k, y + trunk + j, z);
			this.generateLeaves(world, x, y + trunk + j, z + i);
			this.generateLeaves(world, x, y + trunk + j, z - i);
			return true;
		}
		return false;
	}
	
	private void generateLeaves(World world, int x, int y, int z) {
		
		for (int i = -1; i < 1 + 1; i++) {
			for (int j = -1; j < 1 + 1; j++) {
				for (int k = -1; k < 1 + 1; k++)
				{
					int x1 = x + i;
					int y1 = y + j;
					int z1 = z + k;
					
					Block block = world.getBlock(x1, y1, z1);
					if (block != null && block.isLeaves(world, x1, y1, z1) || world.isAirBlock(x1, y1, z1)) 
					{
						if (isSkyTree)
							world.setBlock(x1, y1, z1, ZenScape.blockZenLeaf);
						else
							world.setBlock(x1, y1, z1, ZenScape.blockZenLeaves, 3, 2);
					}
				}
			}
		}
	}
}
