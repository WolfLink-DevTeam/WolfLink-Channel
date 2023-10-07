package org.wolflink.minecraft.actions;

import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Result;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.file.Language;

@Singleton
public class PluginAction {
    @Inject
    Configuration configuration;
    @Inject
    Language language;
    public Result reloadFiles() {
        configuration.load();
        language.load();
        return new Result(true,language.getReloadSuccess());
    }
}
