package com.trcx.ab.Items;

import com.trcx.ab.gunData;

import java.util.HashMap;
import java.util.Map;

public class Rifle extends GunBase {

    public Rifle (){
        super("Rifle");

        this.clipSize = 999999999; //TODO Undo set
        this.projectialDamage = 4;
        this.projectialLength = 1;
        this.maxClips = 4;
        this.reloadTime = 40;
        this.fireRate = 1;

        Map<String, Integer> defaults = new HashMap<String, Integer>();
        defaults.put(gunData.nbtRounds, this.clipSize);
        defaults.put(gunData.nbtEnergy, 10000);
        defaults.put(gunData.nbtClips, 4);
        this.setDefaults(defaults);

    }
}
