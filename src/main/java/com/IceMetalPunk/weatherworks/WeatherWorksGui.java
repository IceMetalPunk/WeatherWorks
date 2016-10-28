package com.IceMetalPunk.weatherworks;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class WeatherWorksGui implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID==0) {
			return new ContainerWeatherManipulator();
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID==0) {
			return new GuiWeatherManipulator(world.getTileEntity(x, y, z));
		}
		return null;
	}

}
