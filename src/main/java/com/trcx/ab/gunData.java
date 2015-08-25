package com.trcx.ab;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class gunData {
    public static final String nbtCooldown = "cooldown";
    public static final String nbtRounds = "rounds";
    public static final String nbtClips = "clips";
    public static final String nbtEnergy = "energy";
    public static final String nbtReloading = "reloading";
    public Map<String, Integer> defaults;

    public gunData(Map <String, Integer> defaultValues){
        this.defaults = defaultValues;
    }

    public data load(ItemStack stack){
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        return new data(stack, this.defaults);
    }

    public class data{
        private ItemStack stack;
        private Map<String, Integer> defaults;

        public data (ItemStack stack, Map<String,Integer> defaults) {
            this.stack = stack;
            this.defaults = defaults;
        }

        private int getInt(String key){
            if (this.stack.stackTagCompound.hasKey(key)){
                return this.stack.stackTagCompound.getInteger(key);
            } else if (this.defaults.containsKey(key)){
                return this.defaults.get(key);
            }
            return -1;
        }

        private void setInt(String key, int value){
            this.stack.stackTagCompound.setInteger(key,value);
        }

        public int getCooldown(){
            return getInt(gunData.nbtCooldown);
        }

        public void setCooldown(int value){
            setInt(gunData.nbtCooldown, value);
        }

        public int getRounds(){
            return getInt(gunData.nbtRounds);
        }

        public void setRounds(int value){
            setInt(gunData.nbtRounds, value);
        }

        public int getClips(){
            return getInt(gunData.nbtClips);
        }

        public void setClips(int value){
            setInt(gunData.nbtClips, value);
        }

        public int getEnergy(){
            return getInt(gunData.nbtEnergy);
        }

        public void setEnergy(int value){
            setInt(gunData.nbtEnergy, value);
        }

        public boolean getReloading(){
            return getInt(gunData.nbtReloading) == 1;
        }

        public void setReloading(boolean value){
            setInt(gunData.nbtReloading, value?1:0);
        }
    }
}
