package com.IceMetalPunk.weatherworks;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class WeatherworksPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE=NetworkRegistry.INSTANCE.newSimpleChannel("weatherworks");
}
