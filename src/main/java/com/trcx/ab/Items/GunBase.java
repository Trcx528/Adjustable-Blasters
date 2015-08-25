package com.trcx.ab.Items;

import com.trcx.ab.gunData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import scala.Int;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GunBase extends Item{

    protected int projectialsPerShot = 1;
    protected int projectialRange = 240; // life time in ticks
    protected float projectialDiameter = 1;
    protected float projectialLength = 2;
    protected float projectialSpeed = 0.05F;
    protected float projectialDamage = 1;
    protected boolean isDestructive = false;
    protected int clipSize = 5;
    protected int maxClips = 1;
    protected short reloadTime = 4; // in ticks
    protected short fireRate = 3;   // in ticks
    protected int zoomLevels = 0;
    protected int energyPerRound = 5;
    protected int maxEnergy = 2000;
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
    }

    public GunBase setDefaults(Map<String, Integer> defaults) {
        this.gun = new gunData(defaults);
        return this;
    }

    protected boolean canShoot(ItemStack stack){
        gunData.data gun = this.gun.load(stack);
        return gun.getCooldown() <= 0 && gun.getRounds() > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        gunData.data gun = this.gun.load(stack);
        double retval = 0D;
        if (gun.getReloading()) {
            retval = (double)(this.reloadTime - gun.getCooldown()) / (double)this.reloadTime;
        } else {
            retval = (double) gun.getRounds() / (double) this.clipSize;
        }
        //System.out.println(retval);
        return 1D-retval;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (!player.worldObj.isRemote) {
            System.out.println("Using! " + count);
            gunData.data gundata = this.gun.load(stack);
            if (this.canShoot(stack)) {
                EntityArrow arrow = new EntityArrow(player.worldObj, player, 4f);
                arrow.setIsCritical(true);
                arrow.canBePickedUp = 2;
                player.worldObj.spawnEntityInWorld(arrow);
                gundata.setCooldown(this.fireRate);
                gundata.setRounds(gundata.getRounds() - 1);
            }
        }
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
        return 72000;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        System.out.println("onItemRightClick");
        player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int duration) {
        super.onPlayerStoppedUsing(stack, world, player, duration);
    }
}
