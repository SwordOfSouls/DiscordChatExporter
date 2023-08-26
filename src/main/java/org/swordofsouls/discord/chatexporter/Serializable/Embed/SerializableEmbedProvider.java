package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.EmbedProvider;
import org.javacord.api.entity.message.embed.EmbedVideo;

import java.net.URL;

@Getter
public class SerializableEmbedProvider {
    private String name;
    private URL url;

    public SerializableEmbedProvider(EmbedProvider embedProvider) {
        name = embedProvider.getName();
        url = embedProvider.getUrl();
    }
}
