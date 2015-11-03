package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenGrass extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon top, side, bottom;

	public BlockZenGrass(Material material) {
		
		super(material);
		setTickRandomly(true);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {

		top = register.registerIcon("zenscape:grass_top");
		side = register.registerIcon("zenscape:grass_side");
		bottom = register.registerIcon("zenscape:dirt0");
	}
	
	@Override
	public IIcon getIcon(int par1, int par2) {
		
		switch(par2)
		{
		case 0:
		{
			switch(par1)
			{
			case 0: return bottom;
			case 1: return top;
			default: return side;
			}
		}
		default:
			return bottom;
		}
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if (!world.isRemote)
		{
			if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2)
			{
				world.setBlock(x, y, z, ZenScape.blockZenDirt, 0, 3);
			}
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int l = 0; l < 4; ++l)
				{
					int i1 = x + rand.nextInt(3) - 1;
					int j1 = y + rand.nextInt(5) - 3;
					int k1 = z + rand.nextInt(3) - 1;
					
					if (world.getBlock(i1, j1, k1) == ZenScape.blockZenDirt && world.getBlockMetadata(i1, j1, k1) == 0 && world.getBlockLightValue(i1, j1 + 1, k1) >= 4 && world.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
					{
						world.setBlock(i1, j1, k1, this);
					}
				}
			}
		}
	}
}
