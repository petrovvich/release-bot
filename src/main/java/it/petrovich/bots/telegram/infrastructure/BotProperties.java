package it.petrovich.bots.telegram.infrastructure;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import java.util.Collection;

@Getter
@Setter
public class BotProperties {
    @NotEmpty
    private String token;
    @NotEmpty
    private String username;
    @NotEmpty
    private Collection<String> chats;
}
