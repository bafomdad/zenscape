package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.zenscape.crafting.ZCrafting;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCraftBox extends Block implements ITileEntityProvider {

	public BlockCraftBox(Material material) {
		
		super(material);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.55F, 1.0F);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess block, int x, int y, int z, int side) {
	
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	public int getRenderType() {
		
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new BlockCraftBox.TileCraftBox();
	}
	
	public static class TileCraftBox extends TileEntity {
		
		private boolean startCrafting = false, stopCrafting = false;
		
		public List<BlockCraftTree.TileCraftTree> tiles = new ArrayList<BlockCraftTree.TileCraftTree>(), tilesToGetFrom = new ArrayList<BlockCraftTree.TileCraftTree>();
			
		@Override
		public void updateEntity() {

			super.updateEntity();
			
			TileCraftBox tcb = (TileCraftBox)worldObj.getTileEntity(xCoord, yCoord, zCoord);
			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1D / 16D * 8D, zCoord, xCoord + 1, yCoord + 1D / 16D * 9D, zCoord + 1));
			for (EntityItem item : items)
			{
				if (item != null) {
					if (item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.sapling))
					{
						if (!tcb.startCrafting)
							tcb.startCrafting = true;
						if (tcb.stopCrafting)
						{
							tcb.stopCrafting = false;
							if (worldObj.isRemote)
								tcb.spawnEffects(worldObj, xCoord, yCoord, zCoord);
							item.setDead();
						}
					}
				}
			}
			
			if (tcb != null && tcb.startCrafting) 
			{
				this.addPossibleBlocks(4, 1, 4);
				this.addPossibleBlocks(-4, 1, -4);
				this.addPossibleBlocks(4, 1, -4);
				this.addPossibleBlocks(-4, 1, 4);
				this.addPossibleBlocks(4, 1, 0);
				this.addPossibleBlocks(0, 1, 4);
				this.addPossibleBlocks(-4, 1, 0);
				this.addPossibleBlocks(0, 1, -4);
				
				if (tiles.isEmpty()) {
					return;
				}
				else {
					for (int i = 0; i < tiles.size(); i++) {
						BlockCraftTree.TileCraftTree tile = tiles.get(i);
						if (tile != null) {
							ItemStack tileItem = tile.getStackInSlot(0);
							if (tileItem != null) {
								tilesToGetFrom.add(tile);
							}
						}
					}
					if (!tilesToGetFrom.isEmpty()) {
						for (ZCrafting recipe : ZCrafting.recipes) {
							if (recipe.matches(tcb))
							{
								tcb.stopCrafting = true;
								clearSlots();
								if (!worldObj.isRemote) {
									ItemStack output = recipe.getOutput().copy();
									EntityItem outputItem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, output);
									worldObj.spawnEntityInWorld(outputItem);
								}
								reset();
							}
						}
						reset();
					}
				}
			}
			tiles.clear();
		}
		
		public void addPossibleBlocks(int x, int y, int z) {
			
			TileEntity tile = worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z);
			if (tile != null && tile instanceof BlockCraftTree.TileCraftTree) {
				if (!tiles.contains((BlockCraftTree.TileCraftTree) tile))
					tiles.add((BlockCraftTree.TileCraftTree) tile);
			}
		}
		
		private void reset() {
			
			startCrafting = false;
			tilesToGetFrom.clear();
		}
		
		private void clearSlots() {
			
			for (int i = 0; i < tilesToGetFrom.size(); i++) {
				BlockCraftTree.TileCraftTree slots = tilesToGetFrom.get(i);
				if (slots != null) {
					ItemStack slotItem = slots.getStackInSlot(0);
					if (slotItem != null) {
						slots.setInventorySlotContents(0, null);
						slots.markDirty();
						if (worldObj.isRemote)					
							this.spawnEffects2(slots, slots.xCoord, slots.yCoord, slots.zCoord);
					}
				}
			}
		}
		
		private void spawnEffects(World world, double x, double y, double z) {
			
			world.spawnParticle("flame", x + world.rand.nextFloat(), y + 1.1, z + world.rand.nextFloat(), 0, 0, 0);
			world.spawnParticle("flame", x + world.rand.nextFloat(), y + 1.1, z + world.rand.nextFloat(), 0, 0, 0);
			world.spawnParticle("flame", x + world.rand.nextFloat(), y + 1.1, z + world.rand.nextFloat(), 0, 0, 0);
			world.spawnParticle("flame", x + world.rand.nextFloat(), y + 1.1, z + world.rand.nextFloat(), 0, 0, 0);
		}
		
		private void spawnEffects2(TileEntity tile, int x, int y, int z) {
			
			worldObj.spawnParticle("cloud", x + worldObj.rand.nextFloat(), y + 0.5, z + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("cloud", x + worldObj.rand.nextFloat(), y + 0.5, z + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("cloud", x + worldObj.rand.nextFloat(), y + 0.5, z + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("cloud", x + worldObj.rand.nextFloat(), y + 0.5, z + worldObj.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}
}
