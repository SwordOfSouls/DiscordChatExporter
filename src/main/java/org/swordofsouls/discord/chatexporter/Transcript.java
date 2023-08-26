package org.swordofsouls.discord.chatexporter;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserFlag;
import org.swordofsouls.discord.chatexporter.Html.Builders.BaseBuilder;
import org.swordofsouls.discord.chatexporter.Html.Builders.Message.MessageBuilder;
import org.swordofsouls.discord.chatexporter.Html.Builders.Message.MessageGroupBuilder;
import org.swordofsouls.discord.chatexporter.Html.Builders.MetaBuilder;
import org.swordofsouls.discord.chatexporter.Html.Html;
import org.swordofsouls.discord.chatexporter.Serializable.SerializableMessage;
import org.swordofsouls.discord.chatexporter.Utils.File.FileUtils;
import org.swordofsouls.discord.chatexporter.Utils.Time.TimeUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class Transcript {
    public final TextChannel channel;
    public final List<SerializableMessage> messages;
    public final ZoneId timeZone;

    private String customCss = null;
    private String customMedia = null;
    private String customHeader = null;
    private String title = null;
    @Setter
    private String favicon = "";
    private Integer overflow = 126;

    public Transcript(String title, TextChannel channel, ZoneId timeZone) {
        this.channel = channel;
        this.title = title;
        this.timeZone = timeZone;
        messages = new ArrayList<>();
        CompletableFuture<MessageSet> messagePromise = channel.getMessages(Integer.MAX_VALUE);
        MessageSet messageSet = messagePromise.join();
        messageSet.forEach(message -> messages.add(new SerializableMessage(message)));
    }

    public Transcript(String title, TextChannel channel, List<SerializableMessage> messages, ZoneId timeZone) {
        this.channel = channel;
        this.title = title;
        this.timeZone = timeZone;
        this.messages = messages;
    }


    public String build(DiscordApi discordApi) {
        ServerChannel serverChannel = channel.asServerChannel().get();
        Server server = serverChannel.getServer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
        BaseBuilder baseBuilder = new BaseBuilder(Html.BASE());

        String serverImage;
        if (server.getIcon().isPresent()) serverImage = server.getIcon().get().getUrl().toString();
        else serverImage = FileUtils.DEFAULT_AVATAR;
        String serverName = server.getName();

        String channelCreation = serverChannel.getCreationTimestamp().atZone(timeZone).format(formatter);

        baseBuilder.setTitle(title);
        baseBuilder.setFavicon(favicon);
        baseBuilder.setServerName(serverName);
        baseBuilder.setServerId(server.getIdAsString());
        baseBuilder.setServerAvatar(serverImage);
        baseBuilder.setChannelName(serverChannel.getName());
        baseBuilder.setChannelCreation(channelCreation);
        channel.asServerTextChannel().ifPresentOrElse((serverTextChannel -> {
            baseBuilder.setChannelSubject(serverTextChannel.getTopic());
            baseBuilder.setChannelTopic(serverTextChannel.getTopic());
        }), () -> {
            baseBuilder.setChannelSubject("⠀");
            baseBuilder.setChannelTopic("⠀");
        });
        baseBuilder.setCreationTime(Instant.now(), timeZone);
        baseBuilder.setChannelId(serverChannel.getIdAsString());
        baseBuilder.setOverflow(overflow);

        if (customCss != null) baseBuilder.setCustomCss(customCss);
        if (customMedia != null) baseBuilder.setCustomMediaBlock(customMedia);
        if (customHeader != null) baseBuilder.setCustomTop(customHeader);

        StringBuilder messageContent = new StringBuilder();
        Map<User, Integer> messageUsers = new HashMap<>();

        User last = null;
        MessageGroupBuilder lastBuilder = null;
        for (SerializableMessage message : messages) {
            if (last == null || (last.getId() != message.getAuthorId())) {
                if (lastBuilder != null) messageContent.append(Html.Message.END().getContent());
                if (message.getAuthorId() == discordApi.getClientId()) last = discordApi.getYourself();
                else last = discordApi.getUserById(message.getAuthorId()).join();

                lastBuilder = new MessageGroupBuilder(Html.Message.START());
                lastBuilder.setAvatar(last.getAvatar().getUrl().toString());
                lastBuilder.setUserId(last.getId());
                lastBuilder.setMessageId(message.getMessageId());
                lastBuilder.setUserName(last.getName());
                lastBuilder.setEmbeds(message.getEmbeds());

                if (last.isBot()) {
                    if (last.getUserFlags().contains(UserFlag.VERIFIED_BOT))
                        lastBuilder.setBot(Html.Message.Bot.TAG_VERIFIED().getContent());
                    else lastBuilder.setBot(Html.Message.Bot.TAG().getContent());
                } else lastBuilder.setBot("");

                if (last.getRoleColor(server).isPresent()) lastBuilder.setUserColor(last.getRoleColor(server).get());

                lastBuilder.setContent(message.getContent());
                lastBuilder.setTimestamp(message.getCreationStamp(), timeZone);
                lastBuilder.setComponents(message.getComponents());
                lastBuilder.setAttachments(message.getAttachments());

                if (message.getLastEditStamp().isPresent())
                    lastBuilder.setEditedTimestamp(message.getLastEditStamp().get(), timeZone);

                messageContent.append(lastBuilder.build().getContent());
            } else {
                last = discordApi.getUserById(message.getAuthorId()).join();
                MessageBuilder messageBuilder = new MessageBuilder(Html.Message.MESSAGE());
                messageBuilder.setContent(message.getContent());
                messageBuilder.setMessageId(message.getMessageId());
                messageBuilder.setTimestamp(message.getCreationStamp(), timeZone);
                messageBuilder.setEmbeds(message.getEmbeds());
                messageBuilder.setComponents(message.getComponents());
                messageBuilder.setAttachments(message.getAttachments());

                if (message.getLastEditStamp().isPresent())
                    messageBuilder.setEditedTimestamp(message.getLastEditStamp().get(), timeZone);

                messageContent.append(messageBuilder.build().getContent());
            }


            if (messageUsers.containsKey(last)) messageUsers.put(last, messageUsers.get(last) + 1);
            else messageUsers.put(last, 1);
        }
        baseBuilder.setMessageCount(messages.size());
        baseBuilder.setMessageParticipants(messageUsers.size());
        baseBuilder.setMessages(messageContent.toString());

        StringBuilder metaContentBuilder = new StringBuilder();
        for (User user : messageUsers.keySet()) {
            MetaBuilder metaBuilder = new MetaBuilder(Html.Message.META());
            if (user.isBot()) {
                if (user.getUserFlags().contains(UserFlag.VERIFIED_BOT))
                    metaBuilder.setBotTag(Html.Message.Bot.TAG_VERIFIED().getContent());
                else metaBuilder.setBotTag(Html.Message.Bot.TAG().getContent());
            }
            metaBuilder.setUserId(user.getId());
            metaBuilder.setUserAvatar(user.getAvatar().getUrl().toString());
            metaBuilder.setUserDiscrim("#" + user.getDiscriminator());
            metaBuilder.setUserName(user.getName());
            metaBuilder.setUserCreation(TimeUtils.getFormattedTime(user.getCreationTimestamp(), timeZone));
            if (server.getJoinedAtTimestamp(user).isPresent())
                metaBuilder.setUserJoin(TimeUtils.getFormattedTime(server.getJoinedAtTimestamp(user).get(), timeZone));
            else metaBuilder.setUserJoin("");
            metaBuilder.setServerAvatar(serverImage);
            metaBuilder.setMessageCount(messageUsers.get(user));
            metaContentBuilder.append(metaBuilder.build().getContent());
        }

        baseBuilder.setMetaData(metaContentBuilder.toString());

        String content = baseBuilder.build().getContent();
        Pattern pattern = Pattern.compile("&lt;:.*?:(\\d*)&gt;");
        HashMap<Pattern, String> patterns = new HashMap<>();
        patterns.put(pattern, "<img class=\"emoji emoji--small\" src=\"https://cdn.discordapp.com/emojis/%s.png\">");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            content = matcher.replaceAll((matchResult -> {
                String emojiId = matchResult.group(1);
                return String.format(patterns.get(pattern), emojiId);
            }));
        }
        return content;
    }
}
