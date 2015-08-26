package com.trcx.ab;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class ProjectileBullet extends EntityThrowable {

    protected int maxLifetime; // life time in ticks
    protected float diameter;
    protected float length;
    protected float speed;
    protected float damage;
    protected boolean destructive;
    protected boolean explosive;

    private int lifetime = 0;

    private static final String nbtLifetime = "lifetime";

    @Override
    public void onUpdate() {
        this.readEntityFromNBT(getEntityData());
        this.lifetime ++;
        if (this.lifetime >= this.maxLifetime) {
            this.setDead();
            System.out.println("Killed bullet");
        }
        this.writeEntityToNBT(getEntityData());
        super.onUpdate();
    }

    public ProjectileBullet(World world, EntityLivingBase player, float damage, int lifetime, float speed, float length, float diameter, int shake, boolean destructive, boolean explosive){
        super(world,player);
        this.damage = damage;
        this.maxLifetime = lifetime;
        this.speed = speed;
        this.length = length;
        this.diameter = diameter;
        this.throwableShake = shake;
        this.destructive = destructive;
        this.explosive = explosive;

        System.out.println("Spawned");
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, speed, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(nbtLifetime, this.lifetime);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.lifetime = tag.hasKey(nbtLifetime) ? tag.getInteger(nbtLifetime) : 0;
    }

    @Override
    protected float getGravityVelocity() {
        return 0f;
    }

    @Override
    protected void onImpact(MovingObjectPosition mop) {
        if(this.ticksExisted <= 1) {
            return;
        }
        if(!worldObj.isRemote) {
            if (this.explosive) {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.damage/3, true);
            }
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (this.destructive) {
                    EntityPlayerMP player = (EntityPlayerMP)this.getThrower();

                    Block block = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
                    int metadata = worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);

                    BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(worldObj, player.theItemInWorldManager.getGameType(), player, mop.blockX, mop.blockY, mop.blockZ);
                    if(!event.isCanceled()) {
                            System.out.println("Breaking block " + block.toString() + " " + block.getBlockHardness(worldObj, mop.blockX, mop.blockY, mop.blockZ));
                            if(worldObj.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ)) {
                                block.dropBlockAsItem(worldObj,(int) this.posX, (int) this.posY, (int) this.posZ, metadata, 0);
                            }
                        }
                }

            } else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                if (mop.entityHit != null) {
                    if (mop.entityHit instanceof EntityLiving) {
                        DamageSource ds = new DamageSource("blaster");
                        mop.entityHit.attackEntityFrom(ds,this.damage);
                    }
                }
            }
            this.setDead();
        }
    }
}
