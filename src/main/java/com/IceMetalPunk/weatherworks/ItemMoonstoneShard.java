package com.IceMetalPunk.weatherworks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMoonstoneShard extends Item {
	public ItemMoonstoneShard() {
		super();
		this.setMaxDamage(0);
		this.setUnlocalizedName("moonstone_shard");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setMaxStackSize(64);
		this.setTextureName("weatherworks:moonstone_shard");
	}
}
