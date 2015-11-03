package com.bafomdad.zenscape.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;

public class BlockTestGear extends Block implements ITileEntityProvider {

	public BlockTestGear(Material material) {
		
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		return new BlockTestGear.TileTestGear();
	}
	
	@Override
    public boolean shouldSideBeRendered(IBlockAccess block, int x, int y, int z, int side) {
		
		return false;
	}
	
	@Override
    public boolean renderAsNormalBlock() {
        
		return false;
    }
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	public static class TileTestGear extends TileEntityZenScape {
	
	}
}
