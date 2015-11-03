package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.util.ZPacketDispatcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockJoker extends Block implements ITileEntityProvider {

	public BlockJoker(Material material) {
		
		super(material);
		setTickRandomly(true);
		setBlockBounds(0, 0.5F, 0, 1, 1, 1);
	}
	
	public static int renderId;
	
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
    {
    	if (side == 0 || side == 1)
    		return false;
    	return true;
    }
    
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		
		return 1;
	}
	
	public int getRenderType() {
		
		return renderId;
	}
    
    public boolean isOpaqueCube() {
    	
    	return false;
    }
    
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
    
		return;
	}
	
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		
		return null;
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if (!world.isRemote)
		{
			EntityPlayer player = world.getClosestPlayer(x, y, z, 7);
			if (player != null && !player.capabilities.isCreativeMode) {
				TileJoker tile = (TileJoker)world.getTileEntity(x, y, z);
				if (tile != null && tile instanceof IInventory) {
					ItemStack tileStack = ((IInventory)tile).getStackInSlot(0);
					if (tileStack != null)
						return;
					this.loopPlayerInv(tile, player, rand);
				}
			}
		}
	}
	
	private void loopPlayerInv(TileJoker tile, EntityPlayer player, Random rand) {
		
		Map<ItemStack, Integer> map = new HashMap<ItemStack, Integer>();
		
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (stack != null) {
				map.put(stack, i);
			}
		}
		if (!map.isEmpty()) {
			for (Map.Entry<ItemStack, Integer> entry : map.entrySet()) {
				ItemStack key = entry.getKey();
				int values = entry.getValue();
				if (rand.nextInt(map.size()) == 0) {
					player.inventory.setInventorySlotContents(values, null);
					tile.setInventorySlotContents(0, key);
					ZPacketDispatcher.dispatchTEToNearbyPlayers(tile);
					break;
				}
			}
		}
	}
    
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

		return new BlockJoker.TileJoker();
	}
	
	public static class TileJoker extends TileEntityZenScape implements ISidedInventory {

		private ItemStack[] inventory;
		
		public TileJoker() {
			
			this.inventory = new ItemStack[1];
		}
		
		public boolean canUpdate() {
			
			return false;
		}
		
		public int getSizeInventory() {

			return inventory.length;
		}

		public ItemStack getStackInSlot(int var1) {

			return inventory[var1];
		}

		public ItemStack decrStackSize(int par1, int par2) {
    		
			if (this.inventory[par1] != null)
    		{
    			ItemStack itemstack;
    			
    			if (this.inventory[par1].stackSize <= par2)
    			{
    				itemstack = this.inventory[par1];
    				this.inventory[par1] = null;
    				this.markDirty();
    				return itemstack;
    			}
    			else
    			{
    				itemstack = this.inventory[par1].splitStack(par2);
    				
    				if (this.inventory[par1].stackSize == 0)
    				{
    					this.inventory[par1] = null;
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

		public ItemStack getStackInSlotOnClosing(int var1) {
    		
			ItemStack stack = this.getStackInSlot(var1);
    		inventory[var1] = null;
    		this.markDirty();
    		return stack;
		}

		public void setInventorySlotContents(int var1, ItemStack var2) {
    		
			inventory[var1] = var2;
    		this.markDirty();
			
		}

		public String getInventoryName() {

			return null;
		}

		public boolean hasCustomInventoryName() {

			return false;
		}

		public int getInventoryStackLimit() {

			return 64;
		}

		public boolean isUseableByPlayer(EntityPlayer player) {

			return true;
		}

		public void openInventory() {}

		public void closeInventory() {}

		@Override
		public boolean isItemValidForSlot(int var1, ItemStack itemStack) {
    		
			return inventory[var1] == null;
		}

		public int[] getAccessibleSlotsFromSide(int side) {

			return new int[0];
		}

		public boolean canInsertItem(int i, ItemStack itemStack, int j) {

			return false;
		}

		public boolean canExtractItem(int i, ItemStack itemStack, int j) {

			return false;
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
}
