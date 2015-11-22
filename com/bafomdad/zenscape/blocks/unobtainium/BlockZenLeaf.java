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

public class BlockZenLeaf extends BlockZenScape {
	
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
	public int quantityDropped(Random rand) {
		
		return 0;
	}
}
