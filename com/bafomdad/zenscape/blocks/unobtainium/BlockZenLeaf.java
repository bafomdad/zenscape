package com.bafomdad.zenscape.blocks.unobtainium;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

public class BlockZenLeaf extends BlockZenScape implements IShearable {
	
	public IIcon leafIcon;

	public BlockZenLeaf(Material material) {
		
		super(material);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		
		this.leafIcon = ZenScape.texLeaves;
	}
	
	@Override
	public IIcon getIcon(int par1, int par2) {
		
		return leafIcon;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {

		return true;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		
		return 0;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {

		ArrayList<ItemStack> ret = new ArrayList();
		ret.add(new ItemStack(ZenScape.blockZenLeaves, 1, 3));
		return ret;
	}
}
