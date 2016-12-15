package com.bafomdad.zenscape.items;

import java.io.IOException;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockCraftBox.TileCraftBox;
import com.bafomdad.zenscape.blocks.BlockZenSapling;
import com.bafomdad.zenscape.util.CoordOutput;
import com.bafomdad.zenscape.worldgen.WorldGenSkyStruc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStaff extends Item {
	
	public ItemStaff() {
		
		super();
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		
		return true;
	}
	
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		
		if (world.isRemote)
		{
			if (world.getBlock(x,y,z) == Blocks.red_flower)
			{
				boolean flag = true;
				if (flowerCircle(world, x, y, z))
				{
					player.worldObj.spawnParticle("lava", x, y + 1, z, 0.0D, 0.6D, 0.0D);
				}
				else
				{
					flag = false;
				}
			}
		}
		else if (!world.isRemote)
		{	
			if (world.getBlock(x, y, z) == ZenScape.blockZenSapling)
			{
				boolean flag = true;
				Random rand = new Random();
				((BlockZenSapling) ZenScape.blockZenSapling).growTree(world, x, y, z, rand);
			}
			int meta = world.getBlockMetadata(x, y, z);
			if (world.getBlock(x, y, z) == Blocks.wool)
			{
				if (!(meta <= 1))
				{
					try {
						new CoordOutput().outputFile(world, x, y, z, meta * 2);
						player.addChatMessage(new ChatComponentTranslation("Coordinates written to output.txt"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				{
					player.addChatMessage(new ChatComponentTranslation("This radius is too small!"));
				}
			}
			if (world.getBlock(x, y, z) == Blocks.nether_brick)
			{
				Random rand = new Random();
				new WorldGenSkyStruc().generate(world, rand, x, y, z);
			}
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof IInventory && player.isSneaking())
			{
				IInventory inv = (IInventory)te;
				for (int l = 0; l < inv.getSizeInventory(); l++) {
					ItemStack stack = inv.getStackInSlot(l);
					if (stack != null)
						System.out.println("Slot #" + l + " " + stack.getDisplayName() + " x" + stack.stackSize);
				}
			}
			if (te instanceof TileCraftBox)
			{
				TileCraftBox tcb = (TileCraftBox)te;
				for (int i = 0; i < tcb.craft.getSizeInventory(); i++)
				{
					ItemStack crafty = tcb.craft.getStackInSlot(i);
					System.out.println("Slot " + i + ": " + crafty);
				}
			}
			if (te instanceof TileEntityEnchantmentTable)
			{
				Enchantment[] ench = Enchantment.enchantmentsList;
				for (int i = 0; i < ench.length; i++) {
					int weight;
					if (ench[i] != null) {
						weight = ench[i].getWeight();
						System.out.println("Enchantment name: " + ench[i].getTranslatedName(1) + " / " + weight);
					}
				}
			}
		}
		return true;
	}
	
	public static boolean flowerCircle(World world, int x, int y, int z) {
		
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				if ((((dx != 0 ? 1 : 0) | (dz != 0 ? 1 : 0)) != 0) &&
						(world.getBlock(x + dx, y, z + dz) != Blocks.yellow_flower)) {
					return false;
				}
			}
		}
		return true;
	}
}
