package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.crafting.ZEnchanter;
import com.bafomdad.zenscape.util.Vector3;
import com.bafomdad.zenscape.util.ZPacketDispatcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnchanter extends BlockContainer {

	public BlockEnchanter(Material material) {
		
		super(material);
		float f = 0.625F;
		float f2 = 0.375F;
		this.setBlockBounds(f2, f2, f2, f, f, f);
	}
	
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	public int getRenderType() {
		
		return -1;
	}
	
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess block, int x, int y, int z, int side) {
	
		return false;
	}
    
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
		
		TileEnchanter tile = (TileEnchanter)world.getTileEntity(x, y, z);
		
		if (player.isSneaking())
		{
			for (int i = 0; i < tile.getSizeInventory(); i++) {
				ItemStack stack = tile.getStackInSlot(i);
				if (stack != null)
				{
					if (!world.isRemote)
					{
						if (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor && !tile.canAccept)
						{
							tile.canAccept = true;
						}
						EntityItem ei = new EntityItem(world, player.posX + 0.5, player.posY + 0.1, player.posZ + 0.5, stack);
						if (stack.hasTagCompound())
							ei.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
						world.spawnEntityInWorld(ei);
						tile.setInventorySlotContents(i, null);
						ZPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
					}
					world.func_147453_f(x, y, z, this);
					break;
				}
			}
		}
		if (!world.isRemote && player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ZenScape.itemWoodStaff)
		{
			tile.startEnchant();
		}
		return false;
	}
    
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	
    	if (!world.isRemote && entity != null) {
    		TileEnchanter tile = (TileEnchanter)world.getTileEntity(x, y, z);
    		if (entity instanceof EntityItem && entity.isEntityAlive()) {
    			ItemStack stack = ((EntityItem)entity).getEntityItem();
    			if (stack != null && (stack.getItem() == Items.writable_book || ((stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor) && tile.acceptableItem(stack)))) {
    				for (int i = 0; i < tile.getSizeInventory(); i++)
    				{
    					ItemStack tileslot = tile.getStackInSlot(i);
    					if (tileslot != null)
    						continue;
    					if (tileslot == null)
    					{
    						tile.setInventorySlotContents(i, stack);
    						((EntityItem)entity).setDead();
    						ZPacketDispatcher.dispatchTEToNearbyPlayers(tile);
    						if ((stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor))
    							tile.canAccept = false;
    						break;
    					}
    				}
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
    
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y - 1, z);
		return block == Blocks.enchanting_table;
	}
	
    public boolean canBlockStay(World world, int x, int y, int z) {
    	
    	Block block = world.getBlock(x, y - 1, z);
    	
    	return block == Blocks.enchanting_table;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	if (!this.canBlockStay(world, x, y, z)) {
    		world.setBlockToAir(x, y, z);
    	}
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        
    	return null;
    }
    
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		if ((rand.nextInt(2) == 0)) {
			world.spawnParticle("witchMagic", x + rand.nextFloat(), y, z + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEnchanter();
	}
	
	public static class TileEnchanter extends TileEntityZenScape implements ISidedInventory {
		
		private ItemStack[] inventory;
		public boolean canAccept = true;
		
		public TileEnchanter() {
			
			this.inventory = new ItemStack[3];
		}
		
		public void updateEntity() {
			
			boolean flag = false;
			
			for (int i = 0; i < this.getSizeInventory(); i++) {
				if (this.getStackInSlot(i) == null) {
					flag = true;
				}
			}
			if (flag)
			{
				List<EntityItem> entities = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.xCoord - 5, this.yCoord - 5, this.zCoord - 5, this.xCoord + 5, this.yCoord + 5, this.zCoord + 5));
				for (EntityItem eb : entities) {
					if (eb != null && eb.getEntityItem().getItem() == Items.writable_book || ((eb.getEntityItem().getItem() instanceof ItemTool || eb.getEntityItem().getItem() instanceof ItemArmor) && this.acceptableItem(eb.getEntityItem())))
					{
						this.setMotionFromVector(eb, new Vector3(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5), 0.45F);
					}
				}
			}
		}
		
		public boolean acceptableItem(ItemStack stack) {
			
			if (canAccept) {
				if (stack.getTagCompound() != null) {
					NBTTagList enchants = stack.getEnchantmentTagList();
					if (enchants != null)
						return true;
				}
			}
			return false;
		}
		
		private void setMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
			
			Vector3 entityVector = Vector3.fromEntity(entity);
			Vector3 finalVector = originalPosVector.copy().subtract(entityVector);
			
			if (finalVector.mag() > 1)
				finalVector.normalize();
			
			entity.motionX = finalVector.x * modifier;
			entity.motionY = finalVector.y * modifier;
			entity.motionZ = finalVector.z * modifier;
		}
		
		public void startEnchant() {
			
			ItemStack tool = null;
			int bookCount = 0;
			int toolSlot = -1;
			
			for (int i = 0; i < this.getSizeInventory(); i++) {
				ItemStack stack = this.getStackInSlot(i);
				if (stack != null)
				{
					if (stack.getItem() == Items.writable_book)
						bookCount++;
					if (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor) {
						tool = stack;
						toolSlot = i;
					}
				}
			}
			if (tool != null && bookCount > 0) {
				NBTTagCompound enchTag;
				NBTTagList enchants = tool.getEnchantmentTagList();
				if (enchants.tagCount() > 0) {
					for (int j = 0; j <= enchants.tagCount(); ++j) 
					{
						if (j < bookCount)
						{
							enchTag = (NBTTagCompound)((NBTTagList)tool.getTagCompound().getTag("ench")).getCompoundTagAt(j);
							outputBook(enchTag);
							enchants.removeTag(j);
							tool.setItemDamage(tool.getItemDamage() + worldObj.rand.nextInt(20) + 20);
						}
					}
					if (enchants.tagCount() == 0) {
						chuckToolOut(tool, toolSlot);
					}
				}
			}
		}
		
		private void outputBook(NBTTagCompound enchTag) {
			
			ItemStack outputBook = new ItemStack(Items.enchanted_book, 1);
			
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagList enchList = new NBTTagList();
			enchList.appendTag(enchTag);
			tag.setTag("ench", enchList);
			outputBook.setTagCompound(tag);

			EntityItem entityBook = new EntityItem(this.worldObj, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, outputBook);
			this.worldObj.spawnEntityInWorld(entityBook);
		}
		
		private void chuckToolOut(ItemStack stack, int slot) {
			
			stack.setTagCompound(null);
			ItemStack spawnItem = stack.copy();
			
			EntityItem entityTool = new EntityItem(this.worldObj, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, spawnItem);
			this.worldObj.spawnEntityInWorld(entityTool);
			
			this.setInventorySlotContents(slot, null);
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
		public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {

			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
			
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

			return 1;
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {

			return false;
		}

		@Override
		public void openInventory() {}

		@Override
		public void closeInventory() {}

		@Override
		public boolean isItemValidForSlot(int i, ItemStack stack) {

			return inventory[i] == null && (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor || stack.getItem() == Items.writable_book);
		}

		@Override
		public int[] getAccessibleSlotsFromSide(int side) {

			return null;
		}

		@Override
		public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
			
			return false;
		}

		@Override
		public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {

			return false;
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
	}
}
