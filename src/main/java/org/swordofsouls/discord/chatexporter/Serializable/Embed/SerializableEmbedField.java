package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.EmbedField;

@Getter
public class SerializableEmbedField {
    private String name;
    private String value;
    private boolean inline;

    public SerializableEmbedField(EmbedField embedField) {
        name = embedField.getName();
        value = embedField.getValue();
        inline = embedField.isInline();
    }
}
