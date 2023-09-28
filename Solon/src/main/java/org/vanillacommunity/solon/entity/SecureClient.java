package org.vanillacommunity.solon.entity;

import lombok.Getter;
import org.wolflink.minecraft.Client;

import java.util.List;

@Getter
public class SecureClient extends Client {
    // 连接密钥
    String password;
    List<String> ipSegments;

    public SecureClient(Client client, String password, List<String> ipSegments) {
        super(client.getAccount(), client.getName(), client.getDisplayName());
        this.password = password;
        this.ipSegments = ipSegments;
    }
}
