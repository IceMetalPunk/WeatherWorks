package com.IceMetalPunk.weatherworks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCloudstoneShard extends Item {
	public ItemCloudstoneShard() {
		super();
		this.setMaxDamage(0);
		this.setUnlocalizedName("cloudstone_shard");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setMaxStackSize(64);
		this.setTextureName("weatherworks:cloudstone_shard");
	}
}
