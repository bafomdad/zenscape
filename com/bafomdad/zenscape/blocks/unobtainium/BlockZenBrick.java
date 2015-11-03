package com.bafomdad.zenscape.blocks.unobtainium;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenBrick extends BlockZenScape {
	
	public IIcon zenbrick0, zenbrick1, zenbrick2, zenbrick3, zenbrick16;

	public BlockZenBrick(Material material) {
		
		super(material);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		zenbrick0 = ZenScape.texBrick0;
		zenbrick1 = ZenScape.texBrick1;
		zenbrick2 = ZenScape.texBrick2;
		zenbrick3 = ZenScape.texBrick3;
		zenbrick16 = ZenScape.texBrick16;
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		switch(meta)
		{
		case 0:
			return zenbrick0;
		case 1:
			return zenbrick1;
		case 2:
		{
			switch(side)
			{
			case 0: return zenbrick16;
			case 1: return zenbrick16;
			default: return zenbrick2;
			}
		}
		case 3:
		{
			switch(side)
			{
			case 0: return zenbrick16;
			case 1: return zenbrick16;
			default: return zenbrick3;
			}
		}
		default:
			return zenbrick0;
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < 4; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		
		this.dropBlockAsItem(world, x, y, z, new ItemStack(ZenScape.blockZenBricks, 1, meta));
	}
	
	public static class ItemTexBricks extends ItemBlock {

		public ItemTexBricks(Block block) {
			
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
