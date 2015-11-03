package com.bafomdad.zenscape.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSkybeam extends Block implements ITileEntityProvider {

	public BlockSkybeam(Material material) {
		
		super(material);
		setHardness(50.0F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
		setBlockName("zenscape" + "." + "blockSkybeam");
	}

	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileSkybeam();
	}
	
	public static class TileSkybeam extends TileEntity {
		
		public int powerlevel;
		public static int MAX_POWER = 10;
		public boolean powered;
		
		public double getMaxRenderDistanceSquared() {
			
			return 65536.0D;
		}
		
		public AxisAlignedBB getRenderBoundingBox() {
			
			return TileEntity.INFINITE_EXTENT_AABB;
		}
		
		public boolean shouldRenderInPass(int pass) {
			
			return pass == 1;
		}
		
		public boolean canUpdate() {
			
			return true;
		}
		
		public void updateEntity() {
			
			if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)){
				
				if (!this.powered){}
				this.powerlevel = Math.min(this.powerlevel + 1, MAX_POWER);
				this.powered = true;
			}
			else
			{
				if (this.powered) {}
				this.powerlevel = Math.max(this.powerlevel - 1, 0);
				this.powered = false;
			}
		}
	}
}
