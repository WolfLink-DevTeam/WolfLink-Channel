package org.wolflink.minecraft.plugin.channelbukkit.impl;

import org.wolflink.minecraft.interfaces.ILogger;

public class BeanConfig extends org.wolflink.minecraft.BeanConfig {
    public BeanConfig() {
        super(new Logger(), new PlatformAdapter());
    }
}
