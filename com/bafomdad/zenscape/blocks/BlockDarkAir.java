package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDarkAir extends Block {
	
	public BlockDarkAir(Material material) {
		
		super(material);
		disableStats();
	}

    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
    	
    	return 7;
    }
    
    public int quantityDropped(Random rand) {
    	
    	return 0;
    }
    
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
    	
    	return new ArrayList<ItemStack>();
    }
    
    public int getRenderType() {
    	
    	return -1;
    }
    
    public boolean isOpaqueCube() {
    	
    	return false;
    }
    
    public boolean renderAsNormalBlock() {
    	
    	return false;
    }
    
    public boolean isAir(IBlockAccess world, int x, int y, int z) {
    	
    	return true;
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
    	
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	
    	return null;
    }
    
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
    	
    	return null;
    }
    
    public boolean canCollideCheck(int par1, boolean par2) {
    	
    	return false;
    }
    
    public boolean isCollidable() {
    	
    	return false;
    }
    
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec1, Vec3 v2) {
    	
    	return null;
    }
    
    public final void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	
    }
    
    public int getMobilityFlag() {
    	
    	return 1;
    }
    
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
    	
    	return true;
    }
}
