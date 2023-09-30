package org.wolflink.minecraft.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.*;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.interfaces.IPlayer;
import org.wolflink.minecraft.interfaces.PlatformAdapter;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Singleton
public class Network implements HttpAPI {
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
        return "ws://"+configuration.getCentralServerIp()+":"+configuration.getCentralServerPort()+"/connection";
    }

    private void enable() {
        if(webSocket != null) webSocket.close(1012,"客户端网络服务重启");
        Request request = new Request.Builder()
                .url(getConnectionUrl()
                        +"?account="+configuration.getAccount()
                        +"&password="+configuration.getPassword()
                        +"&channel_id="+configuration.getChannelId()
                        +"&platform_type="+IOC.getBean(PlatformAdapter.class).getPlatformType()
                )
                .get()
                .build();
        webSocket = httpClient.newWebSocket(request, IOC.getBean(WSListener.class));
    }
    private void disable() {
    }

    @Override
    public Client queryClient(String client_account) {
        RequestBody requestBody = new FormBody.Builder()
                .add("client_account",client_account)
                .build();
        Request request = new Request.Builder()
                .url(getConnectionUrl())
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if(response.body() == null) return null;
            return new Gson().fromJson(response.body().string(),Client.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Channel queryChannel(int channel_id) {
        RequestBody requestBody = new FormBody.Builder()
                .add("channel_id", String.valueOf(channel_id))
                .build();
        Request request = new Request.Builder()
                .url(getConnectionUrl())
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if(response.body() == null) return null;
            return new Gson().fromJson(response.body().string(), Channel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Client> queryChannelOnlineClients(int channel_id) {
        RequestBody requestBody = new FormBody.Builder()
                .add("channel_id",String.valueOf(channel_id))
                .build();
        Request request = new Request.Builder()
                .url(getConnectionUrl())
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if(response.body() == null) return null;
            JsonElement jsonElement = JsonParser.parseString(response.body().string());
            Set<Client> clients = new HashSet<>();
            if(jsonElement instanceof JsonArray) {
                JsonArray jsonArray = (JsonArray) jsonElement;
                jsonArray.forEach(element -> {
                    clients.add(new Gson().fromJson(element.getAsString(),Client.class));
                });
            }
            return clients;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void sendChat(IPlayer iPlayer,String msg) {
        GlobalMessage globalMessage = GlobalMessage.builder()
                .clientAccount(IOC.getBean(Configuration.class).getAccount())
                .sendDate(new Date())
                .senderDisplayName(iPlayer.getName())
                .content(msg)
                .build();
        DataPack dataPack = DataPack.builder()
                .type(MsgType.CHANNEL)
                .content(globalMessage.toJson())
                .build();
        webSocket.send(dataPack.toJson().toString());
    }
}
