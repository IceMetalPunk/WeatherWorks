package com.IceMetalPunk.weatherworks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemThunderstoneShard extends Item {
	public ItemThunderstoneShard() {
		super();
		this.setMaxDamage(0);
		this.setUnlocalizedName("thunderstone_shard");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setMaxStackSize(64);
		this.setTextureName("weatherworks:thunderstone_shard");
	}
}
