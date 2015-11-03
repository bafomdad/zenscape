package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDizzyBlock extends Block {

	public BlockDizzyBlock(Material material) {
		
		super(material);
        float f = 0.5F;
        float f1 = 0.015625F;
        setTickRandomly(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.blockIcon = register.registerIcon("zenscape:" + textureName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		return blockIcon;
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		world.scheduleBlockUpdate(x, y, z, this, 20);
		EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
		if (!world.isRemote && player != null)
		{
			MovingObjectPosition mop = player.rayTrace(10, 1);
			Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if (block == this)
			{
				player.addPotionEffect(new PotionEffect(9, 100, 0));
			}
		}
	}
}
