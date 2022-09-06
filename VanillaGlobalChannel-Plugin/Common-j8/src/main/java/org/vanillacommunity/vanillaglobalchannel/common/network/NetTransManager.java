package org.vanillacommunity.vanillaglobalchannel.common.network;

import java.util.concurrent.TimeUnit;

import okhttp3.*;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.mcserver.MCServerManager;

public class NetTransManager {
    private static NetTransManager instance;

    private final OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .callTimeout(10L,TimeUnit.SECONDS)
            .readTimeout(10L,TimeUnit.SECONDS)
            .writeTimeout(10L,TimeUnit.SECONDS)
            .connectTimeout(10L,TimeUnit.SECONDS)
            .pingInterval(15,TimeUnit.SECONDS)
            .build();

    private WebSocket webSocket;

    private final String connectURL = "http://"+ ConfigManager.centralServerIP+"/ws/"+ConfigManager.serverID+"/"+ConfigManager.account+"/"+ConfigManager.password;

    private NetTransManager(){}

    public static NetTransManager getInstance()
    {
        if(instance == null)instance = new NetTransManager();
        return instance;
    }

    public WebSocket getWebSocket() { return webSocket; }

    public void init(){
        Request request = new Request.Builder().url(connectURL).build();

        webSocket = httpClient.newWebSocket(request,new WebSocketListenerImpl());

    }
    public void parseDataPack(String dataPackStr)
    {
        DataPack dataPack = DataPack.fromString(dataPackStr);
        if(!MCServerManager.getInstance().getServerList().containsKey(dataPack.getServerID()))return;

        String finalMessage = ConfigManager.channelMessageFormat.
                replaceAll("%channelName%",ChannelManager.getInstance().getChannelMap().get(dataPack.getChannelID()).getDisplayName())
                        .replaceAll("%serverName%",MCServerManager.getInstance().getServerList().get(dataPack.getServerID()).getDisplayName())
                                .replaceAll("%playerName%",dataPack.getPlayerDisplayName())
                                        .replaceAll("%message%",dataPack.getMessage());

        ChannelManager.getInstance().broadcast(dataPack.getChannelID(), finalMessage);
    }

    public boolean sendChatData(DataPack dataPack){
        if(webSocket == null)return false;
        webSocket.send(dataPack.toString());
        return true;
    }
}
