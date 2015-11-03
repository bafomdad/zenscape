package com.bafomdad.zenscape.items;

import java.util.List;

import com.bafomdad.zenscape.entity.EntityPuffball;
import com.bafomdad.zenscape.util.ZPacketDispatcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemPuffshoot extends Item {
	
	public ItemPuffshoot() {
		
		this.maxStackSize = 1;
		this.setMaxDamage(50);
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return 72000;
	}
	
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.bow;
	}
	
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	
    	if (player.isSneaking() && stack.getItemDamage() != 0)
    	{
    		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
    	}
    	if (!player.isSneaking() && !player.isUsingItem())
    	{
    		if (stack.getItemDamage() < 50 && !world.isRemote)
    		{
    			EntityPuffball epb = new EntityPuffball(world, player);
    			world.spawnEntityInWorld(epb);
    			stack.damageItem(1, player);
    		}
    	}
    	return stack;
    }
    
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        
    	return stack;
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int time) {
    	
    	MovingObjectPosition pos = getMovingObjectPositionFromPlayer(player.worldObj, player, true);
    	if (pos != null)
    	{
	    	World world = player.worldObj;		
	    	int x = pos.blockX;
		   	int y = pos.blockY;
		   	int z = pos.blockZ;
		  
		   	Block block = world.getBlock(x, y, z);
		    		
		   	if (block != null && !world.isAirBlock(x, y, z) && block == Blocks.web && !world.isRemote)
		   	{
		    	ZPacketDispatcher.clientParticle("cloud", x + 0.5, y + 0.3, z + 0.5, 0.0D, 0.02D, 0.0D);
		    	world.setBlockToAir(x, y, z);
		    	stack.setItemDamage(stack.getItemDamage() - 1);
		    }
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
    	list.add(new ItemStack(item, 1, 0));
    	list.add(new ItemStack(item, 1, 50));
    }
    
    /*
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
    	
    }
    */
}
