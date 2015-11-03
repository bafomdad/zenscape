package com.bafomdad.zenscape.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySeer extends Entity {

	public EntitySeer(World world) {
		
		super(world);
		isImmuneToFire = true;
	}

	@Override
	protected void entityInit() {
		
		setSize(0.5F, 0.5F);
		dataWatcher.addObject(28, 0);
		dataWatcher.setObjectWatched(28);
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if (worldObj.rand.nextInt(3) == 0)
		{
			worldObj.spawnParticle("portal", this.posX - 0.5 + worldObj.rand.nextFloat(), this.posY + worldObj.rand.nextFloat(), this.posZ - 0.5 + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player) {
		
		if (!player.worldObj.isRemote && player.capabilities.isCreativeMode)
		{
			if (player.isSneaking())
			{
				this.setDead();
				return true;
			}
			else
			{
				System.out.println("interact");
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		
		return true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound cmp) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound cmp) {
		
	}
}
