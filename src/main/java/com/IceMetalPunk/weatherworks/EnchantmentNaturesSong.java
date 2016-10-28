package com.IceMetalPunk.weatherworks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentNaturesSong extends Enchantment {

	public EnchantmentNaturesSong(int ID, int rarity) {
		super(ID, rarity, EnumEnchantmentType.breakable);
		this.setName("naturesSong");
		Enchantment.addToBookList(this);
	}
	
	@Override
	public int getMinEnchantability(int lvl) {
		return 20;
	}
	
	@Override
	public int getMaxEnchantability(int lvl) {
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}

}
