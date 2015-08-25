package com.trcx.ab.Items;

public class Rifle extends GunBase {

    public Rifle (){
        super("Rifle");
        this.clipSize = 50;
        this.projectialDamage = 4;
        this.projectialLength = 1;
        this.maxClips = 4;
        this.reloadTime = 20;
        this.fireRate = 4;
    }
}
