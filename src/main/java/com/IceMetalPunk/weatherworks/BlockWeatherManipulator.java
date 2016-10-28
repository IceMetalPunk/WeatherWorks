package com.IceMetalPunk.weatherworks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWeatherManipulator extends BlockContainer {

	private String[] iconPaths={"weather_manip_bottom", "weather_manip_top", "weather_manip_lightning", "weather_manip_sun", "weather_manip_rain", "weather_manip_cloud"};
	private IIcon[] icons=new IIcon[this.iconPaths.length];
	protected BlockWeatherManipulator() {
		super(Material.iron);
		this.setBlockName("weather_manipulator").setHardness(1.0f).setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p2) {
		return new TileEntityWeatherManipulator();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float px, float py, float pz) {
		if (world.isRemote) { return true; }
		player.openGui(WeatherWorks.instance, 0, world, x, y, z);
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.isRemote) { return; }
		TileEntityWeatherManipulator tileEntity=(TileEntityWeatherManipulator)world.getTileEntity(x, y, z);
		tileEntity.updatePower();
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		return this.icons[side];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		for (int p=0; p<this.iconPaths.length; ++p) {
			this.icons[p]=register.registerIcon("weatherworks:"+this.iconPaths[p]);
		}
	}

}
