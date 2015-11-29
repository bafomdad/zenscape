package com.bafomdad.zenscape.util.test;

import com.bafomdad.zenscape.util.test.ZBlockHandler.ZWorldInfo;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ZSaveData extends WorldSavedData {
	
	private static ZSaveData INSTANCE;
	public static final String dataName = "Zenscape-SaveData";

	public ZSaveData(String str) {
		
		super(str);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		int[] savedDimensions = tag.getIntArray("savedDimensions");
		
		for (int dim : savedDimensions)
		{
			NBTTagList blockList = tag.getTagList("blockList" + dim, 10);
			ZBlockHandler.INSTANCE.clearAllBlocks(dim);
			for (int i = 0; i < blockList.tagCount(); i++)
			{
				NBTTagCompound blockTag = blockList.getCompoundTagAt(i);
				ZWorldInfo info = ZWorldInfo.readNBT(blockTag);
				if (info != null)
				{
					ZBlockHandler.INSTANCE.addBlock(dim, info.coords, info);
				}
			}
		}
		
		//TODO: validating broken blocks here?
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {

		Integer[] relDim = ZBlockHandler.INSTANCE.getRelevantDimensions().toArray(new Integer[0]);
		int[] savedDimensions = new int[relDim.length];
		
		for (int i = 0; i < relDim.length; i++)
			savedDimensions[i] = relDim[i];
		
		tag.setIntArray("savedDimensions", savedDimensions);
		for (int dim : savedDimensions)
		{
			World world = MinecraftServer.getServer().worldServerForDimension(dim);
			if (world != null)
			{
				NBTTagList blockList = new NBTTagList();
				for (ZWorldInfo info : ZBlockHandler.INSTANCE.getAllBlocks(world))
				{
					blockList.appendTag(info.writeNBT());
				}
				tag.setTag("blockList" + dim, blockList);
			}
		}
	}
	
	public static void setDirty(int dimension) {
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && INSTANCE != null)
			INSTANCE.markDirty();
	}
	
	public static void setInstance(int dimension, ZSaveData save) {
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			INSTANCE = save;
	}
}
