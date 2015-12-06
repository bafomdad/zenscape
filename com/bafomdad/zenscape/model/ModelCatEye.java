package com.bafomdad.zenscape.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

import com.bafomdad.zenscape.blocks.BlockGearLog;
import com.bafomdad.zenscape.blocks.BlockGearLog.TileGearLog;

public class ModelCatEye extends ModelBase {
	
	ModelRenderer Shape1;

	public ModelCatEye() {
		
		textureWidth = 12;
		textureHeight = 12;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-6F, -6F, -6F, 12, 12, 12);
		Shape1.setRotationPoint(0F, 0F, 0F);
		Shape1.setTextureSize(textureWidth, textureHeight);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
	}

	public void render(TileEntity te, float f) {

		Shape1.render(f);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

