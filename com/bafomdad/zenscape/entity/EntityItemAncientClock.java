package com.bafomdad.zenscape.entity;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import thaumcraft.codechicken.lib.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemAncientClock extends EntityItem {
	
	private int range = 2;

	public EntityItemAncientClock(World world) {
		
		super(world);
	}
	
	public EntityItemAncientClock(World world, EntityItem oldEntity, ItemStack stack) {
		
		super(world, oldEntity.posX, oldEntity.posY, oldEntity.posZ, stack);
		this.motionX = oldEntity.motionX;
		this.motionY = oldEntity.motionY;
		this.motionZ = oldEntity.motionZ;
		this.age = oldEntity.age;
		this.delayBeforeCanPickup = oldEntity.delayBeforeCanPickup;
	}
	
	@Override
	public void onUpdate() {
		
		if (this.age % 300 == 0) {
			
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY);
			int k = MathHelper.floor_double(this.posZ);
			
			for (int x1 = -range; x1 <= range; x1++) {
				for (int y1 = -range; y1 <= range; y1++) {
					for (int z1 = -range; z1 <= range; z1++)
					{
						int x = x1 + i;
						int y = y1 + j;
						int z = z1 + k;
						
						Block block = worldObj.getBlock(x, y, z);
						Random rand = new Random();
						
						if (block == null)
							continue;
						
						if (rand.nextInt(10) == 0) {
							if (!worldObj.isRemote) {
								if (block == Blocks.grass)
									worldObj.setBlock(x, y, z, ZenScape.blockAncientGrass);
								if (block == Blocks.gravel)
									worldObj.setBlock(x, y, z, ZenScape.blockAncientGravel);
								if (block == Blocks.cobblestone)
									worldObj.setBlock(x, y, z, ZenScape.blockAncientStone, 0, 3);
								if (block == Blocks.mossy_cobblestone)
									worldObj.setBlock(x, y, z, ZenScape.blockAncientStone, 1, 3);
								if (block == Blocks.brick_block)
									worldObj.setBlock(x, y, z, ZenScape.blockAncientStone, 2, 3);
							}
							for (int l = 0; l < rand.nextInt(4) + 1; l++) {
								worldObj.spawnParticle("cloud", x + rand.nextFloat(), y + 1 + rand.nextFloat(), z + rand.nextFloat(), 0, 0, 0);
							}
						}
					}
				}
			}
		}
		super.onUpdate();
	}
}
