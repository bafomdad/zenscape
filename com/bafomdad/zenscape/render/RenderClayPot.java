package com.bafomdad.zenscape.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockClayPot;
import com.bafomdad.zenscape.blocks.BlockClayPot.TileClayPotDeco;
import com.bafomdad.zenscape.model.ModelClayPot;

public class RenderClayPot extends TileEntitySpecialRenderer {

	private ModelClayPot model = new ModelClayPot();
	private ResourceLocation texture = new ResourceLocation("zenscape", "textures/model/claypot.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		if (te instanceof BlockClayPot.TileClayPot)
			this.renderClayPotAt((BlockClayPot.TileClayPot)te, x + 0.5, y, z + 0.5);
		if (te instanceof BlockClayPot.TileClayPotDeco)
			this.renderClayPotDecoAt((BlockClayPot.TileClayPotDeco)te, x + 0.5, y, z + 0.5);
	}
	
	private void renderClayPotAt(BlockClayPot.TileClayPot te, double x, double y, double z) {
		
		float f = 0.0625F;
		boolean canRender = false;
		EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;

		if (te != null && te.getWorldObj() != null)
		{
			if ((player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == ZenScape.itemGoggles) || ((player.capabilities.isCreativeMode))) 
			{	
				canRender = true;
			}
		}
		else
		{
			canRender = true;
		}
		
		if (canRender)
		{
			GL11.glPushMatrix();
//			GL11.glTranslatef((float)x + 0.5F, (float)y + 0.3F, (float)z + 0.5F);
			GL11.glTranslatef((float)x, (float)y + 0.3F, (float)z);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			bindTexture(texture);
			model.render(te, f);
			GL11.glPopMatrix();
		}
	}
	
	private void renderClayPotDecoAt(BlockClayPot.TileClayPotDeco te, double x, double y, double z) {
		
		float f = 0.0625F;

		GL11.glPushMatrix();
//		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.3F, (float)z + 0.5F);
		GL11.glTranslatef((float)x, (float)y + 0.3F, (float)z);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		bindTexture(texture);
		model.render(te, f);
		GL11.glPopMatrix();
	}
	
	public static class RenderClayPotItem implements IItemRenderer {

		private final ModelClayPot model;
		
		public RenderClayPotItem() {
			
			this.model = new ModelClayPot();
		}
		
		@Override
		public boolean handleRenderType(ItemStack item, ItemRenderType type) {

			return true;
		}

		@Override
		public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

			return true;
		}

		@Override
		public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
			
			if (type != ItemRenderType.ENTITY)
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new BlockClayPot.TileClayPot(), 0.0D, 0.3D, 0.0D, 0.5F);
			else
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new BlockClayPot.TileClayPot(), -0.5D, 0.3D, -0.5D, 0.5F);
		}
	}
}
