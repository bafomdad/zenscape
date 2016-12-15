package com.bafomdad.zenscape.blocks.unobtainium;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.render.ZenTextureStitch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenLeaf extends BlockZenScape {
	
	public IIcon leafIcon;

	public BlockZenLeaf(Material material) {
		
		super(material);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		
//		this.leafIcon = ZenScape.texLeaves;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		if (event.map.getTextureType() == 0) {
			TextureAtlasSprite icon = new ZenTextureStitch("zenscape:leaves_flywood_tex");
			if (event.map.setTextureEntry("zenscape:leaves_flywood_tex", icon))
				leafIcon = icon;
		}
	}
	
	@Override
	public IIcon getIcon(int par1, int par2) {
		
		return leafIcon;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		
		return 0;
	}
}
