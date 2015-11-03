package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scala.actors.threadpool.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.util.InventoryInsert;
import com.bafomdad.zenscape.util.SlotInsert;

public class BlockRAC extends BlockContainer {

	public BlockRAC(Material material) {
		
		super(material);
		float f = 0.875F;
		float f2 = 0.125F;
		this.setBlockBounds(f2, f2, f2, f, f, f);
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IInventory) {
            for (int i1 = 0; i1 < ((IInventory) tile).getSizeInventory(); ++i1) {
                ItemStack itemstack = ((IInventory) tile).getStackInSlot(i1);

                if (itemstack != null) {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
                        int j1 = world.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                            j1 = itemstack.stackSize;

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) world.rand.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		TileRAC tile = (TileRAC)world.getTileEntity(x, y, z);
		if (tile != null && !player.isSneaking()) {
			player.openGui(ZenScape.instance, 1, world, x, y, z);
			tile.shuffleSlots();
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileRAC();
	}
	
	public static class TileRAC extends TileEntityZenScape implements IInventory {

		private ItemStack[] inventory;
		private boolean flag = false;
		
		public TileRAC() {
			
			inventory = new ItemStack[16];
		}
		
		@Override
		public int getSizeInventory() {

			return inventory.length;
		}
		
		public int getCurrentSizeInventory() {
			
			int currentSize = 0;
			
			for (int i = 0; i < inventory.length; ++i) {
				if (this.getStackInSlot(i) == null)
					continue;
				if (this.getStackInSlot(i) != null)
					currentSize++;
				else
					currentSize = 0;
			}
			return currentSize;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {

			return inventory[slot];
		}

		@Override
		public ItemStack decrStackSize(int par1, int par2) {

			if (this.inventory[par1] != null)
			{
				ItemStack itemstack;
				
				if (this.inventory[par1].stackSize <= par2)
				{
					itemstack = this.inventory[par1];
					this.inventory[par1] = null;
					this.markDirty();
					replaceEmptySlot(par1);
					return itemstack;
				}
				else
				{
					itemstack = this.inventory[par1].splitStack(par2);
					
					if (this.inventory[par1].stackSize == 0)
					{
						this.inventory[par1] = null;
						replaceEmptySlot(par1);
					}
					this.markDirty();
					return itemstack;
				}
			}
			else
			{
				return null;
			}
		}
		
		public void replaceEmptySlot(int slot) {
			
			for (int i = this.inventory.length - 1; i >= 0; --i) {
				ItemStack stack1 = getStackInSlot(i);
				if (stack1 != null && stack1 != getStackInSlot(slot))
				{
					setInventorySlotContents(i, null);
					setInventorySlotContents(slot, stack1);
					break;
				}
				markDirty();
			}
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int var1) {

			ItemStack stack = this.getStackInSlot(var1);
			inventory[var1] = null;
			this.markDirty();
			return stack;
		}

		@Override
		public void setInventorySlotContents(int var1, ItemStack stack) {

			inventory[var1] = stack;
			this.markDirty();
		}
		
		public void shuffleSlots() {
			
			ItemStack[] inv = this.inventory;
			List<ItemStack> stacks = Arrays.asList(inv);
			Collections.shuffle(stacks.subList(0, 3));
			this.inventory = stacks.toArray(inv);
		}
		
		public TileEntity getContainerCoords(int x, int y, int z) {
			
			x = this.xCoord;
			y = this.yCoord;
			z = this.zCoord;
			return worldObj.getTileEntity(x, y, z);
		}

		@Override
		public String getInventoryName() {

			return getBlockType().getLocalizedName();
		}

		@Override
		public boolean hasCustomInventoryName() {

			return false;
		}

		@Override
		public int getInventoryStackLimit() {

			return 64;
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {

			return true;
		}

		@Override
		public void openInventory() {}

		@Override
		public void closeInventory() {}

		@Override
		public boolean isItemValidForSlot(int var1, ItemStack stack) {

			return inventory[var1] == null;
		}
		
	    public void readCustomNBT(NBTTagCompound nbt) {
	    	
	        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
	        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
	            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
	            int j = nbttagcompound1.getByte("Slot") & 255;

	            if (j >= 0 && j < this.inventory.length)
	                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
	        }
	    }

	    public void writeCustomNBT(NBTTagCompound nbt) {
	    	
	        NBTTagList nbttaglist = new NBTTagList();

	        for (int i = 0; i < this.inventory.length; ++i) {
	            if (this.inventory[i] != null) {
	                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	                nbttagcompound1.setByte("Slot", (byte) i);
	                this.inventory[i].writeToNBT(nbttagcompound1);
	                nbttaglist.appendTag(nbttagcompound1);
	            }
	        }
	        nbt.setTag("Items", nbttaglist);
	    }
	}
	
	public static class ContainerRAC extends Container {
		
		public TileRAC rac;
		public InventoryInsert inserty = new InventoryInsert(this);
		
		public ContainerRAC(InventoryPlayer inventory, TileRAC tile) {
			
			this.rac = tile;
	        int i;
	        int j;

	        this.addSlotToContainer(new SlotInsert(inventory.player, inserty, 0, 32, 37));
	        
	        for (i = 0; i < 2; ++i)
	        {
	            for (j = 0; j < 2; ++j)
	            {
	                this.addSlotToContainer(new Slot(tile, j + i * 2, 72 + j * 18, 27 + i * 18));
	            }
	        }

	        for (i = 0; i < 3; ++i)
	        {
	            for (j = 0; j < 9; ++j)
	            {
	                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	            }
	        }

	        for (i = 0; i < 9; ++i)
	        {
	            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	        }
		}

		@Override
		public boolean canInteractWith(EntityPlayer player) {
			
			return rac.isUseableByPlayer(player);
		}
		
		@Override
		public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
			
			ItemStack stack = null;
			Slot slot = (Slot)this.inventorySlots.get(par2);
			
			if (slot != null && slot.getHasStack())
			{
				ItemStack stack1 = slot.getStack();
				stack = stack1.copy();
				
				if (par2 == 0)
				{
					if (!this.mergeItemStack(stack1, 4, this.inventorySlots.size(), true))
						return null;
				}
				else if (!this.mergeItemStack(stack1, 0, 4, false))
					return null;
				
				if (stack1.stackSize == 0)
					slot.putStack((ItemStack)null);
				else
					slot.onSlotChanged();
			}
			return stack;
		}
	}
}
