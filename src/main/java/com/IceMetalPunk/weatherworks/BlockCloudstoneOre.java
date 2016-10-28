package com.IceMetalPunk.weatherworks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

public class BlockCloudstoneOre extends BlockOre {
	public BlockCloudstoneOre() {
		super();
		this.setBlockTextureName("weatherworks:cloudstone_ore");
		this.setBlockName("cloudstone_ore");
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return WeatherWorks.cloudstoneShard;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3)+1;
	}
	
}
