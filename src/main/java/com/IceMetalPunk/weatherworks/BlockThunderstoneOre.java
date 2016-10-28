package com.IceMetalPunk.weatherworks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

public class BlockThunderstoneOre extends BlockOre {
	public BlockThunderstoneOre() {
		super();
		this.setBlockTextureName("weatherworks:thunderstone_ore");
		this.setBlockName("thunderstone_ore");
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return WeatherWorks.thunderstoneShard;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3)+1;
	}
	
}
