package org.vanillacommunity.solon.entity.message;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 一条全局消息，本身不属于任何一个频道
 */
@Data
@Builder
public class GlobalMessage {
    // 消息发送的日期时间戳
    private Date sendDate;
    // 消息归属的客户端账户
    private String clientAccount;
    // 消息发送者显示名称
    private String senderDisplayName;
    // 消息内容
    private String content;

    public static GlobalMessage fromJson(String joStr) {
        return fromJson(JsonParser.parseString(joStr).getAsJsonObject());
    }

    public static GlobalMessage fromJson(JsonObject jo) {
        return GlobalMessage.builder()
                .sendDate(new Date(jo.get("send_date").getAsLong()))
                .clientAccount(jo.get("client_account").getAsString())
                .senderDisplayName(jo.get("sender_display_name").getAsString())
                .content(jo.get("content").getAsString())
                .build();

    }

    public JsonObject toJson() {
        JsonObject jo = new JsonObject();
        jo.addProperty("send_date", sendDate.getTime());
        jo.addProperty("client_account", clientAccount);
        jo.addProperty("sender_display_name", senderDisplayName);
        jo.addProperty("content", content);
        return jo;
    }
}
