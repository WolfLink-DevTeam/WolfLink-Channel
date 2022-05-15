package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import java.io.IOException;
import java.util.*;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

public class NetTransManager {
    private static NetTransManager instance;
    private final OkHttpClient httpClient = new OkHttpClient();

    private NetTransManager(){}

    public static NetTransManager getInstance()
    {
        if(instance == null)instance = new NetTransManager();
        return instance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public boolean getChannelInfo() throws ObjectMappingException
    {
        Request request = new Request.Builder()
                .url("http://"+ConfigManager.getInstance().getCentralServerIP()+"/getchannels ").build();

        final boolean[] getInfoStatus = new boolean[1];
        final String[] channelsInfoStr = new String[1];
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                VanillaGlobalChannel.instance.getLogger().info("中央服务器连接失败！未能获得频道信息，插件将停止运行。");
                getInfoStatus[0] = false;
            }
            @Override
            public void onResponse(Call call, Response response)
            {
                channelsInfoStr[0] = response.message();
                getInfoStatus[0] = true;
            }
        });
        if(getInfoStatus[0])
        {
            List<String> channelStringList = Tools.stringToList(channelsInfoStr[0]);
            for(String str : channelStringList)
            {
                String[] strings = str.split("/");
                int channelID = Integer.parseInt(strings[0]);
                String channelName = strings[1];
                ChannelManager.getInstance().getChannelInfoMap().put(channelID,channelName);
            }
        }
        return getInfoStatus[0];
    }



    public boolean sendChatData(ClientDataPack clientDataPack) throws ObjectMappingException
    {
        Request request = new Request.Builder()
                .url("http://"+ConfigManager.getInstance().getCentralServerIP()+"/chat/"+clientDataPack.getChannelID()+"/"+clientDataPack.getServerID()+"/"+clientDataPack.getPlayerDisplayName()+"/"+clientDataPack.getMessage()).build();
        final boolean[] result = new boolean[1];
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result[0] = false;
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response){
                result[0] = Boolean.getBoolean(response.message());
            }
        });
        return result[0];
    }
}
