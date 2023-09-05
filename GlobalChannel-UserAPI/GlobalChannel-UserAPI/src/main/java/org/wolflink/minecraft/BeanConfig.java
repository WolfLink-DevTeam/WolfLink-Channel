package org.wolflink.minecraft;

import lombok.AllArgsConstructor;
import org.wolflink.common.ioc.BeanProvider;
import org.wolflink.minecraft.interfaces.ILogger;
import org.wolflink.minecraft.interfaces.PlatformAdapter;

@AllArgsConstructor
public class BeanConfig {
    private final ILogger iLogger;
    private final PlatformAdapter platformAdapter;
    @BeanProvider
    public ILogger iLogger() {
        return iLogger;
    }
    @BeanProvider
    public PlatformAdapter platformAdapter() {
        return platformAdapter;
    }
}
