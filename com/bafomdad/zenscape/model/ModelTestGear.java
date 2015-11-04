package com.bafomdad.zenscape.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import com.bafomdad.zenscape.blocks.BlockGearLog;
import com.bafomdad.zenscape.blocks.BlockGearLog.TileGearLog;

public class ModelTestGear extends ModelBase {
	
	ModelRenderer Shape1;

	public ModelTestGear() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-8F, -8F, -8F, 16, 16, 16);
		Shape1.setRotationPoint(0F, 0F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
	}

	public void render(BlockGearLog.TileGearLog te, float f) {

		Shape1.render(f);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
