package com.trcx.ab.Items;

import com.trcx.ab.ProjectileBullet;
import com.trcx.ab.gunData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GunBase extends Item{

    public static final int FULL_AUTO = 1;
    public static final int SEMI_AUTO = 2;

    protected int projectilesPerRound = 1;
    protected int projectileRange = 240; // life time in ticks
    protected float projectileDiameter = 1;
    protected float projectileLength = 2;
    protected float projectileSpeed = 0.5F;
    protected float projectileDamage = 1;
    protected int projectileAccuracy = 1;
    protected boolean isDestructive = false;
    protected boolean isExplosive = false;
    protected int clipSize = 5;
    protected int maxClips = 1;
    protected short reloadTime = 4; // in ticks
    protected short fireRate = 3;   // in ticks
    protected int zoomLevels = 0;
    protected int energyPerRound = 5;
    protected int maxEnergy = 2000;
    protected int triggerStyle = SEMI_AUTO;
    protected gunData gun;

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List data, boolean bool) {
        super.addInformation(stack, player, data, bool);
        gunData.data gun = this.gun.load(stack);
        data.add(" - Rounds " + gun.getRounds());
        data.add(" - Clips " + gun.getClips());
        data.add(" - Cooldown " + gun.getCooldown());
        data.add(" - Energy " + gun.getEnergy());
        data.add(" - Reloading " + gun.getReloading());
    }

    public GunBase(String name){
        setCreativeTab(CreativeTabs.tabCombat);
        setUnlocalizedName(name);
        setTextureName("AB:" + name);
        setMaxStackSize(1);

        Map<String, Integer> defaults = new HashMap<String, Integer>();
        this.gun = new gunData(defaults);
    }

    public GunBase setDefaults(Map<String, Integer> defaults) {
        this.gun = new gunData(defaults);
        return this;
    }

    public GunBase setDefaults() {
        Map<String, Integer> defaults = new HashMap<String, Integer>();
        defaults.put(gunData.nbtRounds, this.clipSize);
        defaults.put(gunData.nbtClips, this.maxClips);
        defaults.put(gunData.nbtEnergy, this.maxEnergy);
        defaults.put(gunData.nbtClips, this.maxClips);
        this.gun = new gunData(defaults);
        return this;
    }

    protected boolean canShoot(ItemStack stack){
        gunData.data gun = this.gun.load(stack);
        return gun.getCooldown() <= 0 && gun.getRounds() > 0;
    }

    protected void spawnProjectiles(gunData.data gun, EntityPlayer player){
        for (int i=0; i<this.projectilesPerRound; i++){
            if (!player.worldObj.isRemote) {
                ProjectileBullet bullet = new ProjectileBullet(player.worldObj, player, this.projectileDamage, this.projectileRange, this.projectileSpeed, this.projectileLength, this.projectileDiameter, this.projectileAccuracy, this.isDestructive, this.isExplosive);
                player.worldObj.spawnEntityInWorld(bullet);
            } else {
                player.worldObj.spawnEntityInWorld(new EntityArrow(player.worldObj,player,this.projectileSpeed));
            }
        }
    }

    protected void shoot(ItemStack stack, EntityPlayer player){
        gunData.data gundata = this.gun.load(stack);
        if (this.canShoot(stack)) {
            spawnProjectiles(gundata, player);
            gundata.setCooldown(this.fireRate);
            gundata.setRounds(gundata.getRounds() - 1);
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        gunData.data gun = this.gun.load(stack);
        double retval;
        if (gun.getReloading()) {
            retval = (double)(this.reloadTime - gun.getCooldown()) / (double)this.reloadTime;
        } else {
            retval = (double) gun.getRounds() / (double) this.clipSize;
        }
        return 1D-retval;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (this.triggerStyle == FULL_AUTO || (this.triggerStyle == SEMI_AUTO && count == this.getMaxItemUseDuration(stack)))
            shoot(stack, player);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int param4, boolean param5) {
        super.onUpdate(stack, world, player, param4, param5);
        gunData.data gundata = this.gun.load(stack);
        if (gundata.getCooldown() > 0) {
            if ((gundata.getReloading() && gundata.getClips() > 1) || !gundata.getReloading() ) {
                gundata.setCooldown(gundata.getCooldown() - 1);
            }
        } else if (gundata.getRounds() <= 0 && ! gundata.getReloading()) {
            gundata.setReloading(true);
            gundata.setCooldown(this.reloadTime);
        } else if (gundata.getReloading() && gundata.getClips() > 0) { // and cooldown is 0
            gundata.setClips(gundata.getClips() -1);
            gundata.setRounds(this.clipSize);
            gundata.setReloading(false);
            System.out.println("Reloaded...");
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (this.triggerStyle == SEMI_AUTO) {
            return 72000;
        } else {
            return 4;
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        System.out.println("onItemUse");
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}
