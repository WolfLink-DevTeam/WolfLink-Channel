package org.vanillacommunity.solon.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ChannelMessage {
    // 消息发送的日期时间戳
    private Date sendDate;
    // 消息归属的客户端账户
    private String clientAccount;
    // 消息发送者名称
    private String sender;
    // 消息内容
    private String content;
    public static ChannelMessage fromJson(String joStr) {
        return fromJson(JsonParser.parseString(joStr).getAsJsonObject());
    }
    private static ChannelMessage fromJson(JsonObject jo) {
        return ChannelMessage.builder()
                .sendDate(new Date(jo.get("send_date").getAsLong()))
                .clientAccount(jo.get("client_account").getAsString())
                .sender(jo.get("sender").getAsString())
                .content(jo.get("content").getAsString())
                .build();

    }
    public JsonObject toJson() {
        JsonObject jo = new JsonObject();
        jo.addProperty("send_date",sendDate.getTime());
        jo.addProperty("client_account",clientAccount);
        jo.addProperty("sender",sender);
        jo.addProperty("content",content);
        return jo;
    }
}
