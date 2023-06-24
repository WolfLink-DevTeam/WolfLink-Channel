package org.vanillacommunity.solon;

import lombok.Getter;
import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.annotation.SolonMain;
import org.vanillacommunity.solon.config.ChannelsConfig;
import org.vanillacommunity.solon.config.ClientsConfig;
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
        ClientsConfig clientsConfig = IOC.get(ClientsConfig.class);
        clientsConfig.load(solonApp);
        ChannelsConfig channelsConfig = IOC.get(ChannelsConfig.class);
        channelsConfig.load(solonApp);
    }
}