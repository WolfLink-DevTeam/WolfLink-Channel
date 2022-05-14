package org.vanillacommunity.springboot.VanillaGlobalChannel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Application {
	static MCServerManager mcServerManager;
	static ChannelManager channelManager;
	public static void main(String[] args) {
		//初始化管理类实例
		mcServerManager = MCServerManager.getInstance();
		channelManager = ChannelManager.getInstance();

		//初始化服务器列表
		mcServerManager.initMCServers();
		//初始化频道列表
		channelManager.initChannels(10);

		SpringApplication.run(Application.class, args);
	}
}
