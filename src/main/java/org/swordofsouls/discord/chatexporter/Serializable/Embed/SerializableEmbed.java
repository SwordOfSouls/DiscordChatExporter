package org.swordofsouls.discord.chatexporter.Serializable.Embed;

import lombok.Getter;
import org.javacord.api.entity.message.embed.*;

import java.awt.*;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class SerializableEmbed {
    private Optional<String> title;
    private String type;
    private Optional<String> description;
    private Optional<URL> url;
    private Optional<Instant> timestamp;
    private Optional<Color> color;
    private Optional<SerializableEmbedFooter> footer;
    private Optional<SerializableEmbedImage> image;
    private Optional<SerializableEmbedImage> thumbnail;
    private Optional<SerializableEmbedVideo> video;
    private Optional<SerializableEmbedProvider> provider;
    private Optional<SerializableEmbedAuthor> author;

    private List<SerializableEmbedField> fields = new ArrayList<>();

    public SerializableEmbed(Embed embed) {
        title = embed.getTitle();
        type = embed.getType();
        description = embed.getDescription();
        url = embed.getUrl();
        timestamp = embed.getTimestamp();
        color = embed.getColor();
        if(embed.getFooter().isPresent()) footer = Optional.of(new SerializableEmbedFooter(embed.getFooter().get()));
        else footer = Optional.empty();
        if(embed.getImage().isPresent()) image = Optional.of(new SerializableEmbedImage(embed.getImage().get()));
        else image = Optional.empty();
        if(embed.getThumbnail().isPresent()) thumbnail = Optional.of(new SerializableEmbedImage(embed.getThumbnail().get()));
        else thumbnail = Optional.empty();
        if(embed.getVideo().isPresent()) video = Optional.of(new SerializableEmbedVideo(embed.getVideo().get()));
        else video = Optional.empty();
        if(embed.getProvider().isPresent()) provider = Optional.of(new SerializableEmbedProvider(embed.getProvider().get()));
        else provider = Optional.empty();
        if(embed.getAuthor().isPresent()) author = Optional.of(new SerializableEmbedAuthor(embed.getAuthor().get()));
        else author = Optional.empty();

        embed.getFields().forEach(embedField -> fields.add(new SerializableEmbedField(embedField)));
    }
}
