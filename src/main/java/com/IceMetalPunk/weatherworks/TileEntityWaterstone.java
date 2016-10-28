package com.IceMetalPunk.weatherworks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityWaterstone extends TileEntity {
	private int cooldown=0;
	
	@Override
	public void updateEntity() {
		if (this.worldObj.isRemote) { return; }
		
		if (this.cooldown>0) { --this.cooldown; return; }
		
		// Crops and fire blocks
		for (int x=-2; x<=2; ++x) {
			for (int z=-2; z<=2; ++z) {
				for (int y=0; y>=-3; --y) {
					if (this.yCoord+y<0 || (y==0 && x==0 && z==0)) { continue; }
					Block block=this.worldObj.getBlock(this.xCoord+x, this.yCoord+y, this.zCoord+z);
					
					// Fire blocks
					if (block instanceof BlockFire) {
						this.worldObj.setBlockToAir(this.xCoord+x, this.yCoord+y, this.zCoord+z);
					}
					
					// Crops
					else if (block instanceof BlockFarmland) {
						this.worldObj.setBlockMetadataWithNotify(this.xCoord+x, this.yCoord+y, this.zCoord+z, 7, 2);
					}
					
					if (block.getTickRandomly()) {
						block.updateTick(this.worldObj, this.xCoord+x, this.yCoord+y, this.zCoord+z, this.worldObj.rand);
					}
				}
			}
		}
		
		// Entities on fire
		List ents=this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord-2, this.yCoord, this.zCoord-2, this.xCoord+2, this.yCoord+3, this.zCoord+2));
		for (int p=0; p<ents.size(); ++p) {
			if (ents.get(p) instanceof Entity) {
				((Entity)ents.get(p)).extinguish();
			}
		}
		
		this.cooldown=80;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Cooldown", this.cooldown);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.cooldown=nbt.getInteger("Cooldown");
	}
}
