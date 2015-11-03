package com.bafomdad.zenscape.render;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockRAC;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiRAC extends GuiContainer {
	
	private BlockRAC.TileRAC tile;
	private final ResourceLocation guiTexture = new ResourceLocation(ZenScape.MOD_ID, "textures/gui/rac.png");

	public GuiRAC(BlockRAC.TileRAC tile, InventoryPlayer inventory, World world, int x, int y, int z) {
		
		super(new BlockRAC.ContainerRAC(inventory, tile));
		this.tile = tile;
	}
	
	public void initGui() {
		
		super.initGui();
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		fontRendererObj.drawString("Random Access Chest", 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
		drawCenteredString(this.fontRendererObj, "Slots", 131, 32, 16777215);
		String filledSlots = String.format("%.0f / %.0f", new Object[] { (double)Integer.valueOf(this.tile.getCurrentSizeInventory()), (double)Integer.valueOf(this.tile.getSizeInventory()) });	
		drawCenteredString(this.fontRendererObj, filledSlots, 131, 42, 16777215);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize)/ 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
