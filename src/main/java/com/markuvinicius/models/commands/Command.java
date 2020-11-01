package com.markuvinicius.models.commands;

import lombok.*;


@Data
@Builder
public class Command {
    private String commandName;
    private String argument;
}
