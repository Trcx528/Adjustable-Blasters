package com.trcx.ab.Items;

public class Shotgun extends GunBase {

    public Shotgun() {
        super("Shotgun");
        this.clipSize = 8;
        this.projectileDamage = 3;
        this.projectileLength = 1;
        this.projectileSpeed = 1F;
        this.projectileAccuracy = 3;
        this.maxClips = 8;
        this.reloadTime = 40;
        this.fireRate = 20;
        this.triggerStyle = SEMI_AUTO;
        this.projectilesPerRound = 8;
        setDefaults();
    }
}
