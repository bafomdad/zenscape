package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenLog2 extends BlockLog {

	public IIcon[] icons;
	public IIcon[] top;
	public String[] textureNames = new String[] { "log_flywood", "log_poison", "log_enchant", "log_joker" };
	
	
	public BlockZenLog2(Material material) {
			
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int par1) {
		
		return this.icons[par1 % icons.length];
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int par1) {
        
		return this.top[par1 % this.top.length];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.icons = new IIcon[textureNames.length];
		this.top = new IIcon[textureNames.length];
		
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = register.registerIcon("zenscape:" + textureNames[i]);
			this.top[i] = register.registerIcon("zenscape:" + textureNames[i] + "_top");
		}
	}

	@Override
	public int damageDropped(int par1) {
		
		return par1 & 0x3;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		
		for (int i = 0; i < 4; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	public static class ItemZenscapeLog2 extends ItemBlock {
		
		public ItemZenscapeLog2(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + (stack.getItemDamage() & 0x3);
    	}
    	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
	}
}
