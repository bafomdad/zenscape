package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.items.ItemCard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockClayPot extends BlockZenScape implements ITileEntityProvider {

	public BlockClayPot(Material material) {
		
		super(material);
		this.setBlockBounds(0.2375F, 0.0F, 0.2375F, 0.7625F, 0.85F, 0.7625F);
	}
	
	@Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if (meta == 0) {
			if (player.getCurrentEquippedItem() != null)
			{
				NBTTagCompound tool = player.getCurrentEquippedItem().getTagCompound();
				if (tool != null && tool.hasKey(ItemCard.TAG_HIGHLIGHT))
				{
					return ForgeHooks.blockStrength(this, player, world, x, y, z);
				}
			}
			return -1.0F;
		}
		return ForgeHooks.blockStrength(this, player, world, x, y, z);
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		
		boolean isSilk = EnchantmentHelper.getSilkTouchModifier(player);
		if (player.getCurrentEquippedItem() != null && isSilk)
		{
			this.dropBlockAsItem(world, x, y, z, new ItemStack(ZenScape.blockClayPot, 1, 1));
		}
		else
		{
			ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
			drops.add(new ItemStack(Blocks.hardened_clay));
			drops.add(new ItemStack(Items.gold_nugget, world.rand.nextInt(2) + 1));
			if (world.rand.nextFloat() < 0.3F)
				drops.add(new ItemStack(Items.gold_ingot));
			
            for (ItemStack is : drops)
            {
                this.dropBlockAsItem(world, x, y, z, is);
            }
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		
		return 1;
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
	public boolean hasTileEntity(int meta) {
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if (meta == 0)
			return new BlockClayPot.TileClayPot();
		else
			return new BlockClayPot.TileClayPotDeco();
	}
	
	public static class ItemClayPot extends ItemBlock {

		public ItemClayPot(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
	}
	
	public static class TileClayPot extends TileEntityZenScape {
	
	}
	
	public static class TileClayPotDeco extends TileEntityZenScape {
		
	}
}
