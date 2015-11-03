package com.bafomdad.zenscape.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFruitBomb extends EntityLivingBase {
	
	public Block block;
	public int meta;
	
	public EntityFruitBomb(World world) {
		
		super(world);
		
		setSize(0.5F, 0.5F);
	}

	public EntityFruitBomb(World world, Block block, int meta) {
		
		this(world);
		this.block = block;
		this.meta = meta;
	}

	@Override
	protected boolean isAIEnabled() {
		
		return false;
	}
	
	@Override
	public boolean canBePushed() {
		
		return false;
	}
	
	@Override
	public boolean isEntityInvulnerable() {
		
		return true;
	}
	
	@Override
	public ItemStack getHeldItem() {
		
		return null;
	}
	
	@Override
	protected boolean canTriggerWalking() {
		
		return false;
	}

	@Override
    public void onEntityUpdate() {
		
        super.onEntityUpdate();

        if (!worldObj.isRemote && this.isEntityAlive() && this.onGround)
        {
        	this.setDead();
        	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true);
        }
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTag) {
		
		super.readEntityFromNBT(nbtTag);;

		block = (Block) Block.blockRegistry.getObject(nbtTag.getString("block"));
		meta = nbtTag.getInteger("meta");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTag) {
		
		super.writeEntityToNBT(nbtTag);
		
		nbtTag.setString("block", Block.blockRegistry.getNameForObject(block));
		nbtTag.setInteger("meta", meta);
	}

	@Override
	public ItemStack getEquipmentInSlot(int p_71124_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		// TODO Auto-generated method stub
		return null;
	}
}
