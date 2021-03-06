package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.entity.CopyCatPlayer;
import com.bafomdad.zenscape.util.CopyCatHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockCopyCat extends BlockContainer {

	public BlockCopyCat(Material material) {
		
		super(material);
	}
	
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ZenScape.blockZenLog3);
	}
	
	@Override
	public int damageDropped(int metadata) {
		
		return 1;
	}

	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileCopyCat();
	}
	
	public static class TileCopyCat extends TileEntityZenScape {
		
		public boolean canLearn = true;
		public int blockInteract = 0;
		
		
		public boolean leftClick = false;
		public byte angle = 1;
		
		CopyCatPlayer fakePlayer;
		
		public void updateEntity() {
			
			if (canLearn || worldObj.isRemote)
				return;
			
		}
		
		public void updateFakePlayer() {
			
			if (fakePlayer != null)
			{
				double x = this.xCoord + 0.5D;
				double y = this.yCoord + 1.1D;
				double z = this.zCoord + 0.5D;
				float pitch = this.angle == 1 ? 0.0F : this.angle == 0 ? 45.0F : -45.0F;
				float yaw = 0.0F;
				
				this.fakePlayer.setPositionAndRotation(x, y, z, yaw, pitch);
				this.fakePlayer.height = -1.1F;
			}
		}
		
		public boolean rightClick(EntityPlayer fakePlayer, ItemStack stack, int blockX, int blockY, int blockZ, int side) {
			
			float f = 0.5F;
			float f1 = 0.5F;
			float f2 = 0.5F;
			
			Block block = this.worldObj.getBlock(blockX, blockY, blockZ);
			
			boolean isAir = block.isAir(this.worldObj, blockX, blockY, blockZ);
			if (isAir)
				return false;
			if (!isAir && block.onBlockActivated(fakePlayer.worldObj, blockX, blockY, blockZ, fakePlayer, side, f, f1, f2))
				return true;
			return false;
		}
		
		public void readCustomNBT(NBTTagCompound tag) {
			
			this.blockInteract = tag.getInteger("blockInteract");
		}
		
		public void writeCustomNBT(NBTTagCompound tag) {
			
			tag.setInteger("blockInteract", (byte)this.blockInteract);
		}
	}
}
