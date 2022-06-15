package org.vanillacommunity.vanillaglobalchannel.common;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.network.NetTransManager;

public class Main {

    private static Main instance;

    private Main(){}

    public static Main getInstance()
    {
        if(instance == null)instance = new Main();
        return instance;
    }

    //插件初始化(服务端还在加载插件)
    public void onInit(){
        try
        {
            ConfigManager.getInstance().init();
        } catch (ObjectMappingException e)
        {
            e.printStackTrace();
        }
        NetTransManager.getInstance().init();
    }
    //服务器启动
    public void onStart()
    {

    }
    //服务器关闭
    public void onShutdown()
    {
        //停止对中央服务器的消息发送
        if(ChannelManager.getInstance().timer != null) ChannelManager.getInstance().timer.cancel();
        //关闭WebSocket连接
        if(NetTransManager.getInstance().getWebSocket() != null) NetTransManager.getInstance().getWebSocket().close(1000,"服务器主动关闭连接");
    }
}
