package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.crafting.ZCrafting;
import com.bafomdad.zenscape.network.ZPacketDispatcher;
import com.bafomdad.zenscape.util.InvTreeCrafting;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
    
    	if (!world.isRemote)
    	{
    		ItemStack stack = player.getCurrentEquippedItem();
    		TileCraftBox tile = (TileCraftBox)world.getTileEntity(x, y, z);
    		if (tile != null)
    		{
        		if (stack != null && stack.getItem() == Items.compass && !tile.normalCrafting)
        		{
        			tile.normalCrafting = true;
        			stack.stackSize--;
        			ZPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
        			return true;
        		}
        		if (stack == null && tile.normalCrafting)
        		{
        			tile.normalCrafting = false;
        			player.inventory.addItemStackToInventory(new ItemStack(Items.compass));
        			ZPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
        			return true;
        		}
    		}
    	}
    	return false;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new BlockCraftBox.TileCraftBox();
	}
	
	public static class TileCraftBox extends TileEntityZenScape {
		
		private static final String NBT_NORMALCRAFT = "normalCrafting", TAG_SPAWNED = "justSpawned";
		private boolean startCrafting = false, stopCrafting = false;
		public boolean normalCrafting = false;
		
		public List<BlockCraftTree.TileCraftTree> tiles = new ArrayList<BlockCraftTree.TileCraftTree>(), tilesToGetFrom = new ArrayList<BlockCraftTree.TileCraftTree>();
		public 	InvTreeCrafting craft = new InvTreeCrafting(3, 3);
			
		@Override
		public void updateEntity() {

			TileCraftBox tcb = (TileCraftBox)worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
			
			if (tcb != null) 
			{
				if (!tcb.normalCrafting)
					treeCrafting(tcb);
				else
					normalCrafting(tcb);
			}
		}
		
		private void treeCrafting(TileCraftBox tcb) {
			
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
							item.getEntityItem().stackSize--;
							if (item.getEntityItem().stackSize == 0)
								item.setDead();
						}
					}
					else
						return;
				}
				else
					return;
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
		
		private void normalCrafting(TileCraftBox tcb) {
			
			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1D / 16D * 8D, zCoord, xCoord + 1, yCoord + 1D / 16D * 9D, zCoord + 1));
			for (EntityItem item : items)
			{
				if (item != null && !(item.getEntityData().hasKey(TAG_SPAWNED)))
				{
					this.addPossibleBlocks(-4, 1, -4);
					this.addPossibleBlocks(0, 1, -4);
					this.addPossibleBlocks(4, 1, -4);
					this.addPossibleBlocks(-4, 1, 0);
					this.addPossibleBlocks(4, 1, 0);
					this.addPossibleBlocks(-4, 1, 4);
					this.addPossibleBlocks(0, 1, 4);
					this.addPossibleBlocks(4, 1, 4);
					
					if (tiles.isEmpty())
						return;
					else {
						for (int i = 0; i < tiles.size(); i++) {
							BlockCraftTree.TileCraftTree tile = tiles.get(i);
							if (tile != null) {
								tilesToGetFrom.add(tile);
							}
						}
						if (!tilesToGetFrom.isEmpty()) {
							List<ItemStack> tempList = new ArrayList<ItemStack>();
							for (int j = 0; j < tilesToGetFrom.size(); j++) {
								
								tempList.add(tilesToGetFrom.get(j).getStackInSlot(0));
								if (tempList.size() == 8) {
									tempList.add(4, item.getEntityItem());
									for (int k = 0; k < tempList.size(); k++) {
										craft.setInventorySlotContents(k, tempList.get(k));
									}
								}
							}
							List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
							for (IRecipe recipe : recipes) {
								if (recipe.matches(craft, worldObj)) {
									
									spawnResult(recipe.getCraftingResult(craft));
									
									item.setDead();
									
									if (worldObj.isRemote)
										tcb.spawnEffects(worldObj, xCoord, yCoord, zCoord);
									clearSlots();
								}
								tilesToGetFrom.clear();
							}
							tilesToGetFrom.clear();
						}
					}
				}
			}
			tiles.clear();
		}
		
		private void spawnResult(ItemStack stack) {
			
			if (!worldObj.isRemote && stack != null) {
				ItemStack output = stack.copy();
				EntityItem outputItem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, output);
				NBTTagCompound tag = outputItem.getEntityData();
				tag.setBoolean(TAG_SPAWNED, true);
				worldObj.spawnEntityInWorld(outputItem);
			}
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
		
		public void writeCustomNBT(NBTTagCompound tag) {
			
			tag.setBoolean(this.NBT_NORMALCRAFT, this.normalCrafting);
		}
		
		public void readCustomNBT(NBTTagCompound tag) {
			
			this.normalCrafting = tag.getBoolean(this.NBT_NORMALCRAFT);
		}
	}
}
