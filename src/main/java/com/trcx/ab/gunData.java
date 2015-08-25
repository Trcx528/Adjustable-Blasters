package com.trcx.ab;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class gunData {

    private ItemStack stack;

    public gunData(ItemStack stack){
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        this.stack = stack;
    }

    public int getCooldown(){

    }
}
