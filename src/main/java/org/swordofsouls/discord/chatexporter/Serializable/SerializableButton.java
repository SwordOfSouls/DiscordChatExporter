package org.swordofsouls.discord.chatexporter.Serializable;

import lombok.Getter;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonStyle;

import java.util.Optional;

@Getter
public class SerializableButton {
    private ButtonStyle style;
    private Optional<String> label;
    private Optional<String> customId;
    private Optional<String> url;
    private Optional<Boolean> disabled;
    private Optional<Emoji> emoji;

    public SerializableButton(Button button) {
        style = button.getStyle();
        label = button.getLabel();
        customId = button.getCustomId();
        url = button.getUrl();
        disabled = button.isDisabled();
        emoji = button.getEmoji();
    }
}
