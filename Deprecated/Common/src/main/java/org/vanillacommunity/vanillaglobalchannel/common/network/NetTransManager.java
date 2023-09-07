package org.vanillacommunity.vanillaglobalchannel.common.network;

import java.util.concurrent.TimeUnit;

import okhttp3.*;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.mcserver.MCServerManager;

/**
 * 网络传输相关的业务类
 */
public class NetTransManager {
    private static NetTransManager instance;

    // 初始化 OKHttp 的客户端
    private final OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .callTimeout(10L,TimeUnit.SECONDS)
            .readTimeout(10L,TimeUnit.SECONDS)
            .writeTimeout(10L,TimeUnit.SECONDS)
            .connectTimeout(10L,TimeUnit.SECONDS)
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
        // 初始化 WebSocket 对象并绑定对应的监听器
        webSocket = httpClient.newWebSocket(request,new WebSocketListenerImpl());
    }
    // 解析服务端发来的频道消息数据包并广播给对应频道
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
    // 发送聊天数据
    public boolean sendChatData(DataPack dataPack){
        if(webSocket == null)return false;
        webSocket.send(dataPack.toString());
        return true;
    }
}
