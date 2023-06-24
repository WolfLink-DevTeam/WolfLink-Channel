package org.vanillacommunity.solon.entity.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Client {
    // 唯一主键
    String account;
    String token;
}
