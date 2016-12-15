package com.bafomdad.zenscape.blocks.unobtainium;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.render.ZenTextureStitch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenSkyBush extends BlockZenScape implements IShearable {
	
	public IIcon skyBush;

	public BlockZenSkyBush(Material material) {
		
		super(material);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
//		this.skyBush = ZenScape.texBush;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		if (event.map.getTextureType() == 0) {
			TextureAtlasSprite icon = new ZenTextureStitch("zenscape:bush_tex");
			if (event.map.setTextureEntry("zenscape:bush_tex", icon))
				skyBush = icon;
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		return this.skyBush;
	}
	
	@Override
	public int getRenderType() {
		
		return 1;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {

		return true;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		
		return true;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		
		return 0;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {

		ArrayList<ItemStack> ret = new ArrayList();
		ret.add(new ItemStack(ZenScape.blockZenBush));
		return ret;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	
		return null;
    }
}
