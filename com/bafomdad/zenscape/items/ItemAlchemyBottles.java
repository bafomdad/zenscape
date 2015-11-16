package com.bafomdad.zenscape.items;

import java.util.List;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemAlchemyBottles extends Item {

	public String[] bottleTypes = new String[] { "bottle_empty", "bottle_counteragent", "bottle_mixture", "vial_empty", "vial_detrimindexta", "vial_karvodailnirol", "bottle_pills" };
	public IIcon[] iconArray;
	
	public ItemAlchemyBottles() {
		
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		
		iconArray = new IIcon[bottleTypes.length];
		for (int i = 0; i < iconArray.length; i++)
		{
			iconArray[i] = register.registerIcon("zenscape:" + bottleTypes[i]);
		}
	}
	
	public IIcon getIconFromDamage(int meta) {
		
		return this.iconArray[meta];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		
		return "item." + ZenScape.MOD_ID + "." + bottleTypes[stack.getItemDamage()];
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < bottleTypes.length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
