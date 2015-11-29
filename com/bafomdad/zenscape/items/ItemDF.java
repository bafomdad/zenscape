package com.bafomdad.zenscape.items;

import java.util.List;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.util.DFHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDF extends Item {
	
	String itemTexture = "df";
	IIcon[] iconArray;

	public enum FruitType {
		
		PARAMECIA,
		ZOAN,
		LOGIA
	};
	
	public ItemDF() {
		
		this.setMaxStackSize(1);
		this.setTextureName("zenscape:df");
		this.setCreativeTab(ZenScape.debugTab);
	}
	
	public void registerIcons(IIconRegister register) {
		
		iconArray = new IIcon[3];
		for (int i = 0; i < iconArray.length; i++)
		{
			iconArray[i] = register.registerIcon("zenscape:" + this.itemTexture + i);
		}
	}
	
	public IIcon getIconFromDamage(int meta) {
		
		return this.iconArray[meta];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		
		return "item." + ZenScape.MOD_ID + "." + this.itemTexture + stack.getItemDamage();
	}
	
	public static FruitType getFruitType(ItemStack stack) {
		
		if (stack.getItemDamage() == 0)
			return FruitType.PARAMECIA;
		if (stack.getItemDamage() == 1)
			return FruitType.ZOAN;
		if (stack.getItemDamage() == 2)
			return FruitType.LOGIA;
		return null;
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return 32;
	}
	
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.eat;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		
		if (world.isRemote)
			return stack;
		
		if (!player.capabilities.isCreativeMode)
			stack.stackSize--;
		
		if (DFHelper.INSTANCE.hasFruit(player))
		{
			System.out.println("you just got dunked on");
			DFHelper.clearFruit(player);
		}
		else
			DFHelper.INSTANCE.setFruit(player, stack);
		
		return stack;
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < iconArray.length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
