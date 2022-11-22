package org.swordofsouls.discord.chatexporter.Html.Builders;

import org.swordofsouls.discord.chatexporter.Html.HtmlFile;

public class Base extends HtmlBase {
    public Base(HtmlFile file) {
        super(file);
    }

    public Base setServerName(String name) {
        file.replace("SERVER_NAME", name);
        return this;
    }
    public Base setServerId(String serverId) {
        file.replace("GUILD_ID", serverId);
        return this;
    }
    public Base setServerAvatar(String url) {
        file.replace("SERVER_AVATAR_URL", url);
        return this;
    }
    public Base setChannelName(String channelName) {
        file.replace("CHANNEL_NAME", channelName);
        return this;
    }
    public Base setChannelCreation(String channelCreation) {
        file.replace("CHANNEL_CREATED_AT", channelCreation);
        return this;
    }
    public Base setChannelTopic(String channelTopic) {
        file.replace("CHANNEL_TOPIC", channelTopic);
        return this;
    }
    public Base setChannelId(String channelId) {
        file.replace("CHANNEL_ID", channelId);
        return this;
    }
}
