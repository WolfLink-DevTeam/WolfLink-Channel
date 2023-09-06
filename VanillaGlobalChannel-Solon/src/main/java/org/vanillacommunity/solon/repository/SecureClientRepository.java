package org.vanillacommunity.solon.repository;


import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.SecureClient;

/**
 * 客户端仓库
 * 管理所有在配置文件中约定好的客户端
 */
@Singleton(true)
@Component
public class SecureClientRepository extends Repository<String, SecureClient> {
    public SecureClientRepository() {
        super(SecureClient::getAccount);
    }
}
