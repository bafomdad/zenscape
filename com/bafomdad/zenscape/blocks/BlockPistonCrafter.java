package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bafomdad.zenscape.crafting.ZPistonCraft;

public class BlockPistonCrafter extends Block implements ITileEntityProvider {

	public BlockPistonCrafter(Material material) {
		
		super(material);
	}
	
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	TilePistonCrafter te = (TilePistonCrafter)world.getTileEntity(x, y, z);
        if (!world.isRemote)
        {
            if (!te.isPowered && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                te.isPowered = true;
            }
            else if (te.isPowered && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
            	te.isPowered = false;
            }
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new BlockPistonCrafter.TilePistonCrafter();
	}
	
	public static class TilePistonCrafter extends TileEntity {
		
		boolean isPowered = false;
		private int timer = -1;
		private boolean flag = false;
		
		public List<ItemStack> itemList = new ArrayList<ItemStack>();
		
		public void updateEntity() {
			
			if (this.isPowered)
			{	
				Block sapling = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
				int saplingmeta = worldObj.getBlockMetadata(this.xCoord, this.yCoord + 2, this.zCoord);
				
				this.getValidBlocks(1, 2, 1);
				this.getValidBlocks(-1, 2, -1);
				this.getValidBlocks(1, 2, -1);
				this.getValidBlocks(-1, 2, 1);
				this.getValidBlocks(1, 2, 0);
				this.getValidBlocks(0, 2, 1);
				this.getValidBlocks(-1, 2, 0);
				this.getValidBlocks(0, 2, -1);

				
				if (isAirBlocks() && sapling == Blocks.sapling && saplingmeta == 1) {
//					System.out.println("true");
					flag = true;
				}
				/*
				if ((++timer >= 0 && ++timer <= 6) && itemList.isEmpty()) {
					flag = true;
				}
				*/
				if (++timer > 6) {
					if (itemList.size() == 4 && flag && sapling != null && sapling == Blocks.sapling && saplingmeta == 1) {
						for (ZPistonCraft recipe : ZPistonCraft.pRecipes) {
							if (recipe.matches(this)) {
								reset();
								wipeIngredients();
								spawnEffects(0, 0, 0);
							
								if (!worldObj.isRemote)
								{
									Block output = (Block)recipe.getOutput();
									int meta = recipe.getOutputMeta();
									worldObj.setBlock(xCoord, yCoord + 2, zCoord, output, meta, 2);
								}
							}
							reset();
						}
					}
					if (itemList.size() == 8 && flag && sapling != null && sapling == Blocks.sapling && saplingmeta == 1) {
						for (ZPistonCraft recipe : ZPistonCraft.pRecipes) {
							if (recipe.matches(this)) {
								reset();
								wipeIngredients();
								spawnEffects(0, 0, 0);
								
								if (!worldObj.isRemote)
								{
									Block output = (Block)recipe.getOutput();
									int meta = recipe.getOutputMeta();
									worldObj.setBlock(xCoord, yCoord + 2, zCoord, output, meta, 2);
								}
							}
							reset();
						}
					}
				}	
				if (++timer >= 16) {
					reset();
				}
			}
			itemList.clear();
		}
		
		private void reset() {
			
			this.timer = -1;
			this.flag = false;
		}

		private void getValidBlocks(int x, int y, int z) {
			
			Block block = worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
			int meta = worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z);
			if (block instanceof BlockLog)
				meta = meta & 0x3;
			
			ItemStack stack = new ItemStack(block, 1, meta);
			
			if (block != null && !worldObj.isAirBlock(xCoord + x, yCoord + y, zCoord + z) && block.getMobilityFlag() == 0)
				itemList.add(stack);
		}
		
		private void wipeIngredients() {
			
			worldObj.setBlockToAir(this.xCoord + 1, this.yCoord + 2, this.zCoord + 1);
			worldObj.setBlockToAir(this.xCoord - 1, this.yCoord + 2, this.zCoord - 1);
			worldObj.setBlockToAir(this.xCoord + 1, this.yCoord + 2, this.zCoord - 1);
			worldObj.setBlockToAir(this.xCoord - 1, this.yCoord + 2, this.zCoord + 1);
			worldObj.setBlockToAir(this.xCoord + 1, this.yCoord + 2, this.zCoord);
			worldObj.setBlockToAir(this.xCoord - 1, this.yCoord + 2, this.zCoord);
			worldObj.setBlockToAir(this.xCoord, this.yCoord + 2, this.zCoord + 1);
			worldObj.setBlockToAir(this.xCoord, this.yCoord + 2, this.zCoord - 1);
		}
		
		private void spawnEffects(int x, int y, int z) {
			
			World world = Minecraft.getMinecraft().theWorld;
			world.spawnParticle("cloud", x + xCoord + worldObj.rand.nextFloat(), y + yCoord + 2.5, z + zCoord + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", x + xCoord + worldObj.rand.nextFloat(), y + yCoord + 2.5, z + zCoord + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", x + xCoord + worldObj.rand.nextFloat(), y + yCoord + 2.5, z + zCoord + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", x + xCoord + worldObj.rand.nextFloat(), y + yCoord + 2.5, z + zCoord + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
		
		private boolean isAirBlocks() {
			
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			
			if (worldObj.isAirBlock(x + 1, y + 2, z + 1)
					&& worldObj.isAirBlock(x - 1, y + 2, z - 1)
					&& worldObj.isAirBlock(x + 1, y + 2, z - 1)
					&& worldObj.isAirBlock(x - 1, y + 2, z + 1)
					&& worldObj.isAirBlock(x + 1, y + 2, z)
					&& worldObj.isAirBlock(x - 1, y + 2, z)
					&& worldObj.isAirBlock(x, y + 2, z + 1)
					&& worldObj.isAirBlock(x, y + 2, z - 1)) {
				return true;
			}
			return false;
		}
	}
}
