package org.wolflink.minecraft;

import lombok.Getter;
import lombok.NonNull;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.interfaces.ILogger;
import org.wolflink.minecraft.interfaces.PlatformAdapter;

@Singleton
public class Application {
    @Getter
    private static ILogger logger;
    @Getter
    private static PlatformAdapter platformAdapter;
    @Getter
    private final static Configuration configuration = new Configuration();
    @Getter
    private final static Language language = new Language();

    public Application(@NonNull ILogger logger, @NonNull PlatformAdapter platformAdapter) {
        Application.logger = logger;
        Application.platformAdapter = platformAdapter;
    }

    private boolean enabled = false;
    public void setEnabled(boolean value) {
        if(enabled == value) return;
        enabled = value;
        if(enabled) enable();
        else disable();
    }

    private void enable() {
        configuration.load();
    }
    private void disable() {
    }

}
