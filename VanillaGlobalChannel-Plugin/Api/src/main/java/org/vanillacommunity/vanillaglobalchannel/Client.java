package org.vanillacommunity.vanillaglobalchannel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    // 唯一主键
    String account;
    String token;
}
