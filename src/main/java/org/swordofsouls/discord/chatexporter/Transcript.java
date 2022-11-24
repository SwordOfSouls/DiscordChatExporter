package org.swordofsouls.discord.chatexporter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserFlag;
import org.swordofsouls.discord.chatexporter.Html.Builders.Message.MessageBuilder;
import org.swordofsouls.discord.chatexporter.Html.Builders.Message.MessageGroupBuilder;
import org.swordofsouls.discord.chatexporter.Html.Builders.MetaBuilder;
import org.swordofsouls.discord.chatexporter.Html.Html;
import org.swordofsouls.discord.chatexporter.Html.Builders.BaseBuilder;
import org.swordofsouls.discord.chatexporter.Utils.File.FileUtils;
import org.swordofsouls.discord.chatexporter.Utils.Time.TimeUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@AllArgsConstructor
public class Transcript {
    public final TextChannel channel;
    public final ZoneId timeZone;

    public String buildTranscript() {
        ServerChannel serverChannel = channel.asServerChannel().get();
        ServerTextChannel serverTextChannel = serverChannel.asServerTextChannel().get();
        Server server = serverChannel.getServer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
        BaseBuilder baseBuilder = new BaseBuilder(Html.BASE());

        String serverImage;
        if(server.getIcon().isPresent()) serverImage = server.getIcon().get().getUrl().toString();
        else serverImage = FileUtils.DEFAULT_AVATAR;
        String serverName = server.getName();
        String time = LocalDateTime.now().atZone(timeZone).format(formatter);

        String channelCreation = serverChannel.getCreationTimestamp().atZone(timeZone).format(formatter);
        String channelTopic = serverTextChannel.getTopic();

        baseBuilder.setServerName(serverName);
        baseBuilder.setServerId(server.getIdAsString());
        baseBuilder.setServerAvatar(serverImage);
        baseBuilder.setChannelName(serverTextChannel.getName());
        baseBuilder.setChannelCreation(channelCreation);
        baseBuilder.setChannelSubject(channelTopic);
        baseBuilder.setChannelTopic(channelTopic);
        baseBuilder.setChannelId(serverTextChannel.getIdAsString());


        StringBuilder messageContent = new StringBuilder();
        CompletableFuture<MessageSet> messagePromise = serverTextChannel.getMessages(Integer.MAX_VALUE);
        MessageSet messageSet = messagePromise.join();
        List<Message> messageList = new ArrayList<>(messageSet);
        Map<User, Integer> messageUsers = new HashMap<>();

        User last = null;
        MessageGroupBuilder lastBuilder = null;
        for (Message message : messageList) {
            if(last == null || (last.getId() != message.getUserAuthor().get().getId())) {
                if(lastBuilder!=null) messageContent.append(Html.Message.END().getContent());
                last = message.getUserAuthor().get();

                lastBuilder = new MessageGroupBuilder(Html.Message.START());
                lastBuilder.setAvatar(last.getAvatar().getUrl().toString());
                lastBuilder.setUserId(last.getId());
                lastBuilder.setMessageId(message.getId());
                lastBuilder.setUserName(last.getName());
                lastBuilder.setEmbeds(message.getEmbeds());

                if(last.isBot()) {
                    if(last.getUserFlags().contains(UserFlag.VERIFIED_BOT)) lastBuilder.setBot(Html.Message.Bot.TAG_VERIFIED().getContent());
                    else lastBuilder.setBot(Html.Message.Bot.TAG().getContent());
                }
                else lastBuilder.setBot("");

                if(last.getRoleColor(server).isPresent()) lastBuilder.setUserColor(last.getRoleColor(server).get());

                lastBuilder.setContent(message.getContent());
                lastBuilder.setTimestamp(message.getCreationTimestamp(), timeZone);
                messageContent.append(lastBuilder.build().getContent());
            } else {
                last = message.getUserAuthor().get();
                MessageBuilder messageBuilder = new MessageBuilder(Html.Message.MESSAGE());

                messageBuilder.setContent(message.getContent());
                messageBuilder.setMessageId(message.getId());
                messageBuilder.setTimestamp(message.getCreationTimestamp(), timeZone);
                messageBuilder.setEmbeds(message.getEmbeds());
                messageContent.append(messageBuilder.getFile().getContent());
            }

            if(messageUsers.containsKey(last)) messageUsers.put(last, messageUsers.get(last)+1);
            else messageUsers.put(last, 1);
        }
        baseBuilder.setMessageCount(messageList.size());
        baseBuilder.setMessageParticipants(messageUsers.size());
        baseBuilder.setMessages(messageContent.toString());

        StringBuilder metaContentBuilder = new StringBuilder();
        for(User user : messageUsers.keySet()) {
            MetaBuilder metaBuilder = new MetaBuilder(Html.Message.META());
            if(user.isBot()) {
                if(user.getUserFlags().contains(UserFlag.VERIFIED_BOT)) metaBuilder.setBotTag(Html.Message.Bot.TAG_VERIFIED().getContent());
                else metaBuilder.setBotTag(Html.Message.Bot.TAG().getContent());
            }
            metaBuilder.setUserId(user.getId());
            metaBuilder.setUserAvatar(user.getAvatar().getUrl().toString());
            metaBuilder.setUserDiscrim("#" + user.getDiscriminator());
            metaBuilder.setUserName(user.getName());
            metaBuilder.setUserCreation(TimeUtils.getFormattedTime(user.getCreationTimestamp(), timeZone));
            if(server.getJoinedAtTimestamp(user).isPresent())
                metaBuilder.setUserJoin(TimeUtils.getFormattedTime(server.getJoinedAtTimestamp(user).get(), timeZone));
            else metaBuilder.setUserJoin("");
            metaBuilder.setServerAvatar(serverImage);
            metaBuilder.setMessageCount(messageUsers.get(user));
            metaContentBuilder.append(metaBuilder.build().getContent());
        }

        baseBuilder.setMetaData(metaContentBuilder.toString());

        return baseBuilder.build().getContent();
    }
}
