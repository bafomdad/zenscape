package com.bafomdad.zenscape.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBombStaff extends Item {

	public ItemBombStaff() {
		
		setMaxStackSize(1);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (!world.isRemote)
		{
			int playerX = MathHelper.floor_double(player.posX);
			int playerY = MathHelper.floor_double(player.posY);
			int playerZ = MathHelper.floor_double(player.posZ);
		
			for (int x1 = 0; x1 < 4; x1++) {
				for (int y1 = 0; y1 < 4; y1++) {
					for (int z1 = 0; z1 < 4; z1++) {
						int x = playerX + x1;
						int y = playerY + y1;
						int z = playerZ + z1;
						
					}
				}
			}
		}		
		return stack;
	}
}
