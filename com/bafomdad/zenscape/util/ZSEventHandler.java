package com.bafomdad.zenscape.util;

import java.util.List;
import java.util.Random;

import thaumcraft.codechicken.lib.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockSpawnBlock;
import com.bafomdad.zenscape.items.ItemCard;
import com.bafomdad.zenscape.util.test.ZBlockHandler;
import com.bafomdad.zenscape.util.test.ZSaveData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ZSEventHandler {
	
	private static final int range = 64;
	
	@SubscribeEvent
	public void onEntityDrops(LivingDropsEvent event) {
		
		if (event.recentlyHit && event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
			ItemStack sample = ((EntityPlayer)event.source.getEntity()).getCurrentEquippedItem();
			if (sample != null && sample.getItem() instanceof ItemSword) {
				
				int looting = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, sample);
				int murderlog = 0;
				
				for (int x1 = -5; x1 <= 5; x1++) {
					for (int y1 = -2; y1 <= 2; y1++) {
						for (int z1 = -5; z1 <= 5; z1++)
						{
							int x = MathHelper.floor_double(event.source.getEntity().posX) + x1;
							int y = MathHelper.floor_double(event.source.getEntity().posY) + y1;
							int z = MathHelper.floor_double(event.source.getEntity().posZ) + z1;
							
							Block loopblock = event.entityLiving.worldObj.getBlock(x, y, z);
							if (loopblock != null && loopblock == ZenScape.blockZenLog3 && (event.entityLiving.worldObj.getBlockMetadata(x, y, z) & 0x3) == 3)
								murderlog++;
						}
					}
				}
				Random rand = event.entity.worldObj.rand;
				if (rand.nextInt(30) + murderlog > (50 / 1 + looting))
				{
					if (event.entityLiving instanceof EntitySkeleton)
						addDrop(event, new ItemStack(Items.skull, 1, ((EntitySkeleton)event.entityLiving).getSkeletonType()));
					else if (event.entityLiving instanceof EntityZombie && !(event.entityLiving instanceof EntityPigZombie))
						addDrop(event, new ItemStack(Items.skull, 1, 2));
					else if (event.entityLiving instanceof EntityCreeper)
						addDrop(event, new ItemStack(Items.skull, 1, 4));
					else if (event.entityLiving instanceof EntityPlayer)
					{
						ItemStack stack = new ItemStack(Items.skull, 1, 3);
						ItemNBTHelper.setString(stack, "SkullOwner", ((EntityPlayer)event.entityLiving).getCommandSenderName());
						addDrop(event, stack);
					}
				}
			}
		}
	}
	
	private void addDrop(LivingDropsEvent event, ItemStack drop) {
		
		EntityItem entityItem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, drop);
		entityItem.delayBeforeCanPickup = 10;
		event.drops.add(entityItem);
	}

	@SubscribeEvent
	public void checkEntityDeaths(LivingDeathEvent event) {
		
		NBTTagCompound tag = event.entity.getEntityData();
		if (tag.hasKey(BlockSpawnBlock.TAG_SPAWNTREASURE))
		{
			int tagx = tag.getInteger(BlockSpawnBlock.TAG_XLOC);
			int tagy = tag.getInteger(BlockSpawnBlock.TAG_YLOC);
			int tagz = tag.getInteger(BlockSpawnBlock.TAG_ZLOC);
			
			if (event.entity.worldObj.isAirBlock(tagx, tagy + 1, tagz))
			{
				Random rand = new Random();
				event.entity.worldObj.setBlock(tagx, tagy + 1, tagz, Blocks.chest);
				WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(ChestGenHooks.DUNGEON_CHEST, rand), (IInventory)event.entity.worldObj.getTileEntity(tagx, tagy + 1, tagz), 9);
			}
		}
	}
	
	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
		
		if (event.getResult() != Result.ALLOW && event.entityLiving instanceof IMob) {
			List<EntityPlayer> players = event.world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(event.x - range, event.y - range, event.z - range, event.x + range, event.y + range, event.z + range));
			for (EntityPlayer player : players) {
				if (isNearNegawood(event.entityLiving) && event.world.rand.nextInt(10) == 0)
				{
					event.setResult(Result.ALLOW);
					return;
				}
			}
		}
	}
	
	private boolean isNearNegawood(EntityLivingBase entity) {
		
		int woodRange = 8;
		for (int i = -woodRange; i < woodRange; i++) {
			for (int k = -woodRange; k < woodRange; k++) {
				
				int x = i + MathHelper.floor_double(entity.posX);
				int y = MathHelper.floor_double(entity.posY);
				int z = k + MathHelper.floor_double(entity.posZ);
				
				Block log = entity.worldObj.getBlock(x, y, z);
				if (log != null && log == ZenScape.blockZenLog3 && (entity.worldObj.getBlockMetadata(x, y, z) & 0x3) == 0)
					return true;
			}
		}
		return false;
	}
	
	@SubscribeEvent
	public void shouldBlockHighlightHighlightCertainBlocks(DrawBlockHighlightEvent event) {
		
		int meta = event.player.worldObj.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ);
			
		if (event.player != null && event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) instanceof BlockZenScape)
		{
			if (event.currentItem != null)
			{
				NBTTagCompound tool = event.currentItem.getTagCompound();
				if (tool != null && tool.hasKey(ItemCard.TAG_HIGHLIGHT))
				{
					return;
				}
			}
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onBlocksExploded(ExplosionEvent.Detonate event) {

		World world = event.world;
		if (!world.isRemote)
		{
			List<ChunkPosition> explodedBlocks = event.getAffectedBlocks();
			for (ChunkPosition pos : explodedBlocks) {
				try
				{
					Block eventBlock = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
					int meta = world.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
					if (!eventBlock.isAir(world, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ) && !eventBlock.hasTileEntity(meta))
					{
						ChunkCoordinates loc = new ChunkCoordinates(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
						ZBlockHandler.INSTANCE.addBlock(world, loc, eventBlock, meta);
						
						ZSaveData.setDirty(world.provider.dimensionId);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
