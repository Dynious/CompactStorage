package com.workshop.compactchests;

import net.minecraftforge.common.MinecraftForge;

import com.workshop.compactchests.configuration.ConfigurationHandler;
import com.workshop.compactchests.event.CCHandler;
import com.workshop.compactchests.proxy.IProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Toby on 19/08/2014.
 */
public class CompactChests
{
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent fmlPreInitializationEvent)
    {
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ConfigurationHandler.init(fmlPreInitializationEvent.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void initialization(FMLInitializationEvent fmlInitializationEvent)
    {

    }

    @Mod.EventHandler
    public void postInitialization(FMLPostInitializationEvent fmlPostInitializationEvent)
    {
        proxy.registerRenderers();

        MinecraftForge.EVENT_BUS.register(new CCHandler());

        //NetworkRegistry.INSTANCE.registerGuiHandler(CompactStorage.legacy_instance, new GuiHandler());
    }
}
