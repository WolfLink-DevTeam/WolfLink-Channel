package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.command.Command;

@Singleton(true)
@Component
public class CommandRepository extends Repository<String, Command> {
    public CommandRepository() {
        super(Command::getText);
    }
}
