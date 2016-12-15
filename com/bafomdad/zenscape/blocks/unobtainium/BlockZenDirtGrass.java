package com.bafomdad.zenscape.blocks.unobtainium;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.render.ZenTextureStitch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenDirtGrass extends BlockZenScape {
	
	public IIcon grassSide, grassTop, dirt, fossil;

	public BlockZenDirtGrass(Material material) {
		
		super(material);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
//		grassSide = ZenScape.texGrassSide;
//		grassTop = ZenScape.texGrassTop;
//		dirt = ZenScape.texGrassBottom;
//		fossil = ZenScape.texDirtFossil;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		if (event.map.getTextureType() == 0) {
			TextureAtlasSprite icon = new ZenTextureStitch("zenscape:zengrass_side");
			if (event.map.setTextureEntry("zenscape:zengrass_side", icon))
				grassSide = icon;
			
			icon = new ZenTextureStitch("zenscape:zengrass_top");
			if (event.map.setTextureEntry("zenscape:zengrass_top", icon))
				grassTop = icon;
			
			icon = new ZenTextureStitch("zenscape:zengrass_bottom");
			if (event.map.setTextureEntry("zenscape:zengrass_bottom", icon))
				dirt = icon;
			
			icon = new ZenTextureStitch("zenscape:dirt");
			if (event.map.setTextureEntry("zenscape:zendirt", icon))
				fossil = icon;
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		switch(meta) 
		{
		case 0:
		{
			switch(side)
			{
			case 0: return dirt;
			case 1: return grassTop;
			default: return grassSide;
			}
		}
		case 1:
			return dirt;
		case 2:
			return fossil;
		default:
			return dirt;
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
	}
	
	public static class ItemSoil extends ItemBlock {
		
		public ItemSoil(Block block) {
			
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
