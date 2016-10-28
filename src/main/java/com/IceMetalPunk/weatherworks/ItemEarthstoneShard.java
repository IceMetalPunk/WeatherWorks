package com.IceMetalPunk.weatherworks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemEarthstoneShard extends Item {
	public ItemEarthstoneShard() {
		super();
		this.setMaxDamage(0);
		this.setUnlocalizedName("earthstone_shard");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setMaxStackSize(64);
		this.setTextureName("weatherworks:earthstone_shard");
	}
}
