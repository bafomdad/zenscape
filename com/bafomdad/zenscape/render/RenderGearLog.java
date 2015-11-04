package com.bafomdad.zenscape.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockGearLog;
import com.bafomdad.zenscape.blocks.BlockGearLog.TileGearLog;
import com.bafomdad.zenscape.model.ModelTestGear;

public class RenderGearLog extends TileEntitySpecialRenderer {

	private ModelTestGear model = new ModelTestGear();
	private ResourceLocation texture = new ResourceLocation(ZenScape.MOD_ID, "textures/model/gearlog.png");
	
	public void renderTileGear(TileGearLog te, double x, double y, double z, float f) {
		
		float prevRotation = te.renderRotation;
		te.renderRotation = te.rotation;
		te.renderRotation = prevRotation + (te.renderRotation - prevRotation) * f;
		
		float f5 = 0.0625F;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		
		if (te.facingMeta == 4) {
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
		}
		if (te.facingMeta == 8) {
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		}
		
		if (!te.isRotating)
			prevRotation = 0.0F;
		GL11.glRotatef(prevRotation, 0.0F, 1.0F, 0.0F);
		
		bindTexture(texture);
		model.render(te, f5);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		
		this.renderTileGear((TileGearLog) te, x, y, z, f);
	}
}
