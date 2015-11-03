package com.bafomdad.zenscape.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPuffball extends EntityThrowable {

	public EntityPuffball(World world) {
	
		super(world);
	}

	public EntityPuffball(World world, EntityLivingBase epb) {
		
		super(world, epb);
	}
	
	public EntityPuffball(World world, double x, double y, double z) {
		
		super(world, x, y, z);
	}

	@Override
	public void onImpact(MovingObjectPosition mop) {
		
		if (mop.typeOfHit == mop.typeOfHit.BLOCK)
		{
			switch(mop.sideHit)
			{
			case 0: 
			{ 
//				System.out.println("bottom"); 
				setBlockIfAir(this.worldObj, mop.blockX, mop.blockY - 1, mop.blockZ, Blocks.web);
				this.setDead(); 
				break; 
				}
			case 1: 
			{ 
//				System.out.println("top");
				setBlockIfAir(this.worldObj, mop.blockX, mop.blockY + 1, mop.blockZ, Blocks.web);
				this.setDead(); 
				break; 
				}
			case 2: 
			{ 
//				System.out.println("east");
				setBlockIfAir(this.worldObj, mop.blockX - 1, mop.blockY, mop.blockZ, Blocks.web);
				this.setDead(); 
				break; 
				}
			case 3: 
			{ 
//				System.out.println("west");
				setBlockIfAir(this.worldObj, mop.blockX + 1, mop.blockY, mop.blockZ, Blocks.web);
				this.setDead(); 
				break; 
				}
			case 4: 
			{ 
//				System.out.println("north");
				setBlockIfAir(this.worldObj, mop.blockX, mop.blockY, mop.blockZ - 1, Blocks.web);
				this.setDead(); 
				break; 
				}
			case 5: 
			{ 
//				System.out.println("south");
				setBlockIfAir(this.worldObj, mop.blockX, mop.blockY - 1, mop.blockZ + 1, Blocks.web);
				this.setDead(); 
				break; 
				}
			}
		}
		if (mop.typeOfHit == mop.typeOfHit.ENTITY)
		{
			int xPos = MathHelper.floor_double(mop.entityHit.posX);
			int yPos = MathHelper.floor_double(mop.entityHit.posY);
			int zPos = MathHelper.floor_double(mop.entityHit.posZ);
			
			setBlockIfAir(this.worldObj, xPos, yPos, zPos, Blocks.web);
		}
	}
	
	private boolean setBlockIfAir(World world, int x, int y, int z, Block block) {
		
		if (world.isAirBlock(x, y, z))
		{
			world.setBlock(x, y, z, block);
			return true;
		}
		return false;
	}
}
