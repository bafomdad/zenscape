package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;

public class BlockCaveTrap extends Block {

	public BlockCaveTrap(Material material) {
		
		super(material);
		setTickRandomly(true);
	}
	
	@Override
    public int quantityDropped(Random rand) {
        
    	return 0;
    }
	
	@Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	
    	if (!world.isRemote && entity instanceof EntityPlayer)
    	{
            world.playSoundEffect((double)x + 0.5D, (double)y + 0.1D, (double)z + 0.5D, "random.click", 0.3F, 0.5F);

            world.scheduleBlockUpdate(x, y, z, this, 20);
            {
            	world.createExplosion(null, x, y + 1, z, 4.5F, true);
            }
    	}
    	return;
    }
}
