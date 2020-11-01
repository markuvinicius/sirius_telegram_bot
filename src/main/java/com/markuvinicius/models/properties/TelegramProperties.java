package com.markuvinicius.models.properties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelegramProperties {
    private String botUserName;
    private String botTelegramToken;
}
