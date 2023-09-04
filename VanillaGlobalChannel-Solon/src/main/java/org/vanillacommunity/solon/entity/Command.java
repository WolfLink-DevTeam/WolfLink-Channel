package org.vanillacommunity.solon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Consumer;

@Data
@AllArgsConstructor
public class Command {
    private final String text;
    private final Consumer<String> consumer;
}
