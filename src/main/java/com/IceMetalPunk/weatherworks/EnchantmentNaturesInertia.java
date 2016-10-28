package com.IceMetalPunk.weatherworks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentNaturesInertia extends Enchantment {

	public EnchantmentNaturesInertia(int ID, int rarity) {
		super(ID, rarity, EnumEnchantmentType.armor);
		this.setName("naturesInertia");
		Enchantment.addToBookList(this);
	}
	
	@Override
	public int getMinEnchantability(int lvl) {
		return 5;
	}
	
	@Override
	public int getMaxEnchantability(int lvl) {
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}

}
