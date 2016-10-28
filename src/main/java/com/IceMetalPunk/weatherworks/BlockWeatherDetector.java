package com.IceMetalPunk.weatherworks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWeatherDetector extends BlockContainer {
	private byte outputLevel=0;
	private IIcon[] icons=new IIcon[2];
	
	public BlockWeatherDetector() {
		super(Material.circuits);
		this.setCreativeTab(CreativeTabs.tabRedstone).setHardness(2.0F);
		this.setStepSound(Block.soundTypeMetal).setBlockName("weatherDetector");
		this.setBlockTextureName("weatherworks:weather_detector").setHarvestLevel(null, 0);
		this.opaque=false;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean canConnectRedstone(World world, int x, int y, int z, int size) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityWeatherDetector();
	}
	
	@Override
	public int tickRate(World world) {
		return 2;
	}
	
	@Override
	public boolean isToolEffective(String type, int metadata) {
		return true;
	}
	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}
	
	public void updateOutput(byte amt) {
		this.outputLevel=amt;
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int face) {
		return this.outputLevel;
	}

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return side == 1 ? this.icons[0] : this.icons[1];
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons[0] = register.registerIcon(this.getTextureName() + "_top");
        this.icons[1] = register.registerIcon(this.getTextureName() + "_side");
    }
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortuneLevel) {
		Item item=Item.getItemFromBlock(WeatherWorks.weatherDetector);
		System.out.println("Item dropped: "+item);
		return item;
	}
}
