package org.swordofsouls.discord.chatexporter.Serializable;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.message.component.*;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.internal.MessageBuilderBaseDelegate;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.core.util.FileContainer;
import org.javacord.core.util.logging.LoggerUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Getter
public class SerializableMessage {
    protected String content;
    protected long messageId;
    protected long authorId;
    protected SerializableInstant creationStamp;
    protected SerializableOptional<SerializableInstant> lastEditStamp;
    protected List<Embed> embeds = new ArrayList<>();
    protected boolean tts = false;
    protected SerializableOptional<String> nonce;
    protected final List<SerializableAttachment> attachments = new ArrayList<>();
    protected final List<HighLevelComponent> components = new ArrayList<>();
    protected Set<Long> stickerIds = new HashSet<>();

    public SerializableMessage(Message message) {
        message.getStickerItems().forEach(stickerItem -> stickerIds.add(stickerItem.getId()));

        for(MessageAttachment messageAttachment : message.getAttachments()) this.attachments.add(new SerializableAttachment(messageAttachment));

        for(HighLevelComponent component : message.getComponents()) {
            if (component.getType() == ComponentType.ACTION_ROW) {
                ActionRowBuilder builder = new ActionRowBuilder();
                builder.copy((ActionRow) component);
                this.components.add(builder.build());
            }
        }

        this.embeds.addAll(message.getEmbeds());
        this.tts = message.isTts();
        this.nonce = new SerializableOptional<>(message.getNonce());
        this.content = message.getContent();
        this.messageId = message.getId();
        this.authorId = message.getUserAuthor().get().getId();
        this.creationStamp = new SerializableInstant(message.getCreationTimestamp());
        if(message.getLastEditTimestamp().isPresent()) this.lastEditStamp = new SerializableOptional<>(Optional.of(new SerializableInstant(message.getLastEditTimestamp().get())));
        else this.lastEditStamp = SerializableOptional.empty();
    }
}