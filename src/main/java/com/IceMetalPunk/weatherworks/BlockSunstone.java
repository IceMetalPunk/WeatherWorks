package com.IceMetalPunk.weatherworks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSunstone extends Block {

	private IIcon[] icons=new IIcon[2];
	protected BlockSunstone() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setHardness(1.0f);
		this.setStepSound(Block.soundTypeStone);
		this.setBlockName("sunstone");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.isRemote) { return; }
		int thisMeta=world.getBlockMetadata(x, y, z);
		int thisPower=world.getStrongestIndirectPower(x, y, z);
		
		if (thisMeta!=thisPower) {
			world.setBlockMetadataWithNotify(x, y, z, thisPower, 3);
			//System.out.println(x+", "+y+", "+z+" meta = "+thisPower);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		onNeighborBlockChange(world, x, y, z, this);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		if (metadata==0) { return this.icons[0]; }
		else { return this.icons[1]; }
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.icons[0]=register.registerIcon("weatherworks:sunstone_off");
		this.icons[1]=register.registerIcon("weatherworks:sunstone_on");
	}

	/*
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySunstone();
	}*/

}
