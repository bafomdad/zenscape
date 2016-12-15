package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockSand;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockZenSand extends BlockSand {

	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	@Override
	public int damageDropped(int meta) {
		
		return 0;
	}
	
	@Override
    public boolean canSilkHarvest() {
    	
		return false;
    }
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		
		return Item.getItemFromBlock(Blocks.sand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		icons = new IIcon[3];
		for (int i = 0; i < icons.length; i++) 
		{
			icons[i] = register.registerIcon("zenscape:" + this.textureName + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {

		return this.icons[meta % this.icons.length];
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
       
		ItemStack hoe = player.getCurrentEquippedItem();
		if (hoe != null && hoe.getItem() == ZenScape.itemZenHoe) {
			if (!world.isRemote)
			{
				int meta = world.getBlockMetadata(x, y, z);
				
				if (meta == 2)
					world.setBlockMetadataWithNotify(x, y, z, 0, 3);
				else
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				
				hoe.damageItem(1, player);
				return true;
			}
		}
		return false;
    }
	
	@Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		
		Block block = world.getBlock(x, y, z);
		if (block != null && block == this) {
			
			ItemStack hoe = player.getCurrentEquippedItem();
			if (hoe != null && hoe.getItem() == ZenScape.itemZenHoe) {
				if (!world.isRemote)
				{
					world.setBlock(x, y, z, Blocks.sand);
					hoe.damageItem(1, player);
				}
			}
		}
	}

	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < icons.length; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public static class ItemZenSand extends ItemBlock {

		public ItemZenSand(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
//    	@Override
//    	public String getUnlocalizedName(ItemStack stack) {
//    		
//    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
//    	}
	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
	}
}
