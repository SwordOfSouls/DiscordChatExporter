package org.swordofsouls.discord.chatexporter.Serializable;

import lombok.Getter;
import org.javacord.api.entity.Attachment;

import java.net.URL;

@Getter
public class SerializableAttachment {
    private final long id;
    private final String fileName;
    private final String description;
    private final int size;
    private final URL url;
    private final URL proxyUrl;
    private final Integer height;
    private final Integer width;
    private final Boolean ephemeral;

    public SerializableAttachment(Attachment attachment) {
        id = attachment.getId();
        fileName = attachment.getFileName();
        description = attachment.getDescription().orElse(null);
        size = attachment.getSize();
        url = attachment.getUrl();
        proxyUrl = attachment.getProxyUrl();
        height = attachment.getHeight().orElse(null);
        width = attachment.getHeight().orElse(null);
        ephemeral = attachment.isEphemeral().orElse(null);
    }
}
