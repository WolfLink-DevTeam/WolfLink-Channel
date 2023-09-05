package org.wolflink.minecraft;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private final boolean result;
    private final String msg;
}
