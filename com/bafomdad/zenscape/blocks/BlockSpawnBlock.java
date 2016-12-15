package com.bafomdad.zenscape.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

public class BlockSpawnBlock extends BlockZenScape implements ITileEntityProvider {
	
	public static String TAG_SPAWNTREASURE = "ZenScape_spawnTreasure";
	public static String TAG_XLOC = "ZenScape_xLoc";
	public static String TAG_YLOC = "ZenScape_yLoc";
	public static String TAG_ZLOC = "ZenScape_zLoc";

	public BlockSpawnBlock(Material material) {
		
		super(material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if (meta == 0)
			return new BlockSpawnBlock.TileSpawnBlock();
		else
			return null;
	}
	
	public static class TileSpawnBlock extends TileEntity {
		
		@Override
		public void updateEntity() {
			
			if ((worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8.0D) != null) && (!worldObj.isRemote))
			{
				EntityVillager entityV = new EntityVillager(worldObj);
				entityV.setPosition(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5);
				NBTTagCompound tag = entityV.getEntityData();
				tag.setBoolean(TAG_SPAWNTREASURE, true);
				tag.setInteger(TAG_XLOC, xCoord);
				tag.setInteger(TAG_YLOC, yCoord);
				tag.setInteger(TAG_ZLOC, zCoord);
				worldObj.spawnEntityInWorld(entityV);
//				worldObj.setBlock(xCoord, yCoord, zCoord, ZenScape.blockSpawnBlock, 1, 3);
				this.selfDisguise();
			}
		}
		
		private void selfDisguise() {
			
			for (int i = -1; i < 1 + 1; i++) {
				Block block = worldObj.getBlock(this.xCoord + i, this.yCoord, this.zCoord + i);
				if (block != null && block != ZenScape.blockSpawnBlock && block != Blocks.air)
				{
					worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, block);
				}
				else
				{
					worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
				}
			}
		}
	}
}
