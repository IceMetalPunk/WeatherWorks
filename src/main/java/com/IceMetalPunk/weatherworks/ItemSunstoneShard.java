package com.IceMetalPunk.weatherworks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSunstoneShard extends Item {
	public ItemSunstoneShard() {
		super();
		this.setMaxDamage(0);
		this.setUnlocalizedName("sunstone_shard");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setMaxStackSize(64);
		this.setTextureName("weatherworks:sunstone_shard");
	}
}
