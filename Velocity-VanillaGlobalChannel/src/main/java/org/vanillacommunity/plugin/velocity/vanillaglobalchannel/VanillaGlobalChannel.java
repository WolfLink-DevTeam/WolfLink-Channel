package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(
        id = "vanillaglobalchannel",
        name = "VanillaGlobalChannel",
        version = "1.0-SNAPSHOT",
        authors = {"VanillaNonBenefitCommunity"}
)
public class VanillaGlobalChannel {

    public static VanillaGlobalChannel instance;
    private final ProxyServer server;
    private Logger logger;
    @Inject
    public VanillaGlobalChannel(ProxyServer server,Logger logger,@DataDirectory Path path) throws IOException {
        this.server = server;
        this.logger = logger;
        ConfigManager.config = loadConfig(path);
        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) throws ObjectMappingException {

        ChannelManager.getInstance().initChannel();

        server.getEventManager().register(this,new OnChatEvent());
        server.getEventManager().register(this,new OnLoginEvent());
    }

    private ConfigurationNode loadConfig(Path path) throws IOException {
        File folder = path.toFile();
        File file = new File(folder, "config.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try (InputStream input = getClass().getResourceAsStream("/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return YAMLConfigurationLoader.builder().setFile(file).build().load();
    }


    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }
}
