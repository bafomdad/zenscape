package com.bafomdad.zenscape.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class ZPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("ZenScape".toLowerCase());

	public static void init() {
		
		int netID = 0;
		
		INSTANCE.registerMessage(PacketFruit.class, PacketFruit.class, netID++, Side.CLIENT);
	}
}
