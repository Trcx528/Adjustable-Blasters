package com.trcx.ab.Items;

public class Handgun extends GunBase{
    public Handgun(){
        super("Handgun");
        this.clipSize = 16;
        this.projectileDamage = 4;
        this.projectileLength = 1;
        this.projectileSpeed = 1F;
        this.maxClips = 4;
        this.reloadTime = 40;
        this.fireRate = 2;
        this.triggerStyle = SEMI_AUTO;
        setDefaults();
    }
}
