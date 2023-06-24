package org.vanillacommunity.solon;

import org.noear.solon.SolonApp;

/**
 * 简单的 IOC 映射容器
 */
public class IOC {
    public static <T> T get(Class<?> clazz) {
        if (clazz.equals(SolonApp.class)) return (T) App.getSolonApp();
        return (T) App.getSolonApp().context().getBean(clazz);
    }
}
