package com.bafomdad.zenscape.items;

import java.util.List;

import com.bafomdad.zenscape.util.ItemCardRecipe;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class ItemCard extends Item {

	public static final String TAG_HIGHLIGHT = "ZenScape_hitBox";
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public ItemCard() {
		
		GameRegistry.addRecipe(new ItemCardRecipe());
		RecipeSorter.register("zenscape:card", ItemCardRecipe.class, Category.SHAPELESS, "");
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		if (stack.getItemDamage() == 0)
		{
			list.add("Craft with any tool");
			list.add("to let them highlight");
			list.add("certain blocks.");
		}
		else
			list.add("Wtf make this do something else");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		
		icons = new IIcon[6];
		for (int i = 0; i < icons.length; i++)
		{
			icons[i] = register.registerIcon("zenscape:" + this.iconString + i);
		}
	}
	
	public IIcon getIconFromDamage(int meta) {
		
		return this.icons[meta];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		
		return this.getUnlocalizedName() + stack.getItemDamage();
	}
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < 6; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
