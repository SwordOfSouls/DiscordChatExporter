package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.EmbedFooter;

import java.net.URL;
import java.util.Optional;

@Getter
public class SerializableEmbedFooter {
    private Optional<String> text;
    private Optional<URL> iconUrl;
    private Optional<URL> proxyIconUrl;

    public SerializableEmbedFooter(EmbedFooter embedFooter) {
        text = embedFooter.getText();
        iconUrl = embedFooter.getIconUrl();
        proxyIconUrl = embedFooter.getProxyIconUrl();
    }
}
