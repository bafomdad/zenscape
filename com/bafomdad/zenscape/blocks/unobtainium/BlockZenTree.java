package com.bafomdad.zenscape.blocks.unobtainium;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.render.ZenTextureStitch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenTree extends BlockZenScape {
	
	public IIcon treeIcon;
	public IIcon topIcon;

	public BlockZenTree(Material material) {
		
		super(material);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
//		this.treeIcon = ZenScape.texLogSide;
//		this.topIcon = ZenScape.texLogTop;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		if (event.map.getTextureType() == 0) {
			TextureAtlasSprite icon = new ZenTextureStitch("zenscape:flywood_tex");
			if (event.map.setTextureEntry("zenscape:flywood_tex", icon))
				treeIcon = icon;
			
			icon = new ZenTextureStitch("zenscape:flywood_top_tex");
			if (event.map.setTextureEntry("zenscape:flywood_top_tex", icon))
				topIcon = icon;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		switch(side) 
		{
			case 0: return topIcon;
			case 1: return topIcon;
			default: return treeIcon;
		}
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		
		this.dropBlockAsItem(world, x, y, z, new ItemStack(ZenScape.blockZenLog2, 1, 0));
	}
}
