package org.vanillacommunity.vanillaglobalchannel.common.network;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.mcserver.MCServerManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;

import java.util.Timer;
import java.util.TimerTask;

public class WebSocketListenerImpl extends WebSocketListener {

    private static int lastWaitingReconnect = 1;
    private static int nowWaitingReconnect = 1;//秒

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        System.out.println(ConfigManager.cmdPrefix+"中央聊天服务器连接成功。");

        ChannelManager.getInstance().initChannel();

        MCServerManager.getInstance().init();

    }
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        if(text.startsWith("[频道信息]"))
        {
            ChannelManager.getInstance().bindChannelsInfo(text.substring(6));
            return;
        }
        if(text.startsWith("[服务器信息]"))
        {
            MCServerManager.getInstance().bindServerInfo(text.substring(7));
            return;
        }
        NetTransManager.getInstance().parseDataPack(text);
    }
    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        System.out.println(ConfigManager.cmdPrefix+"与中央聊天服务器的连接已断开，插件停止工作。");
    }
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable throwable, Response response) {
        super.onFailure(webSocket, throwable, response);

        for(PlayerData playerData : PlayerData.dataMap.values())
        {
            playerData.setChannelID(-1);
        }

            Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               System.out.println(ConfigManager.cmdPrefix + "中央聊天服务器连接失败，正在重试...");
                               NetTransManager.getInstance().init();
                               int temp = nowWaitingReconnect;
                               nowWaitingReconnect+=lastWaitingReconnect;
                               lastWaitingReconnect=temp;
                               if(nowWaitingReconnect > 3600)nowWaitingReconnect = 3600;
                           }
                       },nowWaitingReconnect * 1000L);

    }

}
