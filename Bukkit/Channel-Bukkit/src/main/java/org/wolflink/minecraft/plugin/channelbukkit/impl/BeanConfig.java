package org.wolflink.minecraft.plugin.channelbukkit.impl;

import org.bukkit.plugin.java.JavaPlugin;

public class BeanConfig extends org.wolflink.minecraft.BeanConfig {
    public BeanConfig(JavaPlugin plugin) {
        super(new Logger(), new PlatformAdapter(plugin));
    }
}
