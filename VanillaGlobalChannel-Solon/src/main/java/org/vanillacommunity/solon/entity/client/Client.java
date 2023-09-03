package org.vanillacommunity.solon.entity.client;

import lombok.Data;

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

    public Client(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
