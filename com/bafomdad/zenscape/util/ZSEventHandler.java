package com.bafomdad.zenscape.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockFruitBomb;
import com.bafomdad.zenscape.blocks.BlockSpawnBlock;
import com.bafomdad.zenscape.crafting.ZPadCrafting;
import com.bafomdad.zenscape.items.ItemCard;
import com.bafomdad.zenscape.render.ZenTextureStitch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ZSEventHandler {
	
	public List<ItemStack> eventList = new ArrayList<ItemStack>();
	
	@SubscribeEvent
	public void blockInteract(PlayerInteractEvent event) {
		
		if ((event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) && event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == ZenScape.blockFruitBomb)
		{
			if (!event.entityPlayer.capabilities.isCreativeMode || event.entityPlayer.getCurrentEquippedItem() == null && event.entityPlayer.getCurrentEquippedItem() != new ItemStack(ZenScape.itemGrafterNet))
			{
				BlockFruitBomb bfb = (BlockFruitBomb)event.world.getBlock(event.x, event.y, event.z);
				bfb.dropTheBass(event.world, event.x, event.y, event.z);
				event.setCanceled(true);
			}
		}
		if ((event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == Blocks.waterlily && !event.world.isRemote)
		{
			if (event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == ZenScape.itemCompost)
			{
				eventList.clear();
				List<EntityItem> items = event.world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(event.x, event.y, event.z, event.x + 1, event.y + 1, event.z + 1));
				for (EntityItem item : items) {
					if (item != null) {
						if (!eventList.contains(item.getEntityItem()))
							eventList.add(item.getEntityItem());
						
						for (ZPadCrafting recipes : ZPadCrafting.lilyRecipes) {
							if (recipes.matches(this)) 
							{
								eventList.clear();
								Block output = (Block)recipes.getOutput();
								int meta = recipes.getOutputMeta();
								event.world.setBlock(event.x, event.y, event.z, output, meta, 2);
								if (!event.entityPlayer.capabilities.isCreativeMode)
									event.entityPlayer.getCurrentEquippedItem().stackSize--;
								for (int i = 0; i < items.size(); i++) {
									ZPacketDispatcher.clientParticle("cloud", items.get(i).posX, items.get(i).posY, items.get(i).posZ, 0.0D, 0.1D, 0.0D);
									items.get(i).setDead();
								}		
							}
						}
					}
				}
			}
		}
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
	public void texturePreStitch(TextureStitchEvent.Pre event) {
		
		if(event.map.getTextureType() == 0) 
		{
			event.map.setTextureEntry("zenscape:zengrass_bottom", ZenScape.texGrassBottom = new ZenTextureStitch("zenscape:zengrass_bottom"));
			event.map.setTextureEntry("zenscape:zengrass_side", ZenScape.texGrassSide = new ZenTextureStitch("zenscape:zengrass_side"));
			event.map.setTextureEntry("zenscape:zengrass_top", ZenScape.texGrassTop = new ZenTextureStitch("zenscape:zengrass_top"));
			event.map.setTextureEntry("zenscape:zendirt", ZenScape.texDirtFossil = new ZenTextureStitch("zenscape:zendirt"));
			event.map.setTextureEntry("zenscape:flywood_tex", ZenScape.texLogSide = new ZenTextureStitch("zenscape:flywood_tex"));
			event.map.setTextureEntry("zenscape:flywood_top_tex", ZenScape.texLogTop = new ZenTextureStitch("zenscape:flywood_top_tex"));
			event.map.setTextureEntry("zenscape:leaves_flywood_tex", ZenScape.texLeaves = new ZenTextureStitch("zenscape:leaves_flywood_tex"));
			event.map.setTextureEntry("zenscape:zenbrick0_tex", ZenScape.texBrick0 = new ZenTextureStitch("zenscape:zenbrick0_tex"));
			event.map.setTextureEntry("zenscape:zenbrick1_tex", ZenScape.texBrick1 = new ZenTextureStitch("zenscape:zenbrick1_tex"));
			event.map.setTextureEntry("zenscape:zenbrick2_tex", ZenScape.texBrick2 = new ZenTextureStitch("zenscape:zenbrick2_tex"));
			event.map.setTextureEntry("zenscape:zenbrick3_tex", ZenScape.texBrick3 = new ZenTextureStitch("zenscape:zenbrick3_tex"));
			event.map.setTextureEntry("zenscape:zenbrick16_tex", ZenScape.texBrick16 = new ZenTextureStitch("zenscape:zenbrick16_tex"));
			event.map.setTextureEntry("zenscape:bush_tex", ZenScape.texBush = new ZenTextureStitch("zenscape:bush_tex"));
		}
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
	public void onBlockPlaced(BlockEvent.PlaceEvent event) {
		
		ItemStack stack = event.player.inventory.getCurrentItem();
		
		if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
			if (stack.hasTagCompound()) {
				NBTTagCompound display = stack.stackTagCompound.getCompoundTag("display");
				if (display.hasKey("Lore")) {
					NBTTagList lore = display.getTagList("Lore", 8);
					event.world.setBlock(event.x, event.y + 1, event.z, ZenScape.blockEnchanter);
					
					for (int i = 0; i < lore.tagCount(); i++)
					{
						String s = lore.getStringTagAt(i).substring(2);
						int flower = getStackFromString(s);
						
						if (flower != -1)
						{
							Blocks.double_plant.func_149889_c(event.world, event.x + (i + 2), event.y, event.z + (i + 2), flower, 2);
							Blocks.double_plant.func_149889_c(event.world, event.x - (i + 2), event.y, event.z - (i + 2), flower, 2);
							Blocks.double_plant.func_149889_c(event.world, event.x - (i + 2), event.y, event.z + (i + 2), flower, 2);
							Blocks.double_plant.func_149889_c(event.world, event.x + (i + 2), event.y, event.z - (i + 2), flower, 2);
						}
					}
				}
			}
		}
	}
	
	private int getStackFromString(String name) {
		
		if (name.equals("Sunflower"))
			return 0;
		if (name.equals("Lilac"))
			return 1;
		if (name.equals("Double Tallgrass"))
			return 2;
		if (name.equals("Large Fern"))
			return 3;
		if (name.equals("Rose Bush"))
			return 4;
		if (name.equals("Peony"))
			return 5;
		return -1;
	}
}
