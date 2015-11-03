package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockZenLog extends BlockLog {
	
	public IIcon[] icons;
	public IIcon[] top;
	public String[] textureNames = new String[] { "log_lightning", "log_crafting", "log_explosive", "log_nature" };
	
	public BlockZenLog(Material material) {
		
	}
	
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		
		int meta = world.getBlockMetadata(x, y, z) & 0x3;
		if (meta == 2) {
			return 50.0F;
		}
		return super.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
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
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		
		for (int i = 0; i < 4; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	public static class ItemZenscapeLog extends ItemBlock {
		
		public static final String blockType[] = { "lightning", "crafting", "explosive", "nature" };
		
		public ItemZenscapeLog(Block block) {
			
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