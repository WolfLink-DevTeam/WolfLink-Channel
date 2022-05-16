package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.velocitypowered.api.proxy.Player;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ChannelManager {
    private static ChannelManager instance;

    private final Map<Integer,List<UUID>> channelUserMap = new HashMap<>();

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

    public void initChannel() throws IOException {
        VanillaGlobalChannel.instance.getLogger().info(ConfigManager.pluginStart);
        if(NetTransManager.getInstance().getChannelInfo())
        {
            VanillaGlobalChannel.instance.getLogger().info(ConfigManager.pluginStart1);
            initChannelUserList();
            VanillaGlobalChannel.instance.getLogger().info(ConfigManager.pluginStart2);
            initChannelQueue();

            VanillaGlobalChannel.instance.getLogger().info(ConfigManager.pluginStart3);
            initChannelListener();
            VanillaGlobalChannel.instance.getLogger().info(ConfigManager.pluginStartFinish);
        }
        else
        {
            VanillaGlobalChannel.instance.getLogger().info(ConfigManager.cmdPrefix+"中央服务器连接失败！插件将不能正常运行。");
        }
    }

    public void changeChannel(UUID uuid,int oldChannelID,int newChannelID)
    {
        if(oldChannelID != -1) channelUserMap.get(oldChannelID).remove(uuid);
        channelUserMap.get(newChannelID).add(uuid);
    }

    private void initChannelUserList()
    {
        for(int channelID : channelInfoMap.keySet())
        {
            channelUserMap.put(channelID,new ArrayList<>());
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
        VanillaGlobalChannel.instance.getServer().getScheduler().buildTask(VanillaGlobalChannel.instance, new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                for(int channelID : channelInfoMap.keySet())
                {
                    Request request = new Request.Builder()
                            .url("http://" + ConfigManager.centralServerIP + "/consume/" + channelID + "/" + ConfigManager.serverID).build();
                    Response response = null;
                    try
                    {response = NetTransManager.getInstance().getHttpClient().newCall(request).execute();
                    }catch (IOException ignored)
                    {}
                    if(response == null || !response.isSuccessful())continue;
                    ResponseBody body = response.body();
                    if(body == null)continue;
                    String[] dataArgs = body.string().split("\\|");
                    body.close();
                    response.close();
                    if (dataArgs.length < 4) continue;
                    //int packChannelID = Integer.parseInt(dataArgs[0]);
                    String serverName = dataArgs[1];
                    String playerName = dataArgs[2];
                    String message = dataArgs[3];

                    for (UUID uuid : channelUserMap.get(channelID)) {
                        if (VanillaGlobalChannel.instance.getServer().getPlayer(uuid).isPresent()) {
                            Player p = VanillaGlobalChannel.instance.getServer().getPlayer(uuid).get();

                            String finalMessage = "§7[ §f"+channelInfoMap.get(channelID)+" §7] [ §r"+serverName+" §r§7] [ §a"+playerName+" §7] §8» §7"+message;

                            p.sendMessage(Component.text(finalMessage));
                        }
                    }
                }
            }
        }).repeat(250, TimeUnit.MILLISECONDS).schedule();
    }
}
