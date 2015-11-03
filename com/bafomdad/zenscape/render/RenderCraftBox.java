package com.bafomdad.zenscape.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.ForgeChunkManager.Type;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.blocks.BlockCraftBox;
import com.bafomdad.zenscape.model.ModelCraftBox;

public class RenderCraftBox extends TileEntitySpecialRenderer {
	
	final ModelCraftBox model;
	private ResourceLocation texture = new ResourceLocation("zenscape", "textures/model/craftbox.png");
	
	public RenderCraftBox() {
		
		this.model = new ModelCraftBox();
	}

	public void renderTileEntityAt(TileEntity te, double d, double d1, double d2, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d, (float)d1, (float)d2);
		BlockCraftBox.TileCraftBox tileCraftBox = (BlockCraftBox.TileCraftBox)te;
		this.renderCraftBoxAt(tileCraftBox, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		GL11.glPopMatrix();
	}
	
	private void renderCraftBoxAt(BlockCraftBox.TileCraftBox tileCraftBox, World world, double x, double y, double z) {
		
		float f = 0.0625F;
		
		GL11.glPushMatrix();
		if (tileCraftBox != null && tileCraftBox.getWorldObj() != null)
		{
			GL11.glTranslatef(0.5F, 0.0F, 0.5F);
		}
		GL11.glTranslatef(0.0F, 1.5F, 0.0F);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		bindTexture(texture);
		
		this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f);
		
		GL11.glPopMatrix();
	}
	
	public static class RenderCraftBoxItem implements IItemRenderer {

		private final ModelCraftBox model;
		
		public RenderCraftBoxItem() {
			
			this.model = new ModelCraftBox();
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
			
			if (!(type == type.EQUIPPED_FIRST_PERSON))
			{
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new BlockCraftBox.TileCraftBox(), 0.0D, 0.0D, 0.0D, 0.0F);
			}
			else
			{
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new BlockCraftBox.TileCraftBox(), 0.0D, 0.5D, 0.0D, 0.0F);
			}
		}
	}
}
