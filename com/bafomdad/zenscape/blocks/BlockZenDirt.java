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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenDirt extends Block {
	
	@SideOnly(Side.CLIENT)
	IIcon dirt, fossil;

	public BlockZenDirt(Material material) {
		
		super(material);
	}
	
	public int damageDropped(int par1) {
		
		return par1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		dirt = register.registerIcon("zenscape:dirt0");
		fossil = register.registerIcon("zenscape:dirt1");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		switch(meta) 
		{
		case 0:
			return dirt;
		case 1:
			return fossil;
		default:
			return dirt;
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	public static class ItemZenDirt extends ItemBlock {

		public ItemZenDirt(Block block) {
			
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
