package com.bafomdad.zenscape.entity;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockDokuPot;
import com.bafomdad.zenscape.network.ZPacketDispatcher;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDokuDrop extends EntityThrowable {
	
	public EntityDokuDrop(World world) {
		
		super(world);
	}
	
	public EntityDokuDrop(World world, double d1, double d2, double d3) {
		
		super(world, d1, d2, d3);
	}
	
	public EntityDokuDrop(World world, EntityLivingBase epb) {
		
		super(world, epb);
	}

	public void onImpact(MovingObjectPosition mop) {
		
		if (mop.typeOfHit == mop.typeOfHit.BLOCK)
		{
			Block block = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if (block != ZenScape.blockDokuPot)
			{
				ZPacketDispatcher.clientParticle("droplet", mop.blockX, mop.blockY + 0.5F, mop.blockZ, 0.0D, 0.03D, 0.0D);
				this.setDead();
			}
			else
			{
				int l = ((BlockDokuPot)block).func_150027_b(worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
				((BlockDokuPot)block).func_150024_a(worldObj, mop.blockX, mop.blockY, mop.blockZ, l + 1);
			}
		}
		else if (mop.typeOfHit == mop.typeOfHit.ENTITY)
		{
			Entity e = mop.entityHit;
			((EntityLivingBase)e).addPotionEffect(new PotionEffect(19, 160, 1));
		}
		ZPacketDispatcher.clientParticle("droplet", mop.blockX, mop.blockY + 0.5F, mop.blockZ, 0.0D, 0.03D, 0.0D);
		this.setDead();
	}
}
