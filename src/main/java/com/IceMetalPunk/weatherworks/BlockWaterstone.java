package com.IceMetalPunk.weatherworks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWaterstone extends Block implements ITileEntityProvider {

	protected BlockWaterstone() {
		super(Material.glass);
		this.setHardness(0.5f);
		this.setBlockTextureName("weatherworks:rainstone");
		this.setBlockName("rainstone");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(Block.soundTypeGlass);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityWaterstone();
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int p) {
		return null;
	}
	
	@Override
	public boolean canSilkHarvest() {
		return true;
	}

}
