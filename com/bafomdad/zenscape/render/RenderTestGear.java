package com.bafomdad.zenscape.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockTestGear;
import com.bafomdad.zenscape.model.ModelTestGear;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTestGear extends TileEntitySpecialRenderer {

	private ModelTestGear model = new ModelTestGear();
	private ResourceLocation texture = new ResourceLocation(ZenScape.MOD_ID, "textures/model/gearlog.png");
	
	public void renderTileGear(BlockTestGear.TileTestGear te, double x, double y, double z, float f) {
		
		float f5 = 0.0625F;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		bindTexture(texture);
		model.render(te, f5);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		
		this.renderTileGear((BlockTestGear.TileTestGear) te, x, y, z, f);
	}
}
