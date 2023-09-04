package org.vanillacommunity.solon.entity;

import lombok.Data;

import java.util.List;

@Data
public class Client {
    // 唯一主键
    String account;
    // 连接密钥
    String password;
    // 名称(不带有颜色符号)
    String name = "";
    // 显示名称(带有颜色符号)
    String displayName = "";

    List<String> ipSegments;
    public Client(String account, String password, List<String> ipSegments) {
        this.account = account;
        this.password = password;
        this.ipSegments = ipSegments;
    }
}
