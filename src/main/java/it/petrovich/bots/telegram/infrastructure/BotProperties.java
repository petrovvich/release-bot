package it.petrovich.bots.telegram.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Getter
@Setter
@Validated
public class BotProperties {
    @NotEmpty
    private String token;
    @NotEmpty
    private String username;
    @NotEmpty
    private Collection<String> chats;
}
