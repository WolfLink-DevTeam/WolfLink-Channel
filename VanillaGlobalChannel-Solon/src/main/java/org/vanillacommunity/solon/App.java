package org.vanillacommunity.solon;

import lombok.Getter;
import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.annotation.SolonMain;
import org.vanillacommunity.solon.config.ProvidersConfig;
@SolonMain
public class App {

    @Getter
    private static SolonApp solonApp;
    public static void main(String[] args) {
        solonApp = Solon.start(App.class, args, app -> {
            app.enableWebSocket(true);
            app.enableWebSocketMvc(false);
        });
        initCfg();
    }
    private static void initCfg() {
        ProvidersConfig providersConfig = IOC.get(ProvidersConfig.class);
        providersConfig.load();
    }
}