package com.bafomdad.zenscape.blocks;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.BlockContainerZenScape;
import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.entity.EntityFruitBomb;
import com.bafomdad.zenscape.items.ItemGrafterNet;
import com.bafomdad.zenscape.model.ModelFruitBomb;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFruitBomb extends BlockContainerZenScape {
	
	public Block block;
	public int meta;
	public EntityPlayer player;

	public BlockFruitBomb(Material material) {
		
		super(material);
		this.setBlockBounds(0.2375F, 0.4F, 0.2375F, 0.7625F, 1.0F, 0.7625F);
		this.setTickRandomly(true);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y + 1, z);
		int meta = world.getBlockMetadata(x, y + 1, z);
		return ((block == ZenScape.blockZenLeaves) && (meta == 1));
	}
	
	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
		if (!canPlaceBlockAt(world, x, y, z))
		{
			this.dropTheBass(world, x, y, z);
		}
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
		
		if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ZenScape.itemGrafterNet)
		{
			world.setBlockToAir(x, y, z);
			player.inventory.addItemStackToInventory(new ItemStack(ZenScape.blockFruitBomb));
		}
		else
			this.dropTheBass(world, x, y, z);
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		return new TileEntityFruitBomb();
	}
	
	@Override
	public int getRenderType() {
		
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		world.scheduleBlockUpdate(x, y, z, this, 5);
		
		EntityPlayer ep = world.getClosestPlayer(x, y, z, 10);
		if (ep != null && (!ep.capabilities.isCreativeMode) && (!ep.isSneaking()))
			this.dropTheBass(world, x, y, z);
	}

    public void dropTheBass(World world, int x, int y, int z) {
        
	    	if (!world.isRemote) {
	    	world.setBlockToAir(x, y, z);
	    	EntityFruitBomb efb = new EntityFruitBomb(world, block, meta);
	   		efb.setPosition(x + 0.25, y - 0.56, z + 0.75);
	   		world.spawnEntityInWorld(efb);
    	}
    }
/**
 * 
 * Tile Entity
 *
 */
	public static class TileEntityFruitBomb extends TileEntityZenScape {
	
	}
/**
 * 
 * Tile Entity Special Renderer
 *
 */
	public static class TileEntityFruitBombRenderer extends TileEntitySpecialRenderer {
		
		private final ModelFruitBomb model;
		
		public TileEntityFruitBombRenderer() {
			
			this.model = new ModelFruitBomb();
		}

		@Override
		public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
			
			TileEntityFruitBomb tfb = (TileEntityFruitBomb)te;
			
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.75F, (float) y + 0.9F, (float) z + 0.25F);
			
			ResourceLocation textures = (new ResourceLocation("zenscape:textures/model/fruitbomb.png"));	
			Minecraft.getMinecraft().renderEngine.bindTexture(textures);
			
			GL11.glPushMatrix();
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}
}