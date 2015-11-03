package com.bafomdad.zenscape.blocks;

import com.bafomdad.zenscape.TileEntityZenScape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChestSorter extends BlockContainer {

	public BlockChestSorter(Material material) {
		
		super(material);
		float f = 0.875F;
		float f2 = 0.125F;
		this.setBlockBounds(f2, f2, f2, f, f, f);
	}
	
	@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		
		if (!world.isRemote && entity != null) {
			BlockChestSorter.TileSorter te = (TileSorter) world.getTileEntity(x, y, z);
			if (entity instanceof EntityItem && entity.isEntityAlive()) {
				if (te != null) {
					boolean hasItem = false;
					boolean hasSpace = false;
					ItemStack entityStack = ((EntityItem)entity).getEntityItem();
					for (int i = 0; i < te.getSizeInventory(); i++) {
						ItemStack tileItem = te.getStackInSlot(i);
						if (tileItem != null && tileItem.getItem() == entityStack.getItem() && tileItem.getItemDamage() == entityStack.getItemDamage())
						{
							hasItem = true;
							if ((tileItem.stackSize < tileItem.getMaxStackSize()) && (tileItem.stackSize < te.getInventoryStackLimit())) {
								hasSpace = true;
							}
							if ((hasItem) && (hasSpace))
							{
								int space = Math.min(te.getInventoryStackLimit(), tileItem.getMaxStackSize() - tileItem.stackSize);
								int mergeAmount = Math.min(space, entityStack.stackSize);
								
								ItemStack copy = tileItem.copy();
								copy.stackSize += mergeAmount;
								te.setInventorySlotContents(i, copy);
								entityStack.stackSize -= mergeAmount;
								break;
							}
						}
						else if (tileItem == null) 
						{
							hasSpace = true;
							te.setInventorySlotContents(i, entityStack);
							entity.setDead();
							break;
						}
					}
				}
			}
		}
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
	public TileEntity createNewTileEntity(World world, int meta) {

		return new BlockChestSorter.TileSorter();
	}
	
	public static class TileSorter extends TileEntityZenScape implements ISidedInventory {

		private ItemStack[] inventory;
		private int timer = 0;
		
		public TileSorter() {
			
			this.inventory = new ItemStack[9];
		}
	
		@Override
		public void updateEntity() {

			timer++;
			if (timer >= 60)
			{
				timer = 0;
				for (int i = -2; i < 2 + 1; i++) {
					for (int j = -1; j < 1 + 1; j++) {
						for (int k = -2; k < 2 + 1; k++)
						{
							int x = this.xCoord + i;
							int y = this.yCoord + j;
							int z = this.zCoord + k;
									
							TileEntity tile = worldObj.getTileEntity(x, y, z);
									
							if (tile != null && !(tile instanceof TileSorter) && tile instanceof IInventory) {
								IInventory inv = this.getInventory(tile.getWorldObj(), x, y, z);
								if (inv != null)
								{
									boolean hasItem = false;
									boolean hasSpace = false;
	
									for (int m = 0; m < inv.getSizeInventory(); m++) {
										for (int l = 0; l < inventory.length; l++) {
											if (this.getStackInSlot(l) != null) {
												ItemStack targetItem = inv.getStackInSlot(m);										
												ItemStack sourceItem = this.getStackInSlot(l);

												if (targetItem != null && targetItem.getItem() == sourceItem.getItem() && targetItem.getItemDamage() == sourceItem.getItemDamage())
												{
													hasItem = true;
													if ((targetItem.stackSize < targetItem.getMaxStackSize()) && (targetItem.stackSize < inv.getInventoryStackLimit())) {
														hasSpace = true;
													}
													if ((hasItem) && (hasSpace))
													{
														int space = Math.min(inv.getInventoryStackLimit(), targetItem.getMaxStackSize() - targetItem.stackSize);
														int mergeAmount = Math.min(space, sourceItem.stackSize);
														
														ItemStack copy = targetItem.copy();
														copy.stackSize += mergeAmount;
														inv.setInventorySlotContents(m, copy);
														sourceItem.stackSize -= mergeAmount;
														this.setInventorySlotContents(l, sourceItem);
														if (sourceItem.stackSize == 0)
															this.setInventorySlotContents(l, null);
														inv.markDirty();
														this.markDirty();
														break;
													}
												}

												else if (targetItem == null) 
												{
													hasSpace = true;
													inv.setInventorySlotContents(m, sourceItem);
													this.setInventorySlotContents(l, null);
													inv.markDirty();
													this.markDirty();
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		private void findMatchingStack(IInventory inventory, ItemStack stack, int slot) {
			
		}
	
		@Override
		public int getSizeInventory() {

			return inventory.length;
		}

		@Override
		public ItemStack getStackInSlot(int i) {

			return inventory[i];
		}

		@Override
		public ItemStack decrStackSize(int i, int j) {

			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int i) {

			return null;
		}

		@Override
		public void setInventorySlotContents(int i, ItemStack stack) {
			
			this.inventory[i] = stack;
			if (stack != null && stack.stackSize > this.getInventoryStackLimit())
				stack.stackSize = this.getInventoryStackLimit();
		}

		@Override
		public String getInventoryName() {

			return null;
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

			return false;
		}

		@Override
		public void openInventory() {
			
		}

		@Override
		public void closeInventory() {
			
		}

		@Override
		public boolean isItemValidForSlot(int i, ItemStack stack) {

			return inventory[i] == null;
		}
		
		public void readCustomNBT(NBTTagCompound tag) {
			
			NBTTagList var2 = tag.getTagList("Items", 10);
			inventory = new ItemStack[getSizeInventory()];
			for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
				NBTTagCompound var4 = var2.getCompoundTagAt(var3);
				byte var5 = var4.getByte("Slot");
				if (var5 >= 0 && var5 < inventory.length)
					inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		
		public void writeCustomNBT(NBTTagCompound tag) {
			
			NBTTagList var2 = new NBTTagList();
			for (int var3 = 0; var3 < inventory.length; ++ var3) {
				if (inventory[var3] != null) {
					NBTTagCompound var4 = new NBTTagCompound();
					var4.setByte("Slot", (byte)var3);
					inventory[var3].writeToNBT(var4);
					var2.appendTag(var4);
				}
			}
			tag.setTag("Items", var2);
		}
		
		public IInventory getInventory(World world, int x, int y, int z) {
			
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			
			return tileEntity instanceof IInventory ? (IInventory)tileEntity : null;
		}

		@Override
		public int[] getAccessibleSlotsFromSide(int side) {

			return new int[0];
		}

		@Override
		public boolean canInsertItem(int i, ItemStack stack, int j) {

			return false;
		}

		@Override
		public boolean canExtractItem(int i, ItemStack stack, int j) {

			return false;
		}
	}
}
