package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

public class NetTransManager {
    private static NetTransManager instance;
    private final OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .callTimeout(10L,TimeUnit.SECONDS)
            .readTimeout(10L,TimeUnit.SECONDS)
            .build();

    private NetTransManager(){}

    public static NetTransManager getInstance()
    {
        if(instance == null)instance = new NetTransManager();
        return instance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public boolean getChannelInfo() throws IOException {
        Request request = new Request.Builder()
                .url("http://"+ConfigManager.centralServerIP+"/getchannels").build();
        ResponseBody body = null;
        try
        {
            body = httpClient.newCall(request).execute().body();
        }catch (IOException ignore)
        {}
        if(body == null)return false;
        final String channelsInfoStr = body.string();
        body.close();
        List<String> channelStringList = Tools.stringToList(channelsInfoStr);
        for(String str : channelStringList)
        {
            String[] strings = str.split("/");
            int channelID = Integer.parseInt(strings[0]);
            String channelName = strings[1];
            ChannelManager.getInstance().getChannelInfoMap().put(channelID,channelName);
        }
        return true;
    }

    public boolean sendChatData(ClientDataPack clientDataPack) throws IOException {
        try
        {
            Request request = new Request.Builder()
                    .url("http://"+ConfigManager.centralServerIP+"/chat/"+clientDataPack.getChannelID()+"/"+clientDataPack.getServerID()+"/"+clientDataPack.getPlayerDisplayName()+"/"+clientDataPack.getMessage()).build();
            ResponseBody body = httpClient.newCall(request).execute().body();
            if(body == null)return false;
            String bool = body.string();
            System.out.print(bool);
            return bool.equalsIgnoreCase("true");
        }catch (IOException ignore){ return false; }
    }
}
