package com.bafomdad.zenscape.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.blocks.BlockJoker;

public class RenderJoker extends TileEntitySpecialRenderer {
	
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
}
