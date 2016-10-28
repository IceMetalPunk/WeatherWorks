package com.IceMetalPunk.weatherworks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class WeatherEventListener {
	
	List<Class> mobTypes=new ArrayList<Class>();
	
    /* Event handlers */
	public WeatherEventListener(EventBus bus) {
		bus.register(this);
		this.mobTypes.add(EntityGhast.class);
		this.mobTypes.add(EntityMagmaCube.class);
		this.mobTypes.add(EntitySlime.class);
		this.mobTypes.add(EntityEnderman.class);
		this.mobTypes.add(EntitySpider.class);
		this.mobTypes.add(EntityCaveSpider.class);
		this.mobTypes.add(EntityBlaze.class);
		this.mobTypes.add(EntityPigZombie.class);
	}
	
	@SubscribeEvent(receiveCanceled=true)
	public void onConstructing(EntityConstructing event) {
		event.entity.registerExtendedProperties(WeatherWorks.MODID+":extendedProps", new LivingEntExtendedProps());
	}
	
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof IMob) {
			if (!this.mobTypes.contains(event.entity.getClass()) && !(event.entity instanceof EntityDragon) && !(event.entity instanceof EntityWither)) {
				((EntityLiving)event.entity).tasks.addTask(4, new EntityAIMoonstoneAttract((EntityLiving)event.entity, 1.0d));
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entity.worldObj.isRemote) { return; }
		
		if (this.mobTypes.contains(event.entity.getClass())) {
			World world=event.entity.worldObj;
			EntityLiving ent=(EntityLiving)event.entity;
			if (ent.getNavigator().noPath()) {
				int bX=(int)Math.floor(ent.posX);
				int bY=(int)Math.floor(ent.posY);
				int bZ=(int)Math.floor(ent.posZ);
				boolean foundMoonstone=false;
				int xx=0, yy=0, zz=0;
				breakLoop:
				for (xx=-10; xx<=10; ++xx) {
					for (yy=-10; yy<=10; ++yy) {
						for (zz=-10; zz<=10; ++zz) {
							Block block=world.getBlock(bX+xx, bY+yy, bZ+zz);
							if (block instanceof BlockMoonstone) {
								foundMoonstone=true;
								break breakLoop;
							}
						}
					}
				}
				
				if (foundMoonstone) {
					boolean pathed=ent.getNavigator().tryMoveToXYZ((double)(bX+xx), (double)(bY+yy), (double)(bZ+zz), 1.0D);
				}
			}
			else {
				ent.getNavigator().onUpdateNavigation();
				ent.getMoveHelper().onUpdateMoveHelper();
			}
		}
	}
	
	@SubscribeEvent
	public void handleDamage(LivingHurtEvent event) {
		Entity player=event.entityLiving;
		World world=player.worldObj;
		if (event.source.getDamageType().equals("fall")) {
			Block blockUnder=world.getBlock((int)Math.floor(player.posX), (int)Math.floor(player.posY)-1, (int)Math.floor(player.posZ));
			if (blockUnder instanceof BlockCloudstone) {
				event.setCanceled(true);
			}
		}
	}
	
	/* Replace lightning with electricity damage instead of fire damage */
    /*@SubscribeEvent(receiveCanceled=true)
    public void handleLightning(EntityStruckByLightningEvent event) {
    	System.out.println("Struck by lightning!");
    	event.setCanceled(true);
    	
    	Entity ent=event.entity;
    	if (ent instanceof EntityLivingBase) {
    	      ent.attackEntityFrom(WeatherWorks.thunderDamage, 5);
    	      int newVal=-20;
    	      Field fireField;
			try {
				fireField = Entity.class.getDeclaredField("fire");
				fireField.setAccessible(true);
				newVal=((Integer)fireField.get(ent)).intValue()+1;
				fireField.set(ent, newVal);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
    	      
    	        if (newVal == 0)
    	        {
    	            ent.setFire(8);
    	        }
    	}
    }*/
}
