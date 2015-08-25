package com.trcx.ab;

import com.trcx.ab.Items.Rifle;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.IOException;

@Mod(modid = AB.MODID, version = AB.VERSION, name=AB.NAME)
public class Main
{
    public Main(){

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        AB.Items.Rifle = new Rifle();

        GameRegistry.registerItem(AB.Items.Rifle, AB.Items.ID.Rifle);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event){

    }
}