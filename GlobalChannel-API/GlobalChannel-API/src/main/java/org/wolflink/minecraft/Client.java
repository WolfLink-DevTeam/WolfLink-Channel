package org.wolflink.minecraft;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    // 唯一主键
    String account;
    // 名称(不带有颜色符号)
    String name;
    // 显示名称(带有颜色符号)
    String displayName;

    public Client(Client client) {
        this.account = client.account;
        this.name = client.name;
        this.displayName = client.displayName;
    }

}
