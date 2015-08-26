package com.trcx.ab.Items;

public class RocketLauncher extends GunBase {

    public RocketLauncher() {
        super("RocketLauncher");
        this.clipSize = 2;
        this.projectileDamage = 20;
        this.projectileLength = 1;
        this.projectileSpeed = 0.5F;
        this.maxClips = 6;
        this.isDestructive = true;
        this.isExplosive = true;
        this.reloadTime = 80;
        this.fireRate = 20;
        this.triggerStyle = SEMI_AUTO;
        setDefaults();
    }

}
