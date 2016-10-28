package com.IceMetalPunk.weatherworks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemWaterstoneShard extends Item {
	public ItemWaterstoneShard() {
		super();
		this.setMaxDamage(0);
		this.setUnlocalizedName("rainstone_shard");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setMaxStackSize(64);
		this.setTextureName("weatherworks:rainstone_shard");
	}
}
