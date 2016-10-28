package com.IceMetalPunk.weatherworks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

public class BlockEarthstoneOre extends BlockOre {
	public BlockEarthstoneOre() {
		super();
		this.setBlockTextureName("weatherworks:earthstone_ore");
		this.setBlockName("earthstone_ore");
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return WeatherWorks.earthstoneShard;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3)+1;
	}
	
}
