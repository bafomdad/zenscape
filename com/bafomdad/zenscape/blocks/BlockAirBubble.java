package com.bafomdad.zenscape.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAirBubble extends Block {

	public BlockAirBubble(Material material) {
		
		super(material);
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)p_149668_3_, (double)p_149668_4_, (double)(p_149668_2_ + 1), (double)((float)(p_149668_3_ + 1) - f), (double)(p_149668_4_ + 1));
    }
	
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	
    	if (!(entity instanceof EntityLivingBase))
    		return;
    	if (!((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
    		((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
    }
    
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		world.spawnParticle("bubble", x + rand.nextFloat(), (y + 1) + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.5D, 0.0D);
	}
}
