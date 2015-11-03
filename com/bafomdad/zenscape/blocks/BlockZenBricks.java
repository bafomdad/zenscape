package com.bafomdad.zenscape.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenBricks extends Block {
	
	@SideOnly(Side.CLIENT)
	public IIcon[] iconArray;
	
	public BlockZenBricks(Material material) {
		
		super(material);
	}
	
	public int damageDropped(int par1) {
		
		return par1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		iconArray = new IIcon[20];
		for (int i = 0; i < iconArray.length; i++) 
		{
			iconArray[i] = register.registerIcon("zenscape" + ":" + this.textureName + i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		
		switch(par2)
		{
		case 0:
			return iconArray[0];
		case 1:
			return iconArray[1];
		case 2:
		{
			switch(par1)
			{
			case 0: return iconArray[16];
			case 1: return iconArray[16];
			default: return iconArray[2];
			}
		}
		case 3:
		{
			switch(par1)
			{
			case 0: return iconArray[16];
			case 1: return iconArray[16];
			default: return iconArray[3];
			}
		}
		case 4:
			return iconArray[4];
		case 5:
			return iconArray[5];
		case 6:
		{
			switch(par1)
			{
			case 0: return iconArray[17];
			case 1: return iconArray[17];
			default: return iconArray[6];
			}
		}
		case 7:
			return iconArray[7];
		case 8:
			return iconArray[8];
		case 9:
			return iconArray[9];
		case 10:
		{
			switch(par1)
			{
			case 0: return iconArray[18];
			case 1: return iconArray[18];
			default: return iconArray[10];
			}
		}
		case 11:
			return iconArray[11];
		case 12:
			return iconArray[12];
		case 13:
			return iconArray[13];
		case 14:
		{
			switch(par1)
			{
			case 0: return iconArray[19];
			case 1: return iconArray[19];
			default: return iconArray[14];
			}
		}
		case 15:
			return iconArray[15];
		case 16:
			return iconArray[16];
		case 17:
			return iconArray[17];
		case 18:
			return iconArray[18];
		case 19:
			return iconArray[19];
		default:
		{
			return iconArray[0];
			}
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < 16; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public static class ItemZenBricks extends ItemBlock {

		public ItemZenBricks(Block block) {
			
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
