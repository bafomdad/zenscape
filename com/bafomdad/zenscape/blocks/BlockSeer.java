package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import com.bafomdad.zenscape.entity.FXSun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSeer extends Block {

	public BlockSeer(Material material) {
		
		super(material);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
		
		if (!world.isRemote)
		{
			if (player.getCurrentEquippedItem() != null)
			{
				if (player.getCurrentEquippedItem().getItem() == Items.ender_eye)
				{
					ChunkPosition chunkpos = world.findClosestStructure("Stronghold", (int)player.posX, (int)player.posY, (int)player.posZ);
					if (chunkpos != null)
					{
						player.addChatMessage(new ChatComponentTranslation("Stronghold: " + chunkpos.chunkPosX + ", " + chunkpos.chunkPosY + ", " + chunkpos.chunkPosZ));
					}
				}
				if (player.getCurrentEquippedItem().getItem() == Items.gold_ingot)
				{
					List<TileEntity> list = world.loadedTileEntityList;
					double closest = 1.7976931348623157E+308D;
					for (TileEntity tile : list) {
						if (tile instanceof TileEntityMobSpawner)
						{
							player.addChatComponentMessage(new ChatComponentTranslation("Dungeon: " + tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord));
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube() {
		
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
	
		if (rand.nextInt(3) == 0)
		{
			EntityFX particleSun = new FXSun(world, x + 0.5F, y + 0.5F, z + 0.5F);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleSun);
		}
	}
}
