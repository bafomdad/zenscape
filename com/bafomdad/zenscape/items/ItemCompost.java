package com.bafomdad.zenscape.items;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.zenscape.crafting.ZPadCrafting;
import com.bafomdad.zenscape.network.ZPacketDispatcher;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemCompost extends Item {
	
	public List<ItemStack> eventList = new ArrayList<ItemStack>();

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	Block block = world.getBlock(x, y, z);
    	
    	if (block != null && block == Blocks.waterlily)
    	{
			eventList.clear();
			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
			for (EntityItem item : items) {
				if (item != null) {
					if (!eventList.contains(item.getEntityItem()))
						eventList.add(item.getEntityItem());
					
					for (ZPadCrafting recipes : ZPadCrafting.lilyRecipes) {
						if (recipes.matches(this)) 
						{
							eventList.clear();
							Block output = (Block)recipes.getOutput();
							int meta = recipes.getOutputMeta();
							world.setBlock(x, y, z, output, meta, 2);
							if (!player.capabilities.isCreativeMode)
								player.getCurrentEquippedItem().stackSize--;
							for (int i = 0; i < items.size(); i++) {
								ZPacketDispatcher.clientParticle("cloud", items.get(i).posX, items.get(i).posY, items.get(i).posZ, 0.0D, 0.1D, 0.0D);
								items.get(i).setDead();
							}		
						}
					}
				}
			}
    	}
        return false;
    }
}
