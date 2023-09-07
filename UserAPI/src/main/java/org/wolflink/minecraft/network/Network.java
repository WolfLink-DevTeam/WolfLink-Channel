package org.wolflink.minecraft.network;

import okhttp3.*;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.file.Configuration;

import java.util.concurrent.TimeUnit;

@Singleton
public class Network {
    @Inject
    private Configuration configuration;
    private WebSocket webSocket = null;
    private final OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .callTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L,TimeUnit.SECONDS)
            .writeTimeout(10L,TimeUnit.SECONDS)
            .connectTimeout(10L,TimeUnit.SECONDS)
            .build();
    private boolean enabled = false;
    public void setEnabled(boolean value) {
        if(enabled == value) return;
        enabled = value;
        if(enabled) enable();
        else disable();
    }
    private String getConnectionUrl() {
        return "ws://"+configuration.getCentralServerIp()+":"+configuration.getCentralServerPort()+"/Connection";
    }

    private void enable() {
        if(webSocket != null) webSocket.close(1012,"客户端网络服务重启");
        RequestBody requestBody = new FormBody.Builder()
                .add("account",configuration.getAccount())
                .add("password",configuration.getPassword())
                .add("channel_id", String.valueOf(configuration.getChannelId()))
                .build();
        Request request = new Request.Builder()
                .url(getConnectionUrl())
                .post(requestBody)
                .build();
        webSocket = httpClient.newWebSocket(request, IOC.getBean(WSListener.class));
    }
    private void disable() {
    }
}
