package com.trcx.ab;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ProjectileBullet extends EntityThrowable {

    protected int maxLifetime; // life time in ticks
    protected float diameter;
    protected float length;
    protected float speed;
    protected float damage;
    protected float accuracy;
    protected boolean destructive;
    protected boolean explosive;

    public ProjectileBullet(World world, int damage, int lifetime, int speed, float length, float diameter, float accuracy, boolean destructive, boolean explosive){
        super(world);
        this.damage = damage;
        this.maxLifetime = lifetime;
        this.speed = speed;
        this.length = length;
        this.diameter = diameter;
        this.accuracy = accuracy;
        this.destructive = destructive;
        this.explosive = explosive;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void onImpact(MovingObjectPosition mop) {

    }
}
