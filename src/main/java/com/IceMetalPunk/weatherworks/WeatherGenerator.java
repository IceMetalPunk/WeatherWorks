package com.IceMetalPunk.weatherworks;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WeatherGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int type=random.nextInt(6);
		int rX=0,rY=0,rZ=0;
		for (int tries=0; tries<10; ++tries) {
			if (type==0) {
				rX=random.nextInt(16);
				rY=random.nextInt(16);
				rZ=random.nextInt(16);
				WorldGenMinable veinGen=new WorldGenMinable(WeatherWorks.sunstoneOre, 0, 7, Blocks.stone);
				veinGen.generate(world, random, chunkX*16+rX, rY, chunkZ*16+rZ);
			}
			else if (type==1) {
				rX=random.nextInt(16);
				rY=random.nextInt(16);
				rZ=random.nextInt(16);
				WorldGenMinable veinGen=new WorldGenMinable(WeatherWorks.waterstoneOre, 0, 7, Blocks.stone);
				veinGen.generate(world, random, chunkX*16+rX, rY, chunkZ*16+rZ);
			}
			else if (type==2) {		
				rX=random.nextInt(16);
				rY=random.nextInt(16);
				rZ=random.nextInt(16);
				WorldGenMinable veinGen=new WorldGenMinable(WeatherWorks.thunderstoneOre, 0, 7, Blocks.stone);
				veinGen.generate(world, random, chunkX*16+rX, rY, chunkZ*16+rZ);
			}
			else if (type==3) {
				rX=random.nextInt(16);
				rY=random.nextInt(16);
				rZ=random.nextInt(16);
				WorldGenMinable veinGen=new WorldGenMinable(WeatherWorks.cloudstoneOre, 0, 7, Blocks.stone);
				veinGen.generate(world, random, chunkX*16+rX, rY, chunkZ*16+rZ);
			}
			else if (type==4) {
				rX=random.nextInt(16);
				rY=random.nextInt(16);
				rZ=random.nextInt(16);	
				WorldGenMinable veinGen=new WorldGenMinable(WeatherWorks.earthstoneOre, 0, 7, Blocks.stone);
				veinGen.generate(world, random, chunkX*16+rX, rY, chunkZ*16+rZ);
			}
			else {
				rX=random.nextInt(16);
				rY=random.nextInt(16);
				rZ=random.nextInt(16);
				WorldGenMinable veinGen=new WorldGenMinable(WeatherWorks.moonstoneOre, 0, 7, Blocks.stone);
				veinGen.generate(world, random, chunkX*16+rX, rY, chunkZ*16+rZ);
			}
		}
	}
}
