package com.bafomdad.zenscape.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelCraftBox - Either Mojang or a mod author
 * Created using Tabula 4.1.1
 */
public class ModelCraftBox extends ModelBase {
    public ModelRenderer Shape5;
    public ModelRenderer Shape6;
    public ModelRenderer Shape7;
    public ModelRenderer Shape8;
    public ModelRenderer Shape1;
    public ModelRenderer Shape2;
    public ModelRenderer Shape3;
    public ModelRenderer Shape4;
    public ModelRenderer Shape9;
    public ModelRenderer Shape10;

    public ModelCraftBox() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Shape9 = new ModelRenderer(this, 48, 18);
        this.Shape9.setRotationPoint(-7.2F, 16.0F, 5.1F);
        this.Shape9.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Shape9, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape10 = new ModelRenderer(this, 48, 18);
        this.Shape10.setRotationPoint(2.9F, 16.0F, -4.9F);
        this.Shape10.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Shape10, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape5 = new ModelRenderer(this, 0, 18);
        this.Shape5.setRotationPoint(2.0F, 18.0F, 4.5F);
        this.Shape5.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Shape5, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape1 = new ModelRenderer(this, 0, 0);
        this.Shape1.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.Shape1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);
        this.Shape8 = new ModelRenderer(this, 48, 18);
        this.Shape8.setRotationPoint(2.6F, 16.0F, 4.6F);
        this.Shape8.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Shape8, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape6 = new ModelRenderer(this, 16, 18);
        this.Shape6.setRotationPoint(-6.0F, 21.0F, 0.0F);
        this.Shape6.addBox(0.0F, 0.0F, 0.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Shape6, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape4 = new ModelRenderer(this, 0, 18);
        this.Shape4.setRotationPoint(-8.0F, 18.0F, 5.0F);
        this.Shape4.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Shape4, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape7 = new ModelRenderer(this, 48, 18);
        this.Shape7.setRotationPoint(-7.1F, 16.0F, -5.1F);
        this.Shape7.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Shape7, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape3 = new ModelRenderer(this, 0, 18);
        this.Shape3.setRotationPoint(-8.0F, 18.0F, -5.0F);
        this.Shape3.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Shape3, 0.0F, 0.7853981633974483F, 0.0F);
        this.Shape2 = new ModelRenderer(this, 0, 18);
        this.Shape2.setRotationPoint(2.0F, 18.0F, -5.0F);
        this.Shape2.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Shape2, 0.0F, 0.7853981633974483F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Shape9.render(f5);
        this.Shape10.render(f5);
        this.Shape5.render(f5);
        this.Shape1.render(f5);
        this.Shape8.render(f5);
        this.Shape6.render(f5);
        this.Shape4.render(f5);
        this.Shape7.render(f5);
        this.Shape3.render(f5);
        this.Shape2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

