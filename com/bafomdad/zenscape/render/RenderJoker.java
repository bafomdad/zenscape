package com.bafomdad.zenscape.render;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockJoker;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class RenderJoker extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	
	public void render(BlockJoker.TileJoker tile, double x, double y, double z, float f) {
        
		GL11.glPushMatrix();
		float var10 = (float)(x - 0.5F);
		float var11 = (float)(y - 0.5F);
		float var12 = (float)(z - 0.5F);
		GL11.glTranslatef(var10, var11, var12);
		this.func_82402_b(tile);
		GL11.glPopMatrix();
	}
	
	private void func_82402_b(BlockJoker.TileJoker tile) {
		
		ItemStack stack = tile.getStackInSlot(0);
		
		if (stack != null)
			renderItem(tile, stack);
	}
	
	public void renderItem(BlockJoker.TileJoker tile, ItemStack stack) {
		
		EntityItem item = new EntityItem(tile.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
		item.getEntityItem().stackSize = 1;
		item.hoverStart = 0.0F;
		GL11.glPushMatrix();
		
		GL11.glTranslatef(1F, 0.375F, 1F);
		
		GL11.glScalef(2F, 2F, 2F);
		
		if (((stack.getItem() instanceof Item) && !(stack.getItem() instanceof ItemBlock)) || ((stack.getItem() instanceof ItemBlock) && (!RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType()))))
		{
			GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		}
		else
		{
			GL11.glRotatef(0F, 0F, 1F, 0F);
		}
		
		if (stack.getItem() instanceof ItemBlock)
		{
			GL11.glTranslatef(0F, 0.2F, 0F);
//			GL11.glRotatef(90F, 0F, 1F, 0F);
		}
		
		RenderItem.renderInFrame = true;
		RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x,double y, double z, float f) {
		
		this.render((BlockJoker.TileJoker)tile, x, y, z, f);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        Tessellator tessellator = Tessellator.instance;
        
    	renderer.setRenderBoundsFromBlock(block);
    	renderer.renderStandardBlock(block, x, y, z);
    	
    	IIcon innerSide = ((com.bafomdad.zenscape.blocks.BlockJoker)block).getIcon(4, 0);
   		float f5 = 0.0f;
    	
   		renderer.renderFaceXPos(block, x - 1.0F + f5, y, z, innerSide);
   		renderer.renderFaceXNeg(block, x + 1.0F - f5, y, z, innerSide);
   		renderer.renderFaceZPos(block, x, y, z - 1.0F + f5, innerSide);
   		renderer.renderFaceZNeg(block, x, y, z + 1.0F - f5, innerSide);
        
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return false;
	}

	@Override
	public int getRenderId() {

		return BlockJoker.renderId;
	}
}
