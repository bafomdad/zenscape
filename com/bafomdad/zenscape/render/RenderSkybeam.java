package com.bafomdad.zenscape.render;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.blocks.BlockSkybeam.TileSkybeam;
import com.bafomdad.zenscape.util.Rainbow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSkybeam extends TileEntitySpecialRenderer {

	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5f, (float)z + 0.5F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("zenscape", "textures/misc/skybeam.png"));
		Tessellator tess = Tessellator.instance;
		GL11.glEnable(32826);
		GL11.glDisable(2896);
		GL11.glAlphaFunc(516, 0.0F);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 1);
		GL11.glDepthMask(false);
		GL11.glDisable(2884);
		
		float phase = (float)(Minecraft.getSystemTime() % 2000L) / 2000.0F;
		TileSkybeam beam = (TileSkybeam)tile;
		
		float power = beam.powerlevel * 1.0F / TileSkybeam.MAX_POWER;
		if (beam.powerlevel > 0) {
			for (int i = 0; i < 8; i++) {
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float)Math.cos(2.356194490192345D * i) * 0.5F * power, 0.0F, (float)Math.sin(2.356194490192345D * i) * 0.5F * power);
				GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
				
				tess.startDrawingQuads();
				tess.setColorRGBA_F(Rainbow.r(phase * 2.0F), Rainbow.g(phase * 2.0F), Rainbow.b(phase * 2.0F), 0.5F * (1.0F - phase));
				tess.setBrightness(240);
				float w = 2.0F;
				float h = 60.0F * phase * power;
				tess.addVertexWithUV(-w, h, 0.0D, 0.0D, 0.0D);
				tess.addVertexWithUV(w, h, 0.0D, 1.0D, 0.0D);
				tess.addVertexWithUV(w, 0.0D, 0.0D, 1.0D, 1.0D);
				tess.addVertexWithUV(-w, 0.0D, 0.0D, 0.0D, 1.0D);
				
				tess.draw();
				phase = (float)(phase + 0.125D);
				if (phase > 1.0F) {
					phase -= 1.0F;
				}
				GL11.glPopMatrix();
			}
		}
		GL11.glDisable(32826);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glAlphaFunc(516, 0.1F);
		GL11.glDepthMask(true);
		GL11.glEnable(2884);
		
		GL11.glPopMatrix();
	}
}
