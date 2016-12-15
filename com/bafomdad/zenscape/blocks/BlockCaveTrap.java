package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockCaveTrap extends Block {

	public BlockCaveTrap(Material material) {
		
		super(material);
	}
	
	@Override
    public int quantityDropped(Random rand) {
        
    	return 0;
    }
	
	@Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	
		if (entity != null && entity instanceof EntityPlayer) {
			world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
		}
    }
	
	public int tickRate(World world) {
		
		return 15;
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if (!world.isRemote)
		{
			world.createExplosion(null, x, y + 1, z, 4.5F, true);
		}
	}
}
