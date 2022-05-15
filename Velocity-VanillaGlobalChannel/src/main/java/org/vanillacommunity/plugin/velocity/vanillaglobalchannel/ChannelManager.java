package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import lombok.SneakyThrows;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ChannelManager {
    private static ChannelManager instance;
    


    private final Map<Integer,String> channelInfoMap = new HashMap<>();
    private final Queue<ClientDataPack> clientDataPackQueue = new LinkedList<>();

    public Map<Integer, String> getChannelInfoMap() {
        return channelInfoMap;
    }

    public static ChannelManager getInstance()
    {
        if(instance == null)instance = new ChannelManager();
        return instance;
    }

    public void offerPack(ClientDataPack clientDataPack)
    {
        clientDataPackQueue.offer(clientDataPack);
        if(clientDataPackQueue.size() > 10)clientDataPackQueue.poll();
    }

    public void initChannel() throws ObjectMappingException
    {
        if(NetTransManager.getInstance().getChannelInfo())
        {
            initChannelQueue();
            initChannelListener();
        }
    }
    private void initChannelQueue()
    {
        VanillaGlobalChannel.instance.getServer().getScheduler().buildTask(VanillaGlobalChannel.instance, new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(!clientDataPackQueue.isEmpty())
                {
                    ClientDataPack clientDataPack = clientDataPackQueue.peek();
                    if(clientDataPack == null)return;
                    if(NetTransManager.getInstance().sendChatData(clientDataPack))
                    {
                        //发送成功才出栈
                        clientDataPackQueue.poll();
                    }
                }
            }
        }).repeat(100L, TimeUnit.MILLISECONDS).schedule();
    }
    private void initChannelListener()
    {
        for(int channelID : channelInfoMap.keySet())
        {
            VanillaGlobalChannel.instance.getServer().getScheduler().buildTask(VanillaGlobalChannel.instance, new Runnable() {
                @SneakyThrows
                @Override
                public void run() {

                    Request request = new Request.Builder()
                            .url("http://"+ConfigManager.getInstance().getCentralServerIP()+"/consume/"+channelID+"/"+ConfigManager.getInstance().getServerID()).build();

                    NetTransManager.getInstance().getHttpClient().newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response){
                            String[] dataArgs = response.message().split("\\|");
                            if(dataArgs.length < 4)return;
                            int channelID = Integer.parseInt(dataArgs[0]);
                            int serverID = Integer.parseInt(dataArgs[1]);
                            String playerName = dataArgs[2];
                            String message = dataArgs[3];

                        }
                    });

                }
            }).repeat(250, TimeUnit.MILLISECONDS).schedule();
        }
    }
}
