package com.bafomdad.zenscape.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ZSTickHandler {

	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;
	
	private void calcDelta() {
		
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}
	
	@SubscribeEvent
	public void renderTick(RenderTickEvent event) {
		
		if (event.phase == Phase.START)
			partialTicks = event.renderTickTime;
		else {
			calcDelta();
		}
	}
	
	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		
		GuiScreen gui = Minecraft.getMinecraft().currentScreen;
		if (gui == null || !gui.doesGuiPauseGame())
			ticksInGame++;
		
		calcDelta();
	}
}
