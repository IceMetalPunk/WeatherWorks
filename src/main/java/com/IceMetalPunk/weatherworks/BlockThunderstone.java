package com.IceMetalPunk.weatherworks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockThunderstone extends Block {

	private IIcon[] icons=new IIcon[2];
	
	protected BlockThunderstone() {
		super(Material.rock);
		this.setBlockName("thunderstone");
		this.setHardness(3.0f);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	public boolean canConnectRedstone(World world, int x, int y, int z, int size) {
		return true;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		if (world.isRemote) { return; }
		if (world.getBlockMetadata(x, y, z)>0) {
			ent.attackEntityFrom(WeatherWorks.thunderDamage, 3.0f);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		onNeighborBlockChange(world, x, y, z, this);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.isRemote) { return; }
		int thisMeta=world.getBlockMetadata(x, y, z);
		int power=world.getStrongestIndirectPower(x, y, z);
		if (power!=thisMeta) {
			world.setBlockMetadataWithNotify(x, y, z, power, 3);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return (meta>0)?icons[1]:icons[0];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.icons[0]=register.registerIcon("weatherworks:thunderstone_off");
		this.icons[1]=register.registerIcon("weatherworks:thunderstone_on");
	}

	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float f = 0.01f;//625F;
        return AxisAlignedBB.getBoundingBox(x + f, y, z + f, x + 1 - f, y + 1 - f, z + 1 - f);
    }
	
	
	@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        float f = 0.01f;//625F;
        return AxisAlignedBB.getBoundingBox(x + f, y, z + f, x + 1 - f, y + 1 - f, z + 1 - f);
    }
}
