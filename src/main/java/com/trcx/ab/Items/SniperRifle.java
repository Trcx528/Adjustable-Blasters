package com.trcx.ab.Items;

public class SniperRifle extends GunBase {

    public SniperRifle() {
        super("SniperRifle");
        this.clipSize = 8;
        this.projectileDamage = 16;
        this.projectileLength = 3;
        this.projectileSpeed = 6F;
        this.maxClips = 6;
        this.reloadTime = 30;
        this.fireRate = 10;
        this.triggerStyle = SEMI_AUTO;
        setDefaults();
    }

}
