package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vazkii.botania.api.internal.VanillaPacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockContainerZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.crafting.ZEnchanter;
import com.bafomdad.zenscape.network.ZPacketDispatcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnchantree extends BlockContainerZenScape {

	public BlockEnchantree(Material material) {
		
		super(material);
		this.setLightLevel(0.5F);
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
		
		return Item.getItemFromBlock(ZenScape.blockZenLog2);
	}
	
	@Override
	public int damageDropped(int metadata) {
		
		return 2;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		return new TileEnchantree();
	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
    	
    	TileEnchantree tile = (TileEnchantree)world.getTileEntity(x, y ,z);
    	ItemStack stack = player.getCurrentEquippedItem();
    	
    	boolean did = false;
    	
    	if (player.isSneaking() && stack == null) {
    		for (int i = tile.getSizeInventory() - 1; i >= 0; i--) {
    			ItemStack stackAt = tile.getStackInSlot(i);
    			if (stackAt != null) {
    				ItemStack copy = stackAt.copy();
    				if (!player.inventory.addItemStackToInventory(copy))
    					player.dropPlayerItemWithRandomChoice(copy, false);
    				tile.setInventorySlotContents(i, null);
    				world.func_147453_f(x, y, z, this);
    				break;
    			}
    		}
    	}
    	else if (!player.isSneaking() && stack != null)
    	{
    		if (stack.getItem() == ZenScape.itemWoodStaff && !tile.startEnchant) {
    			
    			tile.startEnchant = true;
    			return true;
    		}
        	for (int i = 0; i < tile.getSizeInventory(); i++)
        		if (tile.getStackInSlot(i) == null && tile.isItemValidForSlot(i, stack)) {
        			ItemStack stackToAdd = stack.copy();
        			stackToAdd.stackSize = 1;
        			tile.setInventorySlotContents(i, stackToAdd);
        			
        			if (player == null || !player.capabilities.isCreativeMode) {
        				stack.stackSize--;
        				if (stack.stackSize == 0 && player != null)
        					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
        			}
        			break;
        		}
        	
        	if (did)
        		ZPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
        	return true;
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
    
    public static class TileEnchantree extends TileEntityZenScape implements ISidedInventory {
    	
    	private ItemStack[] inventory;
    	public boolean startEnchant = false, startPulling = false;
		int range = 4;
		ItemStack result = null;
    	
    	public List<ItemStack> itemList = new ArrayList<ItemStack>();
    	
    	public TileEnchantree() {
    		
    		this.inventory = new ItemStack[1];
    	}
    	
    	public void updateEntity() {
    		
    		if (this.startEnchant) {
    			if (!this.startPulling)
    			{	
        			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));

        			int pulled = 0;
        			for (EntityItem item : items) {
        				if (pulled > 10)
        					break;
        				
        				itemList.add(item.getEntityItem());
        				pulled++;
        			}
        			for (ZEnchanter recipes : ZEnchanter.eRecipes) {
        				if (recipes.matches(this))
        				{
        					result = recipes.getOutput();
        					this.startPulling = true;
        				}
        				else
        					startEnchant = false;
        			}
        			itemList.clear();
        			return;
    			}
    			else
    			{
    				if (inventory[0] == null)
    				{
    					reset();
    					return;
    				}
        			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
        			
        			for (EntityItem item : items)
        			{
        				if (result != null && !worldObj.isRemote)
        				{
        					this.setInventorySlotContents(0, result);
        					reset();
        					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        					return;
        				}
        				if (item.isEntityAlive())
        				{
        					double d = getDistanceTo(item.posX, item.posY, item.posZ);
        					if (d < 2.0D)
        						item.setDead();
        					
            				double var3 = (this.xCoord + 0.5D - item.posX) / 15.0D;
            				double var5 = (this.yCoord + 0.5D - item.posY) / 15.0D;
            				double var7 = (this.zCoord + 0.5D - item.posZ) / 15.0D;
            				double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
            				double var11 = 1.0D - var9;
            				if (var11 > 0.0D)
            				{
            					var11 *= var11;
            					item.motionX += var3 / var9 * var11 * 0.15D;
            					item.motionY += var5 / var9 * var11 * 0.25D;
            					item.motionZ += var7 / var9 * var11 * 0.15D;
            				}
        				}
        			}
    			}
    		}
    	}
    	
    	public double getDistanceTo(double x, double y, double z) {
    		
    		double var7 = this.xCoord + 0.5D - x;
    		double var9 = this.yCoord + 0.5D - y;
    		double var11 = this.zCoord + 0.5D - z;
    		
    		return var7 * var7 + var9 * var9 + var11 * var11;
    	}
    	
    	public void reset() {
    		
    		startEnchant = false;
    		startPulling = false;
    		result = null;
    	}
    
    	public void collectSandGarden() {
    		
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
    	public void openInventory() {}

    	@Override
    	public void closeInventory() {}

    	@Override
    	public boolean isItemValidForSlot(int var1, ItemStack itemStack) {
    		
    		if (inventory[0] == null)
    			return (itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemBow || itemStack.getItem() == Items.writable_book || itemStack.getItem() == Item.getItemFromBlock(Blocks.chest));
    		
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
            this.startEnchant = nbt.getBoolean("ZS_startEnchant");
            this.startPulling = nbt.getBoolean("ZS_startPulling");
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
            nbt.setBoolean("ZS_startEnchant", this.startEnchant);
            nbt.setBoolean("ZS_startPulling", this.startPulling);
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
