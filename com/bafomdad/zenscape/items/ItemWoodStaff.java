package com.bafomdad.zenscape.items;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class ItemWoodStaff extends Item {
	
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		
		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				for (int k = -5; k <= 5; k++)
				{
					int x = (int)player.posX;
					int y = (int)player.posY;
					int z = (int)player.posZ;
					
					Block block = world.getBlock(x + i, y + j, z + k);
					if (block != null && block == ZenScape.blockDarkAir)
					{
						world.setBlockToAir(x + i, y + j, z + k);
					}
				}
			}
		}
		return item;
	}

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase elb)
    {
    	if (elb.worldObj.isRemote)
    		return false;
    	
    	if (elb instanceof IShearable)
    	{
    		IShearable target = (IShearable)elb;
    		if (target.isShearable(stack, elb.worldObj, (int)elb.posX, (int)elb.posY, (int)elb.posZ))
    		{
    			EntitySheep sheep = (EntitySheep)target;
    			if (sheep.getFleeceColor() == 11) {
    				player.addChatMessage(new ChatComponentTranslation("Wololo"));
    				sheep.setFleeceColor(14);
    				return true;
    			}
    			else if (sheep.getFleeceColor() == 14) {
    				player.addChatMessage(new ChatComponentTranslation("Wololo"));
    				sheep.setFleeceColor(11);
    				return true;
    			}
    		}
    	}
        return false;
    }
}
