package org.vanillacommunity.solon.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Message;
import org.noear.solon.lang.Nullable;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.SecureChannel;
import org.vanillacommunity.solon.entity.OnlineClient;
import org.vanillacommunity.solon.repository.SecureChannelRepository;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.wolflink.minecraft.DataPack;
import org.wolflink.minecraft.GlobalMessage;
import org.wolflink.minecraft.MsgType;

import java.util.List;
import java.util.Set;

@Singleton(true)
@Component
public class WebSocketService {
    @Inject
    ChannelService channelService;
    @Inject
    ClientService clientService;
    @Inject
    SecureChannelRepository secureChannelRepository;
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    Logger logger;

    /**
     * 规范化Websocket交互信息
     *
     * @param msgType       消息类型
     * @param globalMessage 消息内容
     */
    public String formatData(MsgType msgType, GlobalMessage globalMessage) {
        return new DataPack(msgType, globalMessage.toJson().toString()).toJson().toString();
    }

    public String formatData(MsgType msgType, JsonElement jsonElement) {
        return new DataPack(msgType, jsonElement.toString()).toJson().toString();
    }

    /**
     * 从给定的JSON格式字符串解析数据包
     *
     * @param input JSON格式字符串
     * @return DataPack对象
     */
    @Nullable
    public DataPack unpackData(String input) {
        return DataPack.fromJson(input);
    }

    /**
     * 解析客户端发来的数据
     */
    public void analyseMessage(OnlineClient onlineClient, Message message) {
        DataPack dataPack = unpackData(message.bodyAsString());
        if(dataPack == null) return;
        int channelId = onlineClient.getChannelId();
        SecureChannel secureChannel = secureChannelRepository.find(channelId);
        if (secureChannel == null) {
            logger.err("不存在ID为 " + channelId + " 的频道，来自在线用户 " + onlineClient.getAccount());
            return;
        }
        // 客户端发来频道消息，则需要广播这条频道消息到客户端所处频道中
        if (dataPack.getType() == MsgType.CHANNEL) {
            GlobalMessage globalMessage = GlobalMessage.fromJson(dataPack.getContent());
            // 把这条消息广播给对应频道
            channelService.broadcast(channelId, MsgType.CHANNEL, globalMessage);
        }
        // 客户端发来系统消息
        // TODO 这里可以考虑重构
        if (dataPack.getType() == MsgType.SYSTEM) {
            String command = dataPack.getContent();
            switch (command) {
                case "lookup_history": {
                    JsonArray jsonArray = new JsonArray();
                    List<GlobalMessage> msgHistory = secureChannel.getMessageContainer().lookup(100);
                    msgHistory.forEach(globalMessage -> jsonArray.add(globalMessage.toJson().toString()));
                    clientService.sendSystemMsg(onlineClient, jsonArray);
                    break;
                }
                case "query_online": {
                    Set<OnlineClient> onlineClients = onlineClientRepository.filterByChannelId(channelId);
                    JsonObject jsonObject = new JsonObject();
                    JsonArray jsonArray = new JsonArray();
                    onlineClients.forEach(onlineClient1 -> {
                        JsonObject jsonObject1 = new JsonObject();
                        jsonObject1.addProperty("name", onlineClient1.getName());
                        jsonObject1.addProperty("display_name", onlineClient1.getDisplayName());
                        jsonObject1.addProperty("account", onlineClient1.getAccount());
                        jsonObject1.addProperty("online_time", onlineClient1.getOnlineTime().toString());
                        jsonArray.add(jsonObject1);
                    });
                    jsonObject.addProperty("online_count", onlineClients.size());
                    jsonObject.add("online_clients", jsonArray);
                    clientService.sendSystemMsg(onlineClient, jsonArray);
                    break;
                }
                default:
            }
        }
    }
}
