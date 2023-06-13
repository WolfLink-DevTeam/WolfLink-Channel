package org.vanillacommunity.solon;

import org.noear.solon.SolonApp;

public class IOC {
    public static <T> T get(Class<?> clazz) {
        if(clazz.equals(SolonApp.class)) return (T) App.getSolonApp();
        return (T) App.getSolonApp().context().getBean(clazz);
    }
}
