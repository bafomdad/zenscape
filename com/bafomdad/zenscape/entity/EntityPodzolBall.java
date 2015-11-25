package com.bafomdad.zenscape.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPodzolBall extends EntityThrowable {
	
	public EntityPodzolBall(World world) {
		
		super(world);
	}

	public EntityPodzolBall(World world, EntityLivingBase elb) {
		
		super(world, elb);
	}
	
	public EntityPodzolBall(World world, double x, double y, double z) {
		
		super(world, x, y, z);
	}

	@Override
	public void onImpact(MovingObjectPosition mop) {
		
		if (mop.typeOfHit == mop.typeOfHit.BLOCK)
		{
			for (int i = -2; i <= 2; i++) {
				for (int j = -2; j <= 2; j++) {
					for (int k = -2; k <= 2; k++) {
						if (i * i + j * j + k * k < (2 + 0.5) * (2 + 0.5))
						{
							int x = mop.blockX + i;
							int y = mop.blockY + j;
							int z = mop.blockZ + k;
							
							Block loopblock = this.worldObj.getBlock(x, y, z);
							int meta = this.worldObj.getBlockMetadata(x, y, z);
							boolean isAir = this.worldObj.isAirBlock(x, y + 1, z);
							
							if (loopblock != null && loopblock == Blocks.dirt && meta == 0 && isAir)
							{
								this.worldObj.setBlock(x, y, z, Blocks.dirt, 2, 3);
								if (this.worldObj.isRemote)
									this.worldObj.spawnParticle("cloud", x + 0.5, y + 1, z + 0.5, 0.0D, 0.05D, 0.0D);
							}
						}
					}
				}
			}
			this.setDead();
		}
		else
			this.setDead();
	}
}
