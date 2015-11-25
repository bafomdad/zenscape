package com.bafomdad.zenscape.items;

import java.util.List;

import com.bafomdad.zenscape.entity.EntityPodzolBall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPodzolBall extends Item {

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityPodzolBall(world, player));
        }
        return stack;
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean idontknowwhatthisdoes) {
    
    	list.add("Throw it at some dirt");
    }
}
