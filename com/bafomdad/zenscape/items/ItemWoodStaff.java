package com.bafomdad.zenscape.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.IShearable;

public class ItemWoodStaff extends Item {

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
