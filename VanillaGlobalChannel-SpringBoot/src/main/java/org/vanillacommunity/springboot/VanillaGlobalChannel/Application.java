package org.vanillacommunity.springboot.VanillaGlobalChannel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vanillacommunity.springboot.VanillaGlobalChannel.controller.WSController;


@SpringBootApplication
public class Application {

	public static ConfigurableApplicationContext appContext;

	public static MCServerManager mcServerManager;

	public static WSController wsController;

	public static WSManager wsManager;

	public static ChannelManager channelManager;

//	public static SessionHeartBeatTask heartBeatTask;

	public static void main(String[] args) {
		appContext = SpringApplication.run(Application.class, args);

		mcServerManager = (MCServerManager) appContext.getBean("mcServerManager");
		wsController = (WSController) appContext.getBean("wsController");
		wsManager = (WSManager) appContext.getBean("wsManager");
		channelManager = (ChannelManager) appContext.getBean("channelManager");
//		heartBeatTask = (SessionHeartBeatTask) appContext.getBean("heartBeatTask");

		mcServerManager.init();
		channelManager.init();
	}
}
