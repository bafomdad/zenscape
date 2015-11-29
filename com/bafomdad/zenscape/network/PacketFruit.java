package com.bafomdad.zenscape.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFruit implements IMessage, IMessageHandler<PacketFruit, IMessage> {
	
	private String key;
	
	public PacketFruit() {}
	
	public PacketFruit(String key) {
		
		this.key = key;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {

		ByteBufUtils.writeUTF8String(buffer, this.key);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {

		this.key = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public IMessage onMessage(PacketFruit message, MessageContext ctx) {

		if (message.key != null)
		{
			System.out.println("Message sent.");
		}
		return null;
	}
}
