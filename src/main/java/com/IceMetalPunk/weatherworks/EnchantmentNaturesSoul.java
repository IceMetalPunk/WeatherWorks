package com.IceMetalPunk.weatherworks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentNaturesSoul extends Enchantment {

	public EnchantmentNaturesSoul(int ID, int rarity) {
		super(ID, rarity, EnumEnchantmentType.armor);
		this.setName("naturesSoul");
		Enchantment.addToBookList(this);
	}
	
	@Override
	public int getMinEnchantability(int lvl) {
		return 15;
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
