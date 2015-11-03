package com.bafomdad.zenscape.blocks.unobtainium;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenSkyBush extends BlockZenScape implements IShearable {
	
	public IIcon skyBush;

	public BlockZenSkyBush(Material material) {
		
		super(material);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		skyBush = ZenScape.texBush;
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		return skyBush;
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
