package org.vanillacommunity.solon.entity.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.vanillacommunity.solon.api.enums.PlatformType;

import java.util.Set;

@Data
@AllArgsConstructor
public class Provider {
    // 唯一主键
    String account;
    String token;
    Set<PlatformType> allowedPlatforms;
}
