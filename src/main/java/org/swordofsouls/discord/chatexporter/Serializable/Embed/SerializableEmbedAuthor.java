package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.EmbedAuthor;

import java.net.URL;
import java.util.Optional;

@Getter
public class SerializableEmbedAuthor {
    private String name;
    private Optional<URL> url;
    private Optional<URL> iconUrl;
    private Optional<URL> proxyIconUrl;

    public SerializableEmbedAuthor(EmbedAuthor embedAuthor) {
        name = embedAuthor.getName();
        url = embedAuthor.getUrl();
        iconUrl = embedAuthor.getIconUrl();
        proxyIconUrl = embedAuthor.getProxyIconUrl();
    }
}
