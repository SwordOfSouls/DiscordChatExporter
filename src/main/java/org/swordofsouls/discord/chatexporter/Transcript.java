package org.swordofsouls.discord.chatexporter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.swordofsouls.discord.chatexporter.Html.Html;
import org.swordofsouls.discord.chatexporter.Html.Builders.Base;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        Base base = Html.BASE();

        String serverImage = server.getIcon().get().getUrl().toString();
        String serverName = server.getName();
        String time = LocalDateTime.now().atZone(timeZone).format(formatter);

        String channelCreation = serverChannel.getCreationTimestamp().atZone(timeZone).format(formatter);
        String channelTopic = serverChannel.asServerTextChannel().get().getTopic();

        base.setServerName(serverName);
        base.setServerId(server.getIdAsString());
        base.setServerAvatar(serverImage);
        base.setChannelName(serverTextChannel.getName());
        base.setChannelCreation(channelCreation);
        base.setChannelTopic(channelTopic);
        base.setChannelId(serverTextChannel.getIdAsString());
        return base.getFile().getContent();
    }
}
