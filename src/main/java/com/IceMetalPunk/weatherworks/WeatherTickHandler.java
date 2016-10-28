package com.IceMetalPunk.weatherworks;

import java.util.List;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class WeatherTickHandler {
	private byte dir=0;
	private float amt=1.0f;
	private int cooldown=0;
	private final double threshold=20.0d;
	private int healCooldown=0;
	
	public WeatherTickHandler(EventBus bus) {
		bus.register(this);
	}
	
	@SubscribeEvent
	public void onTick(ServerTickEvent event) {
		if (event.phase==Phase.START) {

			/* Enchantments */
			World world=MinecraftServer.getServer().getEntityWorld();
			int songAmount=0;
			songAmount=(world.getWorldInfo().isThundering()?2:(world.getWorldInfo().isRaining()?1:0));
			
			if (songAmount>0) {
				for (int p=0; p<world.playerEntities.size(); ++p) {
					EntityPlayer player=(EntityPlayer)world.playerEntities.get(p);
					int bX=(int)Math.floor(player.posX);
					int bY=(int)Math.floor(player.posY);
					int bZ=(int)Math.floor(player.posZ);
					if (!this.noShelter(world, bX, bY, bZ)) { continue; }
					
					// Nature's Song -- repairs items in inventory when outside in the rain
					ItemStack item=null;
					int enchLevel=0;
					for (int slot=0; slot<player.inventory.getSizeInventory(); ++slot) {
						item=player.inventory.getStackInSlot(slot);
						enchLevel=EnchantmentHelper.getEnchantmentLevel(WeatherWorks.enchNaturesSong.effectId, item);
						if (enchLevel>0) {
							int dur=item.getItemDamage();
							boolean vari=item.getHasSubtypes();
							int heal=Math.max(dur-enchLevel*songAmount, 0);
							if (!vari) {
								item.setItemDamage(heal);
							}
						}
					}
					
					// Nature's Fury -- gives you a proportional strength effect when held outside in the rain
					item=player.getHeldItem();
					enchLevel=EnchantmentHelper.getEnchantmentLevel(WeatherWorks.enchNaturesFury.effectId, item);
					if (enchLevel>0 && item.getItem() instanceof ItemSword) {
						player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20, enchLevel-2+songAmount));
					}
					
					// Nature's Soul -- Heals you when wearing armor with it in the rain
					boolean resetHealCooldown=false;
					if (this.healCooldown>0) { --this.healCooldown; }
					for (int slot=0; slot<player.inventory.armorInventory.length; ++slot) {
						item=player.getEquipmentInSlot(slot+1);
						enchLevel=EnchantmentHelper.getEnchantmentLevel(WeatherWorks.enchNaturesSoul.effectId, item);
						if (this.healCooldown==0 && enchLevel>0 && !item.getHasSubtypes() && item.getItem() instanceof ItemArmor) {
							float currentHealth=player.getHealth();
							resetHealCooldown=true;
							if (currentHealth>0 && currentHealth<player.getMaxHealth()) {
								float amountToHeal=Math.min(enchLevel-1+songAmount, player.getMaxHealth()-currentHealth);
								if (amountToHeal<1) { amountToHeal=0; }
								player.heal(amountToHeal);
								if (amountToHeal>0) {
									item.setItemDamage((int)Math.min(Math.ceil(item.getItemDamage()+amountToHeal*2), item.getMaxDamage()));
								}
							}
						}
					}
					if (resetHealCooldown) { this.healCooldown=20; }
					
				}
				
			}
			
			/* Wind */
			
			// Decrement the cooldown
			if (this.cooldown>0) { --this.cooldown; }
			
			// Only blow wind when cooldown<=40
			if (this.cooldown>this.threshold) { return; }
			
			// Only blow wind when it's raining
			if (world.getWorldInfo().isRaining() || world.getWorldInfo().isThundering()) {
				
				// Calculate wind speed on x and z axes
				float velX=(this.dir==0 || this.dir==4 || this.dir==6)?this.amt:((this.dir==1 || this.dir==5 || this.dir==7)?-this.amt:0);
				float velZ=(this.dir==2 || this.dir==4 || this.dir==7)?this.amt:((this.dir==3 || this.dir==5 || this.dir==6)?-this.amt:0);
				
				// Iterate through the list of entities
				List entities=world.getLoadedEntityList();
				for (int p=0; p<entities.size(); ++p) {
					Entity ent=((Entity)entities.get(p));
					int bX=(int)Math.floor(ent.posX);
					int bY=(int)Math.floor(ent.posY);
					int bZ=(int)Math.floor(ent.posZ);
					
					if (world.getBiomeGenForCoords(bX, bZ).getFloatRainfall()<=0.0f) { continue; }
					
					if (ent instanceof EntityPlayerMP) {
						world.playSoundAtEntity(ent, "weatherworks:weatherWind", 0.5f, 1.0f);
					}
					
					/* Don't push hanging entities (paintings, item frames, etc.) */
					if (ent instanceof EntityHanging) { continue; }
					
					/* Don't push entities under any shelter, even transparent */
					if (!this.noShelter(world, bX, bY, bZ)) { continue; }
					
					// Don't push minecarts on rails, but *do* push non-railed minecarts
					if (ent instanceof EntityMinecart) {
						Block blockAt=world.getBlock(bX, bY, bZ);
						if (blockAt instanceof BlockRailBase) {
							continue;
						}
					}
					
					// Reduce the amount of pushing for players with Nature's Inertia armor
					float playerVelX=velX, playerVelZ=velZ;
					if (ent instanceof EntityPlayerMP) {
						EntityPlayerMP player=(EntityPlayerMP)ent;
						for (int slot=0; slot<player.inventory.armorInventory.length; ++slot) {
							ItemStack item=player.getEquipmentInSlot(slot+1);
							int enchLevel=EnchantmentHelper.getEnchantmentLevel(WeatherWorks.enchNaturesInertia.effectId, item);
							if (enchLevel>0 && !item.getHasSubtypes() && item.getItem() instanceof ItemArmor) {
								playerVelX*=0.60;
								playerVelZ*=0.60;
							}
						}
						player.addVelocity((double)playerVelX, 0.0d, (double)playerVelZ);
						player.velocityChanged=true;
						continue;
					}
					
					// Add the velocity to each entity
					ent.addVelocity((double)velX, 0.0d, (double)velZ);
					ent.velocityChanged=true;
				}
			}
			
			// When cooldown=0, reset cooldown and choose a random direction and amount for next time
			if (this.cooldown==0) {
				this.cooldown=world.rand.nextInt(400-(int)this.threshold)+(int)this.threshold+1;
				this.dir=(byte)world.rand.nextInt(8);
				this.amt=world.rand.nextFloat()/4.0f;
			}
		}
	}
	
	public static boolean noShelter(World world, int x, int y, int z) {
		 
			if (!world.canBlockSeeTheSky(x, y+1, z)) { return false; }
			
			// See if anyone's under transparent shelter, and don't push them
			Block test;
			boolean breakIt=true;
			for (int yy=y; yy<world.getActualHeight(); ++yy) {
				test=world.getBlock(x, yy, z);
				if (test.getMaterial()!=Material.air) {
					breakIt=false;
					break;
				}
			}
			return breakIt;
	}
}
