package com.IceMetalPunk.weatherworks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

public class BlockMoonstoneOre extends BlockOre {
	public BlockMoonstoneOre() {
		super();
		this.setBlockTextureName("weatherworks:moonstone_ore");
		this.setBlockName("moonstone_ore");
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return WeatherWorks.moonstoneShard;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3)+1;
	}
	
}
