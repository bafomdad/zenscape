package com.bafomdad.zenscape.proxies;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Mouse;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockClayPot;
import com.bafomdad.zenscape.blocks.BlockCopyCat;
import com.bafomdad.zenscape.blocks.BlockCraftBox;
import com.bafomdad.zenscape.blocks.BlockCraftTree;
import com.bafomdad.zenscape.blocks.BlockDokuPot;
import com.bafomdad.zenscape.blocks.BlockEnchantree;
import com.bafomdad.zenscape.blocks.BlockFruitBomb;
import com.bafomdad.zenscape.blocks.BlockGearLog;
import com.bafomdad.zenscape.blocks.BlockJoker;
import com.bafomdad.zenscape.blocks.BlockPowerLog;
import com.bafomdad.zenscape.blocks.BlockWaterTorch;
import com.bafomdad.zenscape.blocks.BlockZenLily;
import com.bafomdad.zenscape.entity.EntityDokuDrop;
import com.bafomdad.zenscape.entity.EntityFruitBomb;
import com.bafomdad.zenscape.entity.EntityPodzolBall;
import com.bafomdad.zenscape.entity.EntityPuffball;
import com.bafomdad.zenscape.render.RenderClayPot;
import com.bafomdad.zenscape.render.RenderCopyCat;
import com.bafomdad.zenscape.render.RenderCraftBox;
import com.bafomdad.zenscape.render.RenderCraftTree;
import com.bafomdad.zenscape.render.RenderDokuPot;
import com.bafomdad.zenscape.render.RenderEnchantree;
import com.bafomdad.zenscape.render.RenderFruitBomb;
import com.bafomdad.zenscape.render.RenderGearLog;
import com.bafomdad.zenscape.render.RenderJoker;
import com.bafomdad.zenscape.render.RenderPowerLog;
import com.bafomdad.zenscape.render.RenderWaterTorch;
import com.bafomdad.zenscape.render.RenderZenLily;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	public static int renderPass;

	@Override
	public void init() {
		
		super.init();
		
		BlockWaterTorch.renderId = RenderingRegistry.getNextAvailableRenderId();
		BlockDokuPot.renderId = RenderingRegistry.getNextAvailableRenderId();
		BlockZenLily.renderId = RenderingRegistry.getNextAvailableRenderId();
//		BlockPowerLog.renderId = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(new RenderWaterTorch());
		RenderingRegistry.registerBlockHandler(new RenderDokuPot());
		RenderingRegistry.registerBlockHandler(new RenderZenLily());
//		RenderingRegistry.registerBlockHandler(new RenderPowerLog());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFruitBomb.class, new RenderFruitBomb());
		
		ClientRegistry.bindTileEntitySpecialRenderer(BlockFruitBomb.TileEntityFruitBomb.class, new BlockFruitBomb.TileEntityFruitBombRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockCraftTree.TileCraftTree.class, new RenderCraftTree());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockGearLog.TileGearLog.class, new RenderGearLog());		
		ClientRegistry.bindTileEntitySpecialRenderer(BlockClayPot.TileClayPot.class, new RenderClayPot());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockClayPot.TileClayPotDeco.class, new RenderClayPot());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockCraftBox.TileCraftBox.class, new RenderCraftBox());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockJoker.TileJoker.class, new RenderJoker());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockEnchantree.TileEnchantree.class, new RenderEnchantree());
		ClientRegistry.bindTileEntitySpecialRenderer(BlockCopyCat.TileCopyCat.class, new RenderCopyCat());
	}
	
	@Override
	public void registerRenderers() {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityDokuDrop.class, new RenderSnowball(ZenScape.itemDokuDrop));
		RenderingRegistry.registerEntityRenderingHandler(EntityPuffball.class, new RenderSnowball(Items.snowball));
		RenderingRegistry.registerEntityRenderingHandler(EntityPodzolBall.class, new RenderSnowball(ZenScape.itemPodzolBall));
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ZenScape.blockClayPot), new RenderClayPot.RenderClayPotItem());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ZenScape.blockCraftBox), new RenderCraftBox.RenderCraftBoxItem());
	}
	
	public boolean isButtonDown(int button) {
		
		boolean isDown = false;
		try
		{
			isDown = Mouse.isButtonDown(button);
		}
		catch (Exception e) {}
		return isDown;
	}
}
