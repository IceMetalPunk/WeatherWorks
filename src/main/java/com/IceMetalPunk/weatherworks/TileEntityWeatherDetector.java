package com.IceMetalPunk.weatherworks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWeatherDetector extends TileEntity {

	private byte outputLevel=0;
	
	public void updateEntity() {
		if (this.worldObj==null || this.worldObj.isRemote) { return; }
		byte temp=this.outputLevel;
		if (!this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord+1, this.zCoord)) { this.outputLevel=0; }
		else if (this.worldObj.isThundering()) { this.outputLevel=15; }
		else if (this.worldObj.isRaining()) { this.outputLevel=7; }
		else { this.outputLevel=0; }
		if (this.outputLevel!=temp) {
			BlockWeatherDetector myBlock=(BlockWeatherDetector)(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord));
			myBlock.updateOutput(this.outputLevel);
		
			this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, myBlock, myBlock.tickRate(this.worldObj));
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, myBlock);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("outputLevel", this.outputLevel);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.outputLevel=nbt.getByte("outputLevel");
	}
	
}
