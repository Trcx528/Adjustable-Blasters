package com.trcx.ab.Items;


public class Rifle extends GunBase {

    public Rifle (){
        super("Rifle");
        this.clipSize = 50;
        this.projectileDamage = 4;
        this.projectileLength = 1;
        this.projectileSpeed = 1F;
        this.maxClips = 4;
        this.reloadTime = 40;
        this.fireRate = 3;
        this.triggerStyle = FULL_AUTO;
        setDefaults();
    }
}
