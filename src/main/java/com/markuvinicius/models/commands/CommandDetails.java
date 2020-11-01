package com.markuvinicius.models.commands;

import lombok.*;

@Data
@Builder
public class CommandDetails  {
    private String commandName;
    private String arguments;
    private int index;
}
