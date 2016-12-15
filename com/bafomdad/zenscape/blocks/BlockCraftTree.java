package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockContainerZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCraftTree extends BlockContainerZenScape {

	public BlockCraftTree(Material material) {
		
		super(material);
	}
	
    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ZenScape.blockZenLog);
	}
	
	@Override
	public int damageDropped(int metadata) {
		
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		return new TileCraftTree();
	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        
    	TileEntity tile = world.getTileEntity(x, y, z);
        ItemStack stack = player.getCurrentEquippedItem();
        if (tile != null && tile instanceof IInventory) {
            ItemStack tileStack = ((IInventory) tile).getStackInSlot(0);
            world.func_147479_m(x, y, z);

            if (stack != null) {
                if (!player.isSneaking()) 
                {
                    if (tileStack == null) {
                        tileStack = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
                        tileStack.stackTagCompound = stack.getTagCompound();
                        ((IInventory) tile).setInventorySlotContents(0, tileStack);
                        if (!player.capabilities.isCreativeMode)
                            player.getCurrentEquippedItem().stackSize--;
                        world.func_147479_m(x, y, z);
                        return true;
                    }
                }
            }else {
                if (tileStack != null) {
                    ItemStack item = tileStack;
                    EntityItem entity = new EntityItem(world, x, y, z, item);
                    if (!world.isRemote)
                        world.spawnEntityInWorld(entity);
                    ((IInventory) tile).setInventorySlotContents(0, null);
                    world.func_147479_m(x, y, z);
                    return true;
                }
            }
        }
        return false;
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
    
    public static class TileCraftTree extends TileEntityZenScape implements ISidedInventory {
    	
    	private ItemStack[] inventory;
    	
    	public TileCraftTree() {
    		
    		this.inventory = new ItemStack[1];
    	}

    	@Override
    	public int getSizeInventory() {

    		return inventory.length;
    	}

    	@Override
    	public ItemStack getStackInSlot(int var1) {

    		return inventory[var1];
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

    	@Override
    	public ItemStack getStackInSlotOnClosing(int var1) {
    		
    		ItemStack stack = this.getStackInSlot(var1);
    		inventory[var1] = null;
    		this.markDirty();
    		return stack;
    	}

    	@Override
    	public void setInventorySlotContents(int var1, ItemStack var2) {

    		inventory[var1] = var2;
    		this.markDirty();
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

    		return 1;
    	}

    	@Override
    	public boolean isUseableByPlayer(EntityPlayer player) {

    		return true;
    	}

    	@Override
    	public void openInventory() {
    		
    	}

    	@Override
    	public void closeInventory() {
    		
    	}

    	@Override
    	public boolean isItemValidForSlot(int var1, ItemStack itemStack) {

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
