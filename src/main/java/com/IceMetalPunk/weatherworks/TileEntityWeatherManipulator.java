package com.IceMetalPunk.weatherworks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityWeatherManipulator extends TileEntity {
	private byte state=0;
	private boolean wasPowered=false;
	private byte activeLevel=0;
	private int ID=(new Random()).nextInt();
	public final byte beamTime=40;
	
	public void updateState(byte typeSet) {
		this.state=typeSet;
	}
	
	public byte getActiveLevel() {
		return this.activeLevel;
	}
	
	public void setActiveLevel(byte lvl) {
		this.activeLevel=lvl;
	}
	
	@Override
	public void updateEntity() {
		if (this.activeLevel>0) {
			--this.activeLevel;
			if (this.activeLevel==this.beamTime/2) {
				if (this.state==0) {
					this.worldObj.getWorldInfo().setRaining(false);
					this.worldObj.getWorldInfo().setThundering(false);
				}
				else if (this.state==1) {
					this.worldObj.getWorldInfo().setThundering(false);
					this.worldObj.getWorldInfo().setRaining(true);
				}
				else {
					this.worldObj.getWorldInfo().setRaining(true);
					this.worldObj.getWorldInfo().setThundering(true);
				}
			}
		}
	}
	
	public byte getState() {
		return state;
	}
	public void updatePower() {
		if (this.worldObj.isRemote) { return; }
		boolean isPowered=this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
		if (isPowered && !this.wasPowered) {
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "weatherworks:weatherManipulation", 0.5f, 1.0f);
			this.activeLevel=beamTime;
			WeatherworksPacketHandler.INSTANCE.sendToAll(new WeatherManipMessage((byte)1, this.xCoord, this.yCoord, this.zCoord, this.activeLevel));
		}
		this.wasPowered=isPowered;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.state=nbt.getByte("State");
		this.wasPowered=nbt.getBoolean("Powered");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("State", this.state);
		nbt.setBoolean("Powered", this.wasPowered);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() {
		return 500*500;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		// if your render should always be performed regardless of where the player is looking, use infinite
		AxisAlignedBB infiniteExample = INFINITE_EXTENT_AABB;

		// our gem will stay above the block, up to 1 block higher, so our bounding box is from [x,y,z] to  [x+1, y+2, z+1]
		//double b=1020-4*this.yCoord;
		//double x=(this.activeLevel/this.beamTime);
		//double height=b*x-b*x*x+this.yCoord;
		//AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord+1, height, this.zCoord);
		AxisAlignedBB aabb=AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord+1, 255, this.zCoord+1);
		return aabb;
	}
}
