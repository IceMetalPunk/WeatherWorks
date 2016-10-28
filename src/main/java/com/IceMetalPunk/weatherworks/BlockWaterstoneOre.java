package com.IceMetalPunk.weatherworks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

public class BlockWaterstoneOre extends BlockOre {
	public BlockWaterstoneOre() {
		super();
		this.setBlockTextureName("weatherworks:rainstone_ore");
		this.setBlockName("rainstone_ore");
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return WeatherWorks.waterstoneShard;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3)+1;
	}
	
}
