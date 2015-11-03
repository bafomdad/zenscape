package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockColdLava extends Block {

	public BlockColdLava(Material material) {
		
		super(material);
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if (rand.nextInt(2) == 0 && world.provider.isHellWorld)
		{
			world.setBlock(x, y, z, Blocks.lava);
		}
	}
}
