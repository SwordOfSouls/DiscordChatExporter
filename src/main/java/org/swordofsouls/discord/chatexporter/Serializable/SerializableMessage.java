package org.swordofsouls.discord.chatexporter.Serializable;

import lombok.Getter;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.ActionRowBuilder;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.swordofsouls.discord.chatexporter.Serializable.Embed.SerializableEmbed;

import java.time.Instant;
import java.util.*;

@Getter
public class SerializableMessage {
    protected final List<SerializableAttachment> attachments = new ArrayList<>();
    protected final List<SerializableHighLevelComponent> components = new ArrayList<>();
    protected String content;
    protected long messageId;
    protected long authorId;
    protected Instant creationStamp;
    protected Optional<Instant> lastEditStamp;
    protected List<SerializableEmbed> embeds = new ArrayList<>();
    protected boolean tts = false;
    protected Optional<String> nonce;
    protected Set<Long> stickerIds = new HashSet<>();

    public SerializableMessage(Message message) {
        message.getStickerItems().forEach(stickerItem -> stickerIds.add(stickerItem.getId()));
        message.getEmbeds().forEach(embed -> embeds.add(new SerializableEmbed(embed)));

        for (MessageAttachment messageAttachment : message.getAttachments())
            this.attachments.add(new SerializableAttachment(messageAttachment));

        for (HighLevelComponent component : message.getComponents()) {
            if (component.getType() == ComponentType.ACTION_ROW) {
                ActionRowBuilder builder = new ActionRowBuilder();
                builder.copy((ActionRow) component);
                this.components.add(new SerializableHighLevelComponent(builder.build()));
            }
        }

        this.tts = message.isTts();
        this.nonce = message.getNonce();
        this.content = message.getContent();
        this.messageId = message.getId();
        this.authorId = message.getAuthor().getId();
        this.creationStamp = message.getCreationTimestamp();
        if (message.getLastEditTimestamp().isPresent())
            this.lastEditStamp = Optional.of((message.getLastEditTimestamp().get()));
        else this.lastEditStamp = Optional.empty();
    }
}