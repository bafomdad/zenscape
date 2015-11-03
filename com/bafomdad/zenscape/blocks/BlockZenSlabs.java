package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bafomdad.zenscape.ZenScape;

public class BlockZenSlabs extends BlockSlab {

	public BlockZenSlabs(String unlocalizedname, boolean isDoubleSlab, Material material) {
		
		super(isDoubleSlab, material);
		setBlockName(unlocalizedname);
		useNeighborBrightness = true;
	}

	@Override
	public String func_150002_b(int metadata) {

		switch (metadata & 7) 
		{
			case 0: return this.getUnlocalizedName() + "_roughgreen";
			case 1: return this.getUnlocalizedName() + "_smoothgreen";
			case 2: return this.getUnlocalizedName() + "_roughred";
			case 3: return this.getUnlocalizedName() + "_smoothred";
			case 4: return this.getUnlocalizedName() + "_roughblue";
			case 5: return this.getUnlocalizedName() + "_smoothblue";
			case 6: return this.getUnlocalizedName() + "_roughblack";
			case 7: return this.getUnlocalizedName() + "_smoothblack";
			default: return this.getUnlocalizedName();
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		switch (meta & 7)
		{
			case 0: return ZenScape.blockZenBricks.getIcon(side, 0);
			case 1: return ZenScape.blockZenBricks.getIcon(side, 16);
			case 2: return ZenScape.blockZenBricks.getIcon(side, 4);
			case 3: return ZenScape.blockZenBricks.getIcon(side, 17);
			case 4: return ZenScape.blockZenBricks.getIcon(side, 8);
			case 5: return ZenScape.blockZenBricks.getIcon(side, 18);
			case 6: return ZenScape.blockZenBricks.getIcon(side, 12);
			case 7: return ZenScape.blockZenBricks.getIcon(side, 19);
			default: return Blocks.stone.getIcon(0, 0);
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ZenScape.slab);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		
		return this.field_150004_a ? 2 : 1;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		if (!this.field_150004_a) {
			for (int i = 0; i < 8; i++) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition mop, World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y, z);
		
		if (block == null) return null;
		if (block == ZenScape.double_slab) block = ZenScape.slab;
		
		int meta = world.getBlockMetadata(x, y, z) & 7;
		return new ItemStack(block, 1, meta);
	}
	
	public static class ItemBlockZenSlabs extends ItemSlab {
		
		public ItemBlockZenSlabs(Block block, BlockZenSlabs single_slab, BlockZenSlabs double_slab, Boolean isDoubleSlab) {
			
			super(block, single_slab, double_slab, isDoubleSlab);
		}
	}
}
