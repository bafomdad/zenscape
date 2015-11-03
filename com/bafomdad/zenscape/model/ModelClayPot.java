package com.bafomdad.zenscape.model;

import com.bafomdad.zenscape.blocks.BlockClayPot;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelClayPot extends ModelBase {

    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
  
  public ModelClayPot() {
    
	  textureWidth = 64;
	  textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 18, 8);
      Shape1.addBox(-4F, -4F, -4F, 8, 8, 8);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 23, 0);
      Shape2.addBox(-3F, -5F, -3F, 6, 10, 6);
      Shape2.setRotationPoint(0F, 0F, 0F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 25, 10);
      Shape3.addBox(-2F, -2F, -2F, 4, 4, 4);
      Shape3.setRotationPoint(0F, -5F, 0F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 24, 8);
      Shape4.addBox(-2.5F, -0.5F, -2.5F, 5, 1, 5);
      Shape4.setRotationPoint(0F, -7F, 0F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 25);
      Shape5.addBox(-3F, -0.5F, -3F, 6, 1, 6);
      Shape5.setRotationPoint(0F, -8F, 0F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
  }
  
  public void render(BlockClayPot.TileClayPot te, float f) {

	  //    super.render(entity, f, f1, f2, f3, f4, f5);    
	  Shape1.render(f); 
	  Shape2.render(f);   
	  Shape3.render(f);    
	  Shape4.render(f);
	  Shape5.render(f);
  }
  
  public void render(BlockClayPot.TileClayPotDeco te, float f) {

	  //    super.render(entity, f, f1, f2, f3, f4, f5);    
	  Shape1.render(f); 
	  Shape2.render(f);   
	  Shape3.render(f);    
	  Shape4.render(f);
	  Shape5.render(f);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
	  
	  model.rotateAngleX = x;
	  model.rotateAngleY = y;
	  model.rotateAngleZ = z;	
  }
}
