package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.vanillacommunity.solon.repository.SecureClientRepository;
import org.wolflink.common.ioc.IOC;
import org.wolflink.minecraft.Client;

@Controller
@Mapping("/query")
public class HttpController {
    @Mapping("/client")
    public Client queryClient(String client_account) {
        return IOC.getBean(SecureClientRepository.class).find(client_account);
    }
}
