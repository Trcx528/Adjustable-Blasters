package com.trcx.ab;

import com.trcx.ab.Items.*;
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
        AB.Items.Shotgun = new Shotgun();
        AB.Items.Handgun = new Handgun();
        AB.Items.RocketLauncher = new RocketLauncher();
        AB.Items.SniperRifle = new SniperRifle();
        AB.Items.TestGun = new TestGun();

        GameRegistry.registerItem(AB.Items.Rifle, AB.Items.ID.Rifle);
        GameRegistry.registerItem(AB.Items.Shotgun, AB.Items.ID.Shotgun);
        GameRegistry.registerItem(AB.Items.Handgun, AB.Items.ID.Handgun);
        GameRegistry.registerItem(AB.Items.RocketLauncher, AB.Items.ID.RocketLauncher);
        GameRegistry.registerItem(AB.Items.SniperRifle, AB.Items.ID.SniperRifle);
        GameRegistry.registerItem(AB.Items.TestGun, AB.Items.ID.TestGun);


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event){

    }
}