package com.bafomdad.zenscape.render;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.model.ModelCatEye;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderCopyCat extends TileEntitySpecialRenderer {
	
	ModelCatEye cube = new ModelCatEye();
	ResourceLocation texture = new ResourceLocation(ZenScape.MOD_ID, "textures/model/copycat_eye.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {

		float f5 = 0.0525F;
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		bindTexture(texture);
		cube.render(tile, f5);
		
		GL11.glPopMatrix();
	}
}
