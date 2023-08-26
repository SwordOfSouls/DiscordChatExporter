package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.EmbedVideo;

import java.net.URL;

@Getter
public class SerializableEmbedVideo {
    private URL url;
    private int height;
    private int width;

    public SerializableEmbedVideo(EmbedVideo embedVideo) {
        url = embedVideo.getUrl();
        height = embedVideo.getHeight();
        width = embedVideo.getWidth();
    }
}
