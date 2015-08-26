package com.trcx.ab.Items;

public class TestGun extends GunBase {

    public TestGun(){
        super("testGun");
        this.clipSize = 500;
        this.projectileDamage = 50F;
        this.projectileLength = 1F;
        this.projectileSpeed = 5F;
        this.maxClips = 20;
        this.reloadTime = 1;
        this.fireRate = 1;
        this.triggerStyle = FULL_AUTO;
        setDefaults();
    }
}
