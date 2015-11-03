package com.bafomdad.zenscape.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFruitBomb extends ModelBase {

    ModelRenderer Shape1;
    ModelRenderer Shape2;
  
  public ModelFruitBomb()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 16);
      Shape1.addBox(0F, 0F, 0F, 8, 8, 8);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(32, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 0);
      Shape2.addBox(3F, -2F, 3F, 2, 2, 2);
      Shape2.setRotationPoint(0F, 0F, 0F);
      Shape2.setTextureSize(32, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	  
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, float f6, Entity entity) {
   
	  super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
