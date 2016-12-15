package com.bafomdad.zenscape.blocks.ancientblocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockAncientStone extends Block {
	
	@SideOnly(Side.CLIENT)
	public IIcon brick, cobble, cobblemoss;

	public BlockAncientStone(Material material) {
		
		super(material);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(soundTypeStone);
	}
	
	@Override
	public int damageDropped(int meta) {
		
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		cobble = register.registerIcon("zenscape:ancient_cobble");
		cobblemoss = register.registerIcon("zenscape:ancient_cobblemoss");
		brick = register.registerIcon("zenscape:ancient_brick");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {

		switch(meta) 
		{
			case 1: return cobblemoss;
			case 2: return brick;
			default: return cobble;
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < 3; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public static class ItemAncientStone extends ItemBlock {

		public ItemAncientStone(Block block) {
			
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
