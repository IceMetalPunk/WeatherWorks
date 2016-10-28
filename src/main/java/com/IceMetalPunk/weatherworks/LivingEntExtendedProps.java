package com.IceMetalPunk.weatherworks;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class LivingEntExtendedProps implements IExtendedEntityProperties {

	int[] earthstoneCoords=null;
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		if (this.earthstoneCoords!=null) {
			compound.setIntArray("lastEarthstone", this.earthstoneCoords);
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		if (compound.hasKey("lastEarthstone")) {
			this.earthstoneCoords=compound.getIntArray("lastEarthstone");
		}
	}

	@Override
	public void init(Entity entity, World world) {
	}

}
