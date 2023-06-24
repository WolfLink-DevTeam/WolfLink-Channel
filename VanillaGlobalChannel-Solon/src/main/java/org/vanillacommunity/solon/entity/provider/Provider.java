package org.vanillacommunity.solon.entity.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Provider {
    // 唯一主键
    String account;
    String token;
}
