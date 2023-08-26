package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.EmbedImage;
import org.javacord.api.entity.message.embed.EmbedThumbnail;

import java.net.URL;

@Getter
public class SerializableEmbedImage {
    private URL url;
    private URL proxyUrl;
    private int height;
    private int width;

    public SerializableEmbedImage(EmbedImage embedImage) {
        url = embedImage.getUrl();
        url = embedImage.getProxyUrl();
        height = embedImage.getHeight();
        width = embedImage.getWidth();
    }
    public SerializableEmbedImage(EmbedThumbnail embedThumbnail) {
        url = embedThumbnail.getUrl();
        proxyUrl = embedThumbnail.getProxyUrl();
        height = embedThumbnail.getHeight();
        width = embedThumbnail.getWidth();
    }
}
