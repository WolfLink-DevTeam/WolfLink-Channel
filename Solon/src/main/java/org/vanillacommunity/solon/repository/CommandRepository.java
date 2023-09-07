package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.vanillacommunity.solon.entity.Command;
import org.wolflink.common.ioc.Singleton;

@Singleton
@Component
public class CommandRepository extends Repository<String, Command> {
    public CommandRepository() {
        super(Command::getText);
    }
}
