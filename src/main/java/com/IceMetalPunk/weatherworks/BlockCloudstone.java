package com.IceMetalPunk.weatherworks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCloudstone extends Block {
	protected BlockCloudstone() {
		super(Material.cloth);
		this.setHardness(0.0f);
		this.setBlockName("cloudstone");
		this.setBlockTextureName("weatherworks:cloudstone");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(Block.soundTypeCloth);
	}
}
