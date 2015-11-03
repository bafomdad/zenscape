package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockZenBush extends Block implements IShearable {

	public BlockZenBush(Material material) {
		
		super(material);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}
	
	@Override
	public int getRenderType() {
		
		return 1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	
		return null;
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y - 1, z);
		return ((block == ZenScape.blockZenGrass) && (block.isSideSolid(world, x, y, z, ForgeDirection.UP)));
	}
	
	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
		if (!this.canPlaceBlockAt(world, x, y, z))
		{
			world.setBlockToAir(x, y, z);
		}
    }
	
	@Override
    public boolean canBlockStay(World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y - 1, z);
        return block == ZenScape.blockZenGrass && block == ZenScape.blockZenDirt;
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
		ret.add(new ItemStack(ZenScape.blockZenBush));
		return ret;
	}
}
