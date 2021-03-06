package com.bafomdad.zenscape;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bafomdad.zenscape.blocks.*;
import com.bafomdad.zenscape.blocks.ancientblocks.*;
import com.bafomdad.zenscape.blocks.unobtainium.*;
import com.bafomdad.zenscape.crafting.ZCrafting;
import com.bafomdad.zenscape.crafting.ZEnchanter;
import com.bafomdad.zenscape.crafting.ZPadCrafting;
import com.bafomdad.zenscape.crafting.ZPistonCraft;
import com.bafomdad.zenscape.entity.EntityDokuDrop;
import com.bafomdad.zenscape.entity.EntityFruitBomb;
import com.bafomdad.zenscape.entity.EntityPodzolBall;
import com.bafomdad.zenscape.entity.EntityPuffball;
import com.bafomdad.zenscape.items.*;
import com.bafomdad.zenscape.network.ZPacketHandler;
import com.bafomdad.zenscape.proxies.CommonProxy;
import com.bafomdad.zenscape.util.CopyCatHandler;
import com.bafomdad.zenscape.util.Dyes;
import com.bafomdad.zenscape.util.ZGuiHandler;
import com.bafomdad.zenscape.util.ZSEventHandler;
import com.bafomdad.zenscape.util.ZSTickHandler;
import com.bafomdad.zenscape.util.test.ZBlockHandler;
import com.bafomdad.zenscape.util.test.ZSaveData;
import com.bafomdad.zenscape.worldgen.WorldGenDecorators;
import com.bafomdad.zenscape.worldgen.WorldGenDownUnder;
import com.bafomdad.zenscape.worldgen.WorldGenIslands;
import com.bafomdad.zenscape.worldgen.WorldGenNether;
import com.bafomdad.zenscape.worldgen.WorldGenOcean;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "zenscape", name="ZenScape", version="0.1.2")

public class ZenScape {
	
	public static final String MOD_ID = "zenscape";
	@SidedProxy(clientSide = "com.bafomdad.zenscape.proxies.ClientProxy", serverSide = "com.bafomdad.zenscape.proxies.CommonProxy")
	public static CommonProxy proxy;
	@Mod.Instance("zenscape")
	public static ZenScape instance;
	
	public static Block blockCraftTree;
	public static Block blockCraftBox;
	public static Block blockBlackMesa;
	public static Block blockZenBricks;
	public static Block blockZenLog;
	public static Block blockZenLog2;
	public static Block blockZenLog3;
	public static Block blockGearLog;
	public static Block blockZenSapling;
	public static Block blockZenLeaves;
	public static Block blockWaterTorch;
	public static Block blockLightBlock;
	public static Block blockZenLily;
	public static Block blockFruitBomb;
	public static Block blockDokuPot;
	public static Block blockPowerLog;
	public static Block blockGreenStairs;
	public static Block blockRedStairs;
	public static Block blockBlueStairs;
	public static Block blockBlackStairs;
	public static Block blockZenGrass;
	public static Block blockZenDirt;
	public static Block slab;
	public static Block double_slab;
	public static Block blockCaveTrap;
	public static Block blockClayPot;
	public static Block blockSeer;
	public static Block blockSpawnBlock;
	public static Block blockDarkGrass;
	public static Block blockSorter;
	public static Block blockPistonCrafter;
	public static Block blockZenLeaf;
	public static Block blockZenTree;
	public static Block blockZenBrick;
	public static Block blockZenDirtGrass;
	public static Block blockZenBush;
	public static Block blockZenSkyBush;
	public static Block blockColdLava;
	public static Block blockRAC;
	public static Block blockJoker;
	public static Block blockDarkAir;
	public static Block blockEnchantree;
	public static Block blockCopyCat;
	public static Block blockAirBubble;
	public static Block blockPotion;
	public static Block blockHeartCake;
	public static Block blockRainThistle;
	public static Block blockAncientStone;
	public static Block blockAncientGrass;
	public static Block blockAncientGravel;
	public static Block blockZenSand;
	
	public static Item itemTestStaff;
	public static Item itemBatStaff;
	public static Item itemBombStaff;
	public static Item itemSawTooth;
	public static Item itemLightPlate;
	public static Item itemSolomonRing;
	public static Item itemLavaLily;
	public static Item itemGrafterNet;
	public static Item itemDokuDrop;
	public static Item itemDokuBottle;
	public static Item itemCard;
	public static Item itemGoggles;
	public static Item itemFlowerBoots;
	public static Item itemPuffshoot;
	public static Item itemAlchemyBottles;
	public static Item itemCompost;
	public static Item itemClayShovel;
	public static Item itemCakePickaxe;
	public static Item itemGear;
	public static Item itemLilypadBag;
	public static Item itemWoodStaff;
	public static Item itemDemoRock;
	public static Item itemPodzolBall;
	public static Item itemDogResidue;
	public static Item itemAncientClock;
	public static Item itemZenHoe;
	
	public static ItemFood itemDietPills;
	
	public static CreativeTabs zenscapeTab;
	public static CreativeTabs debugTab;
	public static ZConfig config;
	public static Logger logger = LogManager.getLogger("zenscape");

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		config = new ZConfig();
		config.loadConfig(event);
		logger.info("Begin Pre-initialization");
		proxy.init();
		MinecraftForge.EVENT_BUS.register(new ZSEventHandler());
		MinecraftForge.EVENT_BUS.register(new CopyCatHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenDecorators());
		FMLCommonHandler.instance().bus().register(new ZSTickHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ZGuiHandler());
		ZPacketHandler.init();
		
		zenscapeTab = new CreativeTabs("zenscape" + ".creativeTab") {
			
			public ItemStack getIconItemStack() {
				
				return new ItemStack(itemSawTooth);
			}

			@Override
			public Item getTabIconItem() {

				return new Item();
			}
		};
		
		if (config.extraCreativeTab)
		{
			debugTab = new CreativeTabs("zenscape" + ".debugTab") {
				
				public ItemStack getIconItemStack() {
					
					return new ItemStack(blockZenGrass);
				}
				
				@Override
				public Item getTabIconItem() {
					
					return new Item();
				}
			};
		}
				
		itemTestStaff = new ItemStaff().setUnlocalizedName("zenscape" + "." + "teststaff").setTextureName("zenscape:teststaff").setCreativeTab(zenscapeTab); 
		GameRegistry.registerItem(itemTestStaff, "ItemStaff");
		
		itemBatStaff = new ItemBatStaff().setUnlocalizedName("zenscape" + "." + "batstaff").setTextureName("zenscape:batstaff").setCreativeTab(zenscapeTab); 
		GameRegistry.registerItem(itemBatStaff, "ItemBatStaff");
		
		itemBombStaff = new ItemBombStaff().setUnlocalizedName("zenscape" + "." + "bombstaff").setTextureName("zenscape:bombstaff").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemBombStaff, "ItemBombStaff");
		
		itemSawTooth = new ItemSawtooth(ToolMaterial.IRON).setUnlocalizedName("zenscape" + "." + "sawtooth").setTextureName("zenscape:sawtooth").setCreativeTab(zenscapeTab); 
		GameRegistry.registerItem(itemSawTooth, "ItemSawtooth");
		
		itemLightPlate = new Item().setUnlocalizedName("zenscape" + "." + "lightningplate").setTextureName("zenscape:lightningplate").setCreativeTab(zenscapeTab); 
		GameRegistry.registerItem(itemLightPlate, "ItemLightningPlate");
		
		itemSolomonRing = new ItemSolomonRing().setUnlocalizedName("zenscape" + "." + "solomonring").setTextureName("zenscape:solomonring").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemSolomonRing, "ItemSolomonRing");
		
		itemGrafterNet = new Item().setMaxStackSize(1).setUnlocalizedName("zenscape" + "." + "grafternet").setTextureName("zenscape:net_grafter").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemGrafterNet, "ItemGrafterNet");
		
		itemDokuDrop = new ItemDokuDrop().setUnlocalizedName("zenscape" + "." + "dokudrop").setTextureName("zenscape:dokudrop");
		GameRegistry.registerItem(itemDokuDrop, "ItemDokuDrop");
		
		itemDokuBottle = new ItemDokuBottle().setUnlocalizedName("zenscape" + "." + "dokubottle").setTextureName("zenscape:dokubottle").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemDokuBottle, "ItemDokuBottle");
		
		itemCard = new ItemCard().setUnlocalizedName("zenscape" + "." + "card").setTextureName("card").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemCard, "ItemCard");
		
		itemGoggles = new ItemGlasses(ArmorMaterial.IRON, 0).setUnlocalizedName("zenscape" + "." + "goggles").setTextureName("zenscape:zengoggles").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemGoggles, "ItemGoggles");
		
		itemFlowerBoots = new ItemFlowerBoots(ArmorMaterial.CLOTH, 3).setUnlocalizedName("zenscape" + "." + "flowerboots").setTextureName("zenscape:flowerboots").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemFlowerBoots, "ItemFlowerBoots");
		
		itemPuffshoot = new ItemPuffshoot().setUnlocalizedName("zenscape" + "." + "puffshoot").setTextureName("zenscape:puffshoot").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemPuffshoot, "ItemPuffShoot");
		
		itemAlchemyBottles = new ItemAlchemyBottles().setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemAlchemyBottles, "ItemAlchemyBottles");
		
		itemCompost = new ItemCompost().setUnlocalizedName("zenscape" + "." + "compost").setTextureName("zenscape:compost").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemCompost, "ItemCompost");
		
		itemClayShovel = new ItemClayShovel(ToolMaterial.WOOD).setUnlocalizedName("zenscape" + "." + "clayshovel").setTextureName("zenscape:shovelclay").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemClayShovel, "ItemClayShovel");
		
		itemCakePickaxe = new ItemCakePickaxe(ToolMaterial.WOOD).setUnlocalizedName("zenscape" + "." + "cakepickaxe").setTextureName("zenscape:pickaxecake").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemCakePickaxe, "ItemCakePickaxe");
		
		itemGear = new Item().setUnlocalizedName("zenscape" + "." + "itemgear").setTextureName("zenscape:itemgear").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemGear, "ItemGear");
		
		itemLilypadBag = new ItemLilypadBag().setUnlocalizedName("zenscape" + "." + "lilypadbag").setTextureName("zenscape:lilypadbag").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemLilypadBag, "ItemLilypadBag");
		
		itemWoodStaff = new ItemWoodStaff().setUnlocalizedName("zenscape" + "." + "woodstaff").setTextureName("zenscape:woodstaff").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemWoodStaff, "ItemWoodStaff");
		
		itemDemoRock = new ItemDemoRock().setUnlocalizedName("zenscape" + "." + "demorock").setTextureName("zenscape:demorock").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemDemoRock, "ItemDemoRock");
		
		itemDietPills = (ItemFood)new ItemFood(-4, 0, false).setAlwaysEdible().setUnlocalizedName("zenscape" + "." + "dietpills").setTextureName("zenscape:dietpills").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemDietPills, "ItemDietPills");
		
		itemPodzolBall = new ItemPodzolBall().setMaxStackSize(16).setUnlocalizedName("zenscape" + "." + "podzolball").setTextureName("zenscape:podzolball").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemPodzolBall, "ItemPozolBall");
		
		itemDogResidue = new ItemDogResidue().setMaxStackSize(1).setUnlocalizedName("zenscape" + "." + "dogresidue").setTextureName("zenscape:dogresidue").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemDogResidue, "ItemDogResidue");
		
		itemAncientClock = new ItemAncientClock().setUnlocalizedName("zenscape" + "." + "ancientclock").setTextureName("zenscape:ancient_clock").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemAncientClock, "ItemAncientClock");
		
		itemZenHoe = new ItemZenHoe().setUnlocalizedName("zenscape" + "." + "zenhoe").setTextureName("zenscape:zen_hoe").setCreativeTab(zenscapeTab);
		GameRegistry.registerItem(itemZenHoe, "ItemZenHoe");
		
		blockZenLily = new BlockZenLily(Material.plants).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "zenlily").setBlockTextureName("zenlily").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenLily, BlockZenLily.ItemZenLily.class, "zenlily");
		GameRegistry.registerTileEntity(BlockZenLily.TileVisibleLily.class, "tileVisibleLily");
		GameRegistry.registerTileEntity(BlockZenLily.TileInvisibleLily.class, "tileInvisibleLily");
		
		blockCraftTree = new BlockCraftTree(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "crafttree").setBlockTextureName("zenscape:crafttreetile").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockCraftTree, "craftingtree");
		GameRegistry.registerTileEntity(BlockCraftTree.TileCraftTree.class, "tilecrafttree");
		
		blockCraftBox = new BlockCraftBox(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "craftbox").setBlockTextureName("zenscape:craftbox_bottom").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockCraftBox, "craftingbox");
		GameRegistry.registerTileEntity(BlockCraftBox.TileCraftBox.class, "tileCraftBox");
		
		blockBlackMesa = new BlockBlackMesa(Material.iron).setHardness(5.0F).setStepSound(Block.soundTypeMetal).setBlockName("zenscape" + "." + "blackmesa").setBlockTextureName("blackmesa").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockBlackMesa, BlockBlackMesa.ItemBlackMesa.class, "blackmesa");
		
		blockZenBricks = new BlockZenBricks(Material.rock).setHardness(3.0F).setStepSound(Block.soundTypeStone).setBlockName("zenscape" + "." + "zenbrick").setBlockTextureName("zenbrick").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenBricks, BlockZenBricks.ItemZenBricks.class, "zenbricks");
		
		blockZenLog = new BlockZenLog(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "zenlog").setBlockTextureName("zenscape:log").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenLog, BlockZenLog.ItemZenscapeLog.class, "zenlog1");
		
		blockZenLog2 = new BlockZenLog2(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "zenlog2").setBlockTextureName("zenscape:log").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenLog2, BlockZenLog2.ItemZenscapeLog2.class, "zenlog2");
		
		blockZenLog3 = new BlockZenLog3(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "zenlog3").setBlockTextureName("zenscape:log").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenLog3, BlockZenLog3.ItemZenscapeLog3.class, "zenlog3");
		
		blockGearLog = new BlockGearLog(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeStone).setBlockName("zenscape" + "." + "gearlog").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockGearLog, "gearlog");
		GameRegistry.registerTileEntity(BlockGearLog.TileGearLog.class, "tileGearLog");
		
		blockZenSapling = new BlockZenSapling(Material.plants).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "zensapling").setBlockTextureName("zenscape:sapling").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenSapling, BlockZenSapling.ItemZenscapeSapling.class, "zensapling");
		
		blockZenLeaves = new BlockZenLeaves(Material.leaves, false).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "zenleaves").setBlockTextureName("zenscape:leaves").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenLeaves, BlockZenLeaves.ItemZenscapeLeaves.class, "zenleaves");
		
		blockWaterTorch = new BlockWaterTorch(Material.water).setHardness(0.0F).setBlockName("zenscape" + "." + "watertorch").setBlockTextureName("zenscape:watertorch").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockWaterTorch, "watertorch");
		
		blockLightBlock = new BlockLightningBlock(Material.iron).setHardness(5.0F).setStepSound(Block.soundTypeMetal).setBlockName("zenscape" + "." + "lightningblock").setBlockTextureName("zenscape:lightningblock").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockLightBlock, "lightningblock");
			
		blockFruitBomb = new BlockFruitBomb(Material.gourd).setHardness(0.2F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "fruitbomb").setBlockTextureName("zenscape:fruitbomb").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockFruitBomb, "fruitbomb");
		GameRegistry.registerTileEntity(BlockFruitBomb.TileEntityFruitBomb.class, "tileEntityFruitBomb");
		
		blockDokuPot = new BlockDokuPot(Material.iron).setHardness(5.0F).setStepSound(Block.soundTypeMetal).setBlockName("zenscape" + "." + "dokupot").setBlockTextureName("zenscape:dokupot").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockDokuPot, "dokupot");
		
		blockPowerLog = new BlockPowerLog(Material.wood).setStepSound(Block.soundTypeWood).setHardness(2.0F).setBlockName("zenscape" + "." + "powerlog").setBlockTextureName("powerblock").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockPowerLog, "powerlog");
		GameRegistry.registerTileEntity(BlockPowerLog.TilePowerLog.class, "tileEntityPowerLog");
		
		blockZenGrass = new BlockZenGrass(Material.grass).setStepSound(Block.soundTypeGrass).setHardness(0.6F).setBlockName("zenscape" + "." + "zengrass").setBlockTextureName("grassz").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenGrass, "zengrass");
		
		blockZenDirt = new BlockZenDirt(Material.ground).setStepSound(Block.soundTypeGrass).setHardness(0.5F).setBlockName("zenscape" + "." + "zendirt").setBlockTextureName("dirt").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenDirt, BlockZenDirt.ItemZenDirt.class, "zendirt");
		
		blockCaveTrap = new BlockCaveTrap(Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.0F).setBlockName("zenscape" + "." + "cavetrap").setBlockTextureName("zenscape:cavetrap").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockCaveTrap, "cavetrap");
		
		blockClayPot = new BlockClayPot(Material.clay).setStepSound(Block.soundTypeStone).setHardness(0.1F).setBlockName("zenscape" + "." + "claypot").setBlockTextureName("hardened_clay").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockClayPot, BlockClayPot.ItemClayPot.class, "claypot");
		GameRegistry.registerTileEntity(BlockClayPot.TileClayPot.class, "tileClayPot");
		GameRegistry.registerTileEntity(BlockClayPot.TileClayPotDeco.class, "tileClayPotDeco");
		
		blockSeer = new BlockSeer(Material.glass).setBlockName("zenscape" + "." + "seer").setBlockTextureName("zenscape:glassthing").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockSeer,  "seerblock");
		
		blockSpawnBlock = new BlockSpawnBlock(Material.rock).setBlockName("zenscape" + "." + "spawnblock").setBlockTextureName("zenscape:spawnblock").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockSpawnBlock, "spawnerblock");
		GameRegistry.registerTileEntity(BlockSpawnBlock.TileSpawnBlock.class, "tileSpawnBlock");
		
		blockDarkGrass = new BlockDarkGrass(Material.plants).setBlockName("zenscape" + "." + "darkgrass").setBlockTextureName("zenscape:darkgrass").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockDarkGrass, "darkgrass");
		
		blockSorter = new BlockChestSorter(Material.wood).setHardness(2.0F).setBlockName("zenscape" + "." + "chestsorter").setBlockTextureName("minecraft:planks_jungle").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockSorter, "sortertile");
		GameRegistry.registerTileEntity(BlockChestSorter.TileSorter.class, "tileChestSorter");
		
		blockPistonCrafter = new BlockPistonCrafter(Material.wood).setHardness(2.0F).setBlockName("zenscape" + "." + "pistoncrafter").setBlockTextureName("zenscape:pistoncrafter").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockPistonCrafter, "pistoncrafter");
		GameRegistry.registerTileEntity(BlockPistonCrafter.TilePistonCrafter.class, "tilePistonCrafter");
		
		blockZenBush = new BlockZenBush(Material.plants).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "bush").setBlockTextureName("zenscape:bush").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockZenBush, "yellowbush");
		
		blockColdLava = new BlockColdLava(Material.rock).setHardness(5.0F).setStepSound(Block.soundTypeStone).setBlockName("zenscape" + "." + "coldlava").setBlockTextureName("zenscape:coldlava").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockColdLava, "coldlava");
		
		blockRAC = new BlockRAC(Material.wood).setHardness(2.0F).setBlockName("zenscape" + "." + "rac").setBlockTextureName("minecraft:planks_birch").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockRAC, "ractile");
		GameRegistry.registerTileEntity(BlockRAC.TileRAC.class, "tileRAC");
		
		blockJoker = new BlockJoker(Material.leaves).setHardness(0.2F).setBlockName("zenscape" + "." + "joker").setBlockTextureName("zenscape:jokertile").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockJoker, "jokertile");
		GameRegistry.registerTileEntity(BlockJoker.TileJoker.class, "tileJoker");
		
		blockDarkAir = new BlockDarkAir(Material.air).setBlockName("zenscape" + "." + "darkair").setBlockTextureName("zenscape:darkair");
		GameRegistry.registerBlock(blockDarkAir, "blockDarkAir");
		
		blockEnchantree = new BlockEnchantree(Material.wood).setHardness(2.0F).setBlockName("zenscape" + "." + "enchantree").setBlockTextureName("zenscape:log_enchant_tile").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockEnchantree, "blockEnchantree");
		GameRegistry.registerTileEntity(BlockEnchantree.TileEnchantree.class, "tileEnchantree");
		
		blockCopyCat = new BlockCopyCat(Material.wood).setBlockName("zenscape" + "." + "copycat").setBlockTextureName("zenscape:tilecopycat").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockCopyCat, "blockCopyCat");
		GameRegistry.registerTileEntity(BlockCopyCat.TileCopyCat.class, "tileCopyCat");
		
		blockAirBubble = new BlockAirBubble(Material.rock).setBlockName("zenscape" + "." + "airbubble").setBlockTextureName("zenscape:zenbrick11").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockAirBubble, "blockAirBubble");
		
		blockPotion = new BlockPotion(Material.plants).setBlockName("zenscape" + "." + "potionblock").setBlockTextureName("zenscape:blockpotion");
		GameRegistry.registerBlock(blockPotion, "blockPotion");
		
		blockHeartCake = new BlockHeartCake().setBlockName("zenscape" + "." + "heartcake").setBlockTextureName("zenscape:heartcake").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockHeartCake, "blockHeartCake");
		
		blockRainThistle = new BlockRainThistle(Material.plants).setBlockName("zenscape" + "." + "rainthistle").setBlockTextureName("rainthistle").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockRainThistle, BlockRainThistle.ItemRainThistle.class, "blockRainThistle");
		GameRegistry.registerTileEntity(BlockRainThistle.TileRainThistle.class, "tileRainThistle");
		
		blockAncientStone = new BlockAncientStone(Material.rock).setBlockName("zenscape" + "." + "ancientstone").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockAncientStone, BlockAncientStone.ItemAncientStone.class, "blockAncientStone");
		
		blockAncientGrass = new BlockAncientGrass(Material.ground).setBlockName("zenscape" + "." + "ancientgrass").setBlockTextureName("minecraft:dirt").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockAncientGrass, "blockAncientGrass");
		
		blockAncientGravel = new BlockAncientGravel().setBlockName("zenscape" + "." + "ancientgravel").setBlockTextureName("zenscape:ancient_gravel").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockAncientGravel, "blockAncientGravel");
		
		blockZenSand = new BlockZenSand().setBlockName("zenscape" + "." + "zensand").setStepSound(Block.soundTypeSand).setHardness(0.5F).setBlockTextureName("zensand");
		GameRegistry.registerBlock(blockZenSand, "blockZenSand");
		
		GameRegistry.registerBlock(blockGreenStairs = new BlockZenStairs.BlockGreenStairs("zenscape.greenstairs", ZenScape.blockZenBricks, 0), "zenscape.greenstairs").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockRedStairs = new BlockZenStairs.BlockRedStairs("zenscape.redstairs", ZenScape.blockZenBricks, 4), "zenscape.redstairs").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockBlueStairs = new BlockZenStairs.BlockBlueStairs("zenscape.bluestairs", ZenScape.blockZenBricks, 8), "zenscape.bluestairs").setCreativeTab(zenscapeTab);
		GameRegistry.registerBlock(blockBlackStairs = new BlockZenStairs.BlockBlackStairs("zenscape.blackstairs", ZenScape.blockZenBricks, 12), "zenscape.blackstairs").setCreativeTab(zenscapeTab);
		
		slab = new BlockZenSlabs("zenscape.slab", false, Material.rock).setHardness(3.0F).setCreativeTab(zenscapeTab);	
		double_slab = new BlockZenSlabs("double_slab", true, Material.rock).setHardness(3.0F).setCreativeTab(zenscapeTab);
	
		// UNOBTAINIUM
		blockZenLeaf = new BlockZenLeaf(Material.leaves).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "flywoodleaf").setCreativeTab(debugTab);
		GameRegistry.registerBlock(blockZenLeaf, "invisibleleaves");
		
		blockZenTree = new BlockZenTree(Material.wood).setHardness(2.0F).setStepSound(Block.soundTypeWood).setBlockName("zenscape" + "." + "flywood").setCreativeTab(debugTab);
		GameRegistry.registerBlock(blockZenTree, "invisblelog");
		
		blockZenBrick = new BlockZenBrick(Material.rock).setHardness(3.0F).setStepSound(Block.soundTypeStone).setBlockName("zenscape" + "." + "texbrick").setCreativeTab(debugTab);
		GameRegistry.registerBlock(blockZenBrick, BlockZenBrick.ItemTexBricks.class, "invisiblebrick");
		
		blockZenDirtGrass = new BlockZenDirtGrass(Material.ground).setHardness(0.55F).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "dirtgrass").setCreativeTab(debugTab);
		GameRegistry.registerBlock(blockZenDirtGrass, BlockZenDirtGrass.ItemSoil.class, "zensoil");
		
		blockZenSkyBush = new BlockZenSkyBush(Material.plants).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("zenscape" + "." + "skybush").setCreativeTab(debugTab);
		GameRegistry.registerBlock(blockZenSkyBush, "skybush");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		
		logger.info("Begin Initialization");
		
		proxy.registerRenderers();
		
		GameRegistry.registerBlock(double_slab, BlockZenSlabs.ItemBlockZenSlabs.class, "double_slab", slab, double_slab, true);
		GameRegistry.registerBlock(slab, BlockZenSlabs.ItemBlockZenSlabs.class, "zenscape.slab", slab, double_slab, false);
		
		GameRegistry.addShapelessRecipe(new ItemStack(itemTestStaff), new ItemStack(Items.golden_hoe, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(blockZenSapling, 1, 1), new Object[] { Blocks.sapling, Blocks.crafting_table});
		GameRegistry.addShapelessRecipe(new ItemStack(blockZenSapling, 1, 2), new Object[] { Blocks.sapling, Blocks.tnt });
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 4), new Object[] { Dyes.LAPIS_LAZULI.createStack(1), Blocks.iron_block});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 5), new Object[] { ZenScape.blockFruitBomb});
		GameRegistry.addShapelessRecipe(new ItemStack(itemCompost), new Object[] { Dyes.BONE_MEAL.createStack(), Dyes.CACTUS_GREEN.createStack() });
		GameRegistry.addShapelessRecipe(new ItemStack(itemDietPills, 3), new Object[] { new ItemStack(itemAlchemyBottles, 1, 6) });
		GameRegistry.addShapelessRecipe(new ItemStack(itemPodzolBall, 4), new Object[] { new ItemStack(Blocks.leaves, 1, 0), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3) });

		GameRegistry.addShapelessRecipe(new ItemStack(Items.potionitem, 1, 8196), new Object[] { ZenScape.itemDokuBottle, Items.fermented_spider_eye });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.potionitem, 1, 8228), new Object[] { ZenScape.itemDokuBottle, Items.glowstone_dust, Items.fermented_spider_eye });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.potionitem, 1, 16388), new Object[] { ZenScape.itemDokuBottle, Items.fermented_spider_eye, Items.gunpowder });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.potionitem, 1, 16420), new Object[] { ZenScape.itemDokuBottle, Items.fermented_spider_eye, Items.glowstone_dust, Items.gunpowder });
		
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 1, 1), new Object[] { new ItemStack(blockBlackMesa, 1, 0) });
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 1, 2), new Object[] { new ItemStack(blockBlackMesa, 1, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 1, 3), new Object[] { new ItemStack(blockBlackMesa, 1, 2) });
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 1, 4), new Object[] { new ItemStack(blockBlackMesa, 1, 3) });
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 1, 5), new Object[] { new ItemStack(blockBlackMesa, 1, 4) });
		GameRegistry.addShapelessRecipe(new ItemStack(blockBlackMesa, 1, 0), new Object[] { new ItemStack(blockBlackMesa, 1, 5) });
		
		GameRegistry.addShapelessRecipe(new ItemStack(slab, 1, 1), new Object[] { new ItemStack(slab, 1, 0) });
		GameRegistry.addShapelessRecipe(new ItemStack(slab, 1, 3), new Object[] { new ItemStack(slab, 1, 2) });
		GameRegistry.addShapelessRecipe(new ItemStack(slab, 1, 5), new Object[] { new ItemStack(slab, 1, 4) });
		GameRegistry.addShapelessRecipe(new ItemStack(slab, 1, 7), new Object[] { new ItemStack(slab, 1, 6) });
	
		GameRegistry.addShapedRecipe(new ItemStack(itemGrafterNet, 1), new Object[] { "  t", " ts", "tss", 't', Items.stick, 's', Items.string });
		GameRegistry.addShapedRecipe(new ItemStack(itemLilypadBag, 1), new Object[] { "LSL", "LPL", "LLL", 'L', Items.leather, 'S', Items.string, 'P', Blocks.waterlily });
		GameRegistry.addShapedRecipe(new ItemStack(itemCakePickaxe, 1), new Object[] { "CCC", " S ", " S ", 'C', Items.cake, 'S', Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(itemClayShovel, 1), new Object[] { " C ", " S ", " S ", 'C', Blocks.hardened_clay, 'S', Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(blockDokuPot, 1), new Object[] { "c c", "c c", "ccc", 'c', new ItemStack(Blocks.stained_hardened_clay, 0)});
		GameRegistry.addShapedRecipe(new ItemStack(blockLightBlock, 1), new Object[] { " p ", "pip", " p ", 'p', itemLightPlate, 'i', Blocks.iron_block });
		GameRegistry.addShapedRecipe(new ItemStack(blockGreenStairs, 4), new Object[] { "  G", " GG", "GGG", 'G', new ItemStack(ZenScape.blockZenBricks, 1, 0) });
		GameRegistry.addShapedRecipe(new ItemStack(blockGreenStairs, 4), new Object[] { "G  ", "GG ", "GGG", 'G', new ItemStack(ZenScape.blockZenBricks, 1, 0) });
		GameRegistry.addShapedRecipe(new ItemStack(blockRedStairs, 4), new Object[] { "  R", " RR", "RRR", 'R', new ItemStack(ZenScape.blockZenBricks, 1, 5) });
		GameRegistry.addShapedRecipe(new ItemStack(blockRedStairs, 4), new Object[] { "R  ", "RR ", "RRR", 'R', new ItemStack(ZenScape.blockZenBricks, 1, 5) });
		GameRegistry.addShapedRecipe(new ItemStack(blockBlueStairs, 4), new Object[] { "  B", " BB", "BBB", 'B', new ItemStack(ZenScape.blockZenBricks, 1, 10) });
		GameRegistry.addShapedRecipe(new ItemStack(blockBlueStairs, 4), new Object[] { "B  ", "BB ", "BBB", 'B', new ItemStack(ZenScape.blockZenBricks, 1, 10) });
		
		GameRegistry.addRecipe(new ItemStack(slab, 6, 0), "###", '#', new ItemStack(ZenScape.blockZenBricks, 1, 0));
		GameRegistry.addRecipe(new ItemStack(slab, 6, 2), "###", '#', new ItemStack(ZenScape.blockZenBricks, 1, 4));
		GameRegistry.addRecipe(new ItemStack(slab, 6, 4), "###", '#', new ItemStack(ZenScape.blockZenBricks, 1, 8));
		GameRegistry.addRecipe(new ItemStack(slab, 6, 6), "###", '#', new ItemStack(ZenScape.blockZenBricks, 1, 12));
		
		GameRegistry.addSmelting((new ItemStack(blockZenLog, 1, 0)), (new ItemStack(itemLightPlate)), 0.0F);
		GameRegistry.addSmelting((new ItemStack(blockRainThistle, 1, 1)), (new ItemStack(blockRainThistle, 1, 0)), 0.0F);
		
		int entityId = 0;
		EntityRegistry.registerModEntity(EntityFruitBomb.class, "FruitBomb", entityId++, this, 64, 20, true);
		EntityRegistry.registerModEntity(EntityDokuDrop.class, "ProjDokuDrop", entityId++, this, 48, 20, true);
		EntityRegistry.registerModEntity(EntityPuffball.class, "ProjPuff", entityId++, this, 48, 20, true);
		EntityRegistry.registerModEntity(EntityPodzolBall.class, "ProjPodzolBall", entityId++, this, 48, 20, true);

		ZCrafting.addRecipe(new ItemStack(ZenScape.blockZenSapling, 1, 8), new ItemStack[] { new ItemStack(Blocks.redstone_block), new ItemStack(Items.redstone), new ItemStack(Blocks.redstone_torch), new ItemStack(Blocks.log, 1, 2) });
		ZCrafting.addRecipe(new ItemStack(ZenScape.blockZenSapling, 1, 2), new ItemStack[] { new ItemStack(Blocks.tnt), new ItemStack(Blocks.redstone_block), new ItemStack(Items.gunpowder), new ItemStack(Items.gunpowder) });
		ZCrafting.addRecipe(new ItemStack(ZenScape.blockZenSapling, 1, 5), new ItemStack[] { new ItemStack(Items.potionitem, 1, 8196), new ItemStack(Items.nether_wart), new ItemStack(Items.spider_eye), new ItemStack(Items.fermented_spider_eye) });
		ZCrafting.addRecipe(new ItemStack(Blocks.diamond_block), new ItemStack[] { new ItemStack(Items.diamond_axe), new ItemStack(Items.diamond_hoe), new ItemStack(Items.diamond_pickaxe), new ItemStack(Items.diamond_shovel) });
	
		ZPistonCraft.addRecipe(ZenScape.blockZenSapling, 1, new ItemStack[] { new ItemStack(Blocks.crafting_table), new ItemStack(Blocks.crafting_table), new ItemStack(Blocks.crafting_table), new ItemStack(Blocks.crafting_table), new ItemStack(Blocks.crafting_table), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 1) });
		ZPistonCraft.addRecipe(ZenScape.blockZenSapling, 4, new ItemStack[] { new ItemStack(Blocks.wool, 1, 4), new ItemStack(Blocks.stained_hardened_clay, 1, 4), new ItemStack(Blocks.stained_glass, 1, 4), new ItemStack(Blocks.gold_block) });
		ZPistonCraft.addRecipe(Blocks.anvil, 0, new ItemStack[] { new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_bars) });

		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 0, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Items.blaze_rod), new ItemStack(Items.gunpowder), new ItemStack(Items.blaze_powder) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 1, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.ice), new ItemStack(Items.sugar), new ItemStack(Items.snowball) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 2, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), Dyes.PINK_DYE.createStack(), new ItemStack(Blocks.wool, 1, 6), new ItemStack(Items.wheat) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 3, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), Dyes.COCOA_BEANS.createStack(), new ItemStack(Blocks.leaves, 1, 3), new ItemStack(ZenScape.itemAlchemyBottles, 1, 5), new ItemStack(ZenScape.itemAlchemyBottles, 1, 2) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 4, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Items.ender_pearl), new ItemStack(Blocks.end_stone), new ItemStack(ZenScape.itemAlchemyBottles, 1, 4) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 5, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(ZenScape.itemAlchemyBottles, 1, 1) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 6, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Items.potionitem, 1, 8238) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 7, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.redstone_block), new ItemStack(Items.redstone) });
		ZPadCrafting.addRecipe(ZenScape.blockZenLily, 9, new ItemStack[] { new ItemStack(Blocks.sapling, 1, 3), new ItemStack(ZenScape.blockZenLily, 1, 4), new ItemStack(Items.ender_eye) });

		ZEnchanter.addRecipe(new ItemStack(Blocks.chest), new ItemStack[] { new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 3) });
		
		GameRegistry.registerWorldGenerator(new WorldGenDownUnder(), 2);
		GameRegistry.registerWorldGenerator(new WorldGenIslands(), 2);
		GameRegistry.registerWorldGenerator(new WorldGenNether(), 2);
		GameRegistry.registerWorldGenerator(new WorldGenOcean(), 2);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		logger.info("Begin Post-initialization");
	}
	
	@Mod.EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		
		if (ZBlockHandler.INSTANCE == null)
			ZBlockHandler.INSTANCE = new ZBlockHandler();
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			World world = MinecraftServer.getServer().getEntityWorld();
			if (!world.isRemote)
			{
				logger.info("WorldData loading");
				ZSaveData saveData = (ZSaveData)world.loadItemData(ZSaveData.class, ZSaveData.dataName);
				if (saveData == null)
				{
					logger.info("WorldData not found");
					saveData = new ZSaveData(ZSaveData.dataName);
					world.setItemData(ZSaveData.dataName, saveData);
				}
				else
					logger.info("WorldData retrieved");
				ZSaveData.setInstance(world.provider.dimensionId, saveData);
			}
		}
	}
}
