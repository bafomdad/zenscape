package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDarkGrass extends Block {

	public BlockDarkGrass(Material material) {
		
		super(material);
		setTickRandomly(true);
		float f = 0.4f;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
	
	@Override
    public int quantityDropped(Random rand) {
		
		return 0;
	}
	
	@Override
	public int tickRate(World world) {
		
		return 5;
	}
	
	@Override
	public int getRenderType() {
		
		return 1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y - 1, z);
		return (block != this && !world.isAirBlock(x, y - 1, z));
	}
	
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	
    	if (!(entity instanceof EntityLivingBase)) {
    		return;
    	}
    	entity.attackEntityFrom(DamageSource.outOfWorld, 1.5f);
    }

	@Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	
		if ((!world.isRemote) && (world.getClosestPlayer(x, y, z, 15.0D) != null) && !world.getClosestPlayer(x, y, z, 15.0D).capabilities.isCreativeMode)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			
			int distX = (int) (player.posX - x);
			int distY = (int) (player.posY - y);
			int distZ = (int) (player.posZ - z);
			
			int dirX = distX % 2;
			int dirY = distY % 2;
			int dirZ = distZ % 2;
				
			for (int y1 = -1; y1 < 2; y1++) {
				if (y1 != 0 && y1 != dirY) continue;
						
				if (world.isAirBlock(x + dirX, y + y1, z + dirZ) && this.canPlaceBlockAt(world, x + dirX, y + y1, z + dirZ))
				{
					world.setBlock(x + dirX, y + y1, z + dirZ, this);
					world.scheduleBlockUpdate(x + dirX, y + y1, z + dirZ, this, 20);
					break;
				}
			}
		}
    }

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
	
		if (rand.nextInt(2) == 0)
		{
			world.spawnParticle("depthsuspend", x + rand.nextFloat(), y + 0.85, z + rand.nextFloat(), 0.0D, 0.5D, 0.0D);
		}
	}
}
