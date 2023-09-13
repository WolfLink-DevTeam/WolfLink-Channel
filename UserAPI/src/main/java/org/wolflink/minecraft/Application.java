package org.wolflink.minecraft;

import lombok.NonNull;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.file.Configuration;

@Singleton
public class Application {

    /**
     * 需要自行实现 BeanConfig 抽象类
     * 相关接口都需要对接到具体客户端实现中
     */
    public Application(@NonNull BeanConfig beanConfig) {
        IOC.registerBeanConfig(beanConfig);
    }

    private boolean enabled = false;
    public void setEnabled(boolean value) {
        if(enabled == value) return;
        enabled = value;
        if(enabled) enable();
        else disable();
    }

    private void enable() {
        IOC.getBean(Configuration.class).load();
    }
    private void disable() {
    }

}
