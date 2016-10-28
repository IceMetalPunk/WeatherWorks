/* TODO:
 * ==Moonstone...what does it do?
 */

package com.IceMetalPunk.weatherworks;

import com.IceMetalPunk.weatherworks.WeatherManipMessage.WeatherWorksManipLevelHandler;
import com.IceMetalPunk.weatherworks.WeatherManipMessage.WeatherWorksMessageHandler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = WeatherWorks.MODID, version = WeatherWorks.VERSION, name = WeatherWorks.MODNAME)
public class WeatherWorks
{
	public static final String MODNAME = "Weather Works";
    public static final String MODID = "weatherworks";
    public static final String VERSION = "1.1";
    
    /* Event listener registry */
    public static final WeatherEventListener eventListener=new WeatherEventListener(MinecraftForge.EVENT_BUS);
    public static final WeatherTickHandler tickHandler=new WeatherTickHandler(FMLCommonHandler.instance().bus());
    
    /* Block instantiations */
    public static final BlockWeatherDetector weatherDetector = new BlockWeatherDetector();
    public static final BlockWeatherManipulator weatherManipulator = new BlockWeatherManipulator();
    public static final BlockSunstone sunstone = new BlockSunstone();
    public static final BlockSunstoneOre sunstoneOre = new BlockSunstoneOre();
    public static final BlockWaterstone waterstone = new BlockWaterstone();
    public static final BlockWaterstoneOre waterstoneOre = new BlockWaterstoneOre();
    public static final BlockThunderstone thunderstone = new BlockThunderstone();
    public static final BlockThunderstoneOre thunderstoneOre = new BlockThunderstoneOre();
    public static final BlockCloudstone cloudstone = new BlockCloudstone();
    public static final BlockCloudstoneOre cloudstoneOre = new BlockCloudstoneOre();
    public static final BlockEarthstone earthstone = new BlockEarthstone();
    public static final BlockEarthstoneOre earthstoneOre = new BlockEarthstoneOre();
    public static final BlockMoonstone moonstone = new BlockMoonstone();
    public static final BlockMoonstoneOre moonstoneOre = new BlockMoonstoneOre();
    
    /* Item instantiations */
    public static final ItemSunstoneShard sunstoneShard = new ItemSunstoneShard();
    public static final ItemWaterstoneShard waterstoneShard = new ItemWaterstoneShard();
    public static final ItemThunderstoneShard thunderstoneShard = new ItemThunderstoneShard();
    public static final ItemCloudstoneShard cloudstoneShard = new ItemCloudstoneShard();
    public static final ItemEarthstoneShard earthstoneShard = new ItemEarthstoneShard();
    public static final ItemMoonstoneShard moonstoneShard = new ItemMoonstoneShard();
    
    /* DamageSource instantiations */
    public static final DamageSource thunderDamage = new DamageSource("electricity").setDamageBypassesArmor();
    
    /* Enchantment instantiations */
    public static int enchantmentID=255;
    public static final EnchantmentNaturesSong enchNaturesSong = new EnchantmentNaturesSong(enchantmentID--,5);
    public static final EnchantmentNaturesFury enchNaturesFury = new EnchantmentNaturesFury(enchantmentID--,5);
    public static final EnchantmentNaturesSoul enchNaturesSoul = new EnchantmentNaturesSoul(enchantmentID--,5);
    public static final EnchantmentNaturesInertia enchNaturesInertia = new EnchantmentNaturesInertia(enchantmentID--,5);
    
    /* World generator instantiations */
    public static final WeatherGenerator weatherGen = new WeatherGenerator();
    
    /* Mod instantiation */
    @Instance(value=WeatherWorks.MODID)
    public static WeatherWorks instance=new WeatherWorks();
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    	
    	/* Block registry */
    	GameRegistry.registerBlock(weatherDetector, MODID+"_weather_detector");
    	GameRegistry.registerBlock(weatherManipulator, MODID+"_weather_manipulator");
    	GameRegistry.registerBlock(sunstone, MODID+"_sunstone");
    	GameRegistry.registerBlock(sunstoneOre, MODID+"_sunstone_ore");
    	GameRegistry.registerBlock(waterstone, MODID+"_rainstone");
    	GameRegistry.registerBlock(waterstoneOre, MODID+"_rainstone_ore");
    	GameRegistry.registerBlock(thunderstone, MODID+"_thunderstone");
    	GameRegistry.registerBlock(thunderstoneOre, MODID+"_thunderstone_ore");
    	GameRegistry.registerBlock(cloudstone, MODID+"_cloudstone");
    	GameRegistry.registerBlock(cloudstoneOre, MODID+"_cloudstone_ore");
    	GameRegistry.registerBlock(earthstone, MODID+"_earthstone");
    	GameRegistry.registerBlock(earthstoneOre, MODID+"_earthstone_ore");
    	GameRegistry.registerBlock(moonstone, MODID+"_moonstone");
    	GameRegistry.registerBlock(moonstoneOre, MODID+"_moonstone_ore");
    	
    	/* Item registry */
    	GameRegistry.registerItem(sunstoneShard, MODID+"_sunstone_shard");
    	GameRegistry.registerItem(waterstoneShard, MODID+"_rainstone_shard");
    	GameRegistry.registerItem(thunderstoneShard, MODID+"_thunderstone_shard");
    	GameRegistry.registerItem(cloudstoneShard, MODID+"_cloudstone_shard");
    	GameRegistry.registerItem(earthstoneShard, MODID+"_earthstone_shard");
    	GameRegistry.registerItem(moonstoneShard, MODID+"_moonstone_shard");
    	
    	/* Tile entity registry */
    	GameRegistry.registerTileEntity(TileEntityWeatherManipulator.class, MODID+"_WeatherManipulator");
    	GameRegistry.registerTileEntity(TileEntityWeatherDetector.class, MODID+"_WeatherDetector");
    	GameRegistry.registerTileEntity(TileEntityWaterstone.class, MODID+"_Waterstone");
    	    	
    	/* GUI registry */
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new WeatherWorksGui());
    	
    	/* Packet handler registry */
    	int messageID=0;
    	WeatherworksPacketHandler.INSTANCE.registerMessage(WeatherWorksMessageHandler.class, WeatherManipMessage.class, messageID++, Side.SERVER);
    	WeatherworksPacketHandler.INSTANCE.registerMessage(WeatherWorksManipLevelHandler.class, WeatherManipMessage.class, messageID++, Side.CLIENT);
        
    	/* TESR registry */
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherManipulator.class, new WeatherManipulatorRenderer());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	/* Smelting recipe registry */
    	GameRegistry.addSmelting(sunstoneOre, new ItemStack(sunstoneShard, 1), 1);
    	GameRegistry.addSmelting(waterstoneOre, new ItemStack(waterstoneShard, 1), 1);
    	GameRegistry.addSmelting(thunderstoneOre, new ItemStack(thunderstoneShard, 1), 1);
    	GameRegistry.addSmelting(cloudstoneOre, new ItemStack(cloudstoneShard, 1), 1);
    	GameRegistry.addSmelting(earthstoneOre, new ItemStack(earthstoneShard, 1), 1);
    	GameRegistry.addSmelting(moonstoneOre, new ItemStack(moonstoneShard, 1), 1);
    	
    	/* Crafting recipe registry */ 
    	GameRegistry.addShapedRecipe(new ItemStack(weatherDetector, 1),
    			"   ",
    			"RBC",
    			"III",
    			'I', Items.iron_ingot, 'R', Items.redstone, 'B', Items.bucket, 'C', Items.comparator);
    	GameRegistry.addShapedRecipe(new ItemStack(weatherManipulator, 1),
    			"RCR",
    			"TNW",
    			"ISI",
    			'I', Items.iron_ingot, 
    			'R', Items.redstone,
    			'C', cloudstone,
    			'N', Items.nether_star,
    			'W', waterstone,
    			'S', sunstone,
    			'T', thunderstone);
    	GameRegistry.addShapedRecipe(new ItemStack(sunstone,1),
    			"SSS",
    			"SSS",
    			"SSS",
    			'S', sunstoneShard);
    	GameRegistry.addShapedRecipe(new ItemStack(waterstone,1),
    			"RRR",
    			"RRR",
    			"RRR",
    			'R', waterstoneShard);
    	GameRegistry.addShapedRecipe(new ItemStack(thunderstone,1),
    			"TTT",
    			"TTT",
    			"TTT",
    			'T', thunderstoneShard);
    	GameRegistry.addShapedRecipe(new ItemStack(cloudstone,1),
    			"CCC",
    			"CCC",
    			"CCC",
    			'C', cloudstoneShard);
    	GameRegistry.addShapedRecipe(new ItemStack(earthstone,1),
    			"EEE",
    			"EEE",
    			"EEE",
    			'E', earthstoneShard);
    	GameRegistry.addShapedRecipe(new ItemStack(moonstone,1),
    			"MMM",
    			"MMM",
    			"MMM",
    			'M', moonstoneShard);
    	
    	/* World generator registry */
        GameRegistry.registerWorldGenerator(weatherGen, 128);
    }
    
}
