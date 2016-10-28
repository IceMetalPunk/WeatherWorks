package com.IceMetalPunk.weatherworks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockEarthstone extends Block {

	protected BlockEarthstone() {
		super(Material.ground);
		this.setHardness(0.5f);
		this.setBlockName("earthstone");
		this.setBlockTextureName("weatherworks:earthstone");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(Block.soundTypeGrass);
		this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.isRemote) { return; }
		
		double dx=x, dy=y, dz=z;
		List entities=world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(dx, dy, dz, dx+1, dy+1, dz+1).expand(3.0d, 3.0d, 3.0d));
		for (Object entry : entities) {
			EntityLivingBase ent=(EntityLivingBase)entry;
			LivingEntExtendedProps props=(LivingEntExtendedProps)ent.getExtendedProperties(WeatherWorks.MODID+":extendedProps");
			if (props.earthstoneCoords!=null && props.earthstoneCoords[0]==x && props.earthstoneCoords[1]==y && props.earthstoneCoords[2]==z) { continue; }
			
			if (ent instanceof EntityVillager) {
				double px=ent.posX, py=ent.posY, pz=ent.posZ;
				ent.setDead();
				EntityZombie replacement=new EntityZombie(world);
				replacement.posX=px;
				replacement.posY=py;
				replacement.posZ=pz;
				replacement.setVillager(true);
				replacement.setPositionAndUpdate(px, py, pz);
				world.spawnEntityInWorld(replacement);
				ent=replacement;
			}
			else if (ent instanceof EntityWitch) {
				double px=ent.posX, py=ent.posY, pz=ent.posZ;
				ent.setDead();
				EntityZombie replacement=new EntityZombie(world);
				replacement.posX=px;
				replacement.posY=py;
				replacement.posZ=pz;
				replacement.setVillager(false);
				replacement.setPositionAndUpdate(px, py, pz);
				world.spawnEntityInWorld(replacement);
				ent=replacement;
			}
			else if (ent instanceof EntityZombie) {
				double px=ent.posX, py=ent.posY, pz=ent.posZ;
				ent.setDead();
				EntitySkeleton replacement=new EntitySkeleton(world);
				replacement.posX=px;
				replacement.posY=py;
				replacement.posZ=pz;
				replacement.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
				replacement.setPositionAndUpdate(px, py, pz);
				world.spawnEntityInWorld(replacement);
				ent=replacement;
			}
			else if (ent instanceof EntityHorse) {
				if (((EntityHorse) ent).getHorseType()<3) {
					((EntityHorse) ent).setHorseType(3);
				}
				else if (((EntityHorse) ent).getHorseType()==3) {
					((EntityHorse) ent).setHorseType(4);
				}
			}
			else if (ent instanceof EntitySlime) {
				 if (((EntitySlime)ent).getSlimeSize()>1) {
					 ent.setHealth(0.0f);
					ent.setDead();
					this.makeParticles(world, ent.posX, ent.posY, ent.posZ);
					ent=null;
				 }
			}
			else { continue; }
			
			if (ent==null) { continue; }
			
			this.makeParticles(world, ent.posX, ent.posY, ent.posZ);
			props=(LivingEntExtendedProps)ent.getExtendedProperties(WeatherWorks.MODID+":extendedProps");
			if (props.earthstoneCoords==null) {
				props.earthstoneCoords=new int[3];
			}
			props.earthstoneCoords[0]=x;
			props.earthstoneCoords[1]=y;
			props.earthstoneCoords[2]=z;
			
		}
	}
	
	private void makeParticles(World world, double x, double y, double z) {
		for (int partX=-1; partX<=1; ++partX) {
			for (int partZ=-1; partZ<=1; ++partZ) {
				for (int n=0; n<5; ++n) {
					world.spawnParticle("magicCrit", x+partX, y, z+partZ, 0.0d, 5.0d, 0.0d);
				}
			}
		}
	}
	

}
