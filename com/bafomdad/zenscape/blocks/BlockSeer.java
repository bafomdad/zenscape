package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import com.bafomdad.zenscape.BlockZenScape;
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
					Chunk chunk = world.getChunkFromBlockCoords(x, z);
					Block spawner = Blocks.mob_spawner;
					
					for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
					{
						if (storage != null)
						{
							for (int i = 0; i < 16; ++i) {
								for (int j = 0; j < 16; ++j) {
									for (int k = 0; k < 16; ++k)
									{
										if (storage.getBlockByExtId(i, j, k) == spawner)
										{
											player.addChatComponentMessage(new ChatComponentTranslation("Dungeon: " + (x + i) + ", " + j + ", " + (z + k)));
										}
									}
								}
							}
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
