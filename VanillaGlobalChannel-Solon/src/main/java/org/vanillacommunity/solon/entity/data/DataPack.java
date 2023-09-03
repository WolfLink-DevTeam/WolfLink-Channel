package org.vanillacommunity.solon.entity.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.noear.solon.lang.Nullable;
import org.vanillacommunity.solon.MsgType;

/**
 * WebSocket 通信的最外层数据包结构
 * content 可能是与插件系统的交互字符串
 * 也可能是GlobalMessage的Json格式化字符串
 */
@Builder
@AllArgsConstructor
public class DataPack {
    @Getter
    MsgType type;
    @Getter
    String content;

    @Nullable
    public static DataPack fromJson(String jsonStr) {
        try {
            JsonObject jo = JsonParser.parseString(jsonStr).getAsJsonObject();
            return DataPack.builder()
                    .type(MsgType.valueOf(jo.get("type").getAsString()))
                    .content(jo.get("content").getAsString())
                    .build();
        } catch (Exception ignore) { return null; }
    }

    public JsonObject toJson() {
        JsonObject jo = new JsonObject();
        jo.addProperty("type", type.name());
        jo.addProperty("content", content);
        return jo;
    }
}
