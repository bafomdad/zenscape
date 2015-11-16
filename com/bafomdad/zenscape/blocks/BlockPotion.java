package com.bafomdad.zenscape.blocks;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockPotion extends Block {

	public BlockPotion(Material material) {
		
		super(material);
	}
	
	public int getRenderType() {
		
		return 1;
	}
	
    public boolean canSilkHarvest() {
    	
    	return false;
    }
	
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	public Item getItemDropped(int i, Random rand, int j) {
		
		return ZenScape.itemAlchemyBottles;
	}
	
	public int damageDropped(int meta) {
		
		return 6;
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        
    	return null;
    }
    
    public boolean canBlockStay(World world, int x, int y, int z) {
    	
    	Block block = world.getBlock(x, y + 1, z);
    	int meta = world.getBlockMetadata(x, y + 1, z);
    	
    	return block == ZenScape.blockZenLeaves && meta == 8;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	if (!this.canBlockStay(world, x, y, z)) {
    		world.setBlockToAir(x, y, z);
    	}
    }
}
