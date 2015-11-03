package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBlackMesa extends Block {
	
	@SideOnly(Side.CLIENT)
	public IIcon[] iconArray;

	public BlockBlackMesa(Material material) {
		
		super(material);
	}
	
	public int damageDropped(int par1) {
		
		return par1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		iconArray = new IIcon[6];
		for (int i = 0; i < iconArray.length; i++) 
		{
			iconArray[i] = register.registerIcon("zenscape:" + this.textureName + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {

		return this.iconArray[meta % this.iconArray.length];
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < 6; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public static class ItemBlackMesa extends ItemBlock {

		public ItemBlackMesa(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
	}
}
