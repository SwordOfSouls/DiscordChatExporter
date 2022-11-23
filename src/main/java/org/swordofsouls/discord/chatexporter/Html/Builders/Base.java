package org.swordofsouls.discord.chatexporter.Html.Builders;

import org.swordofsouls.discord.chatexporter.Html.HtmlFile;

public class Base extends HtmlBase {
    public Base(HtmlFile file) {
        super(file);
    }

    public void setServerName(String name) {
        file.replace("SERVER_NAME", name);
    }
    public void setServerId(String serverId) {
        file.replace("GUILD_ID", serverId);
    }
    public void setServerAvatar(String url) {
        file.replace("SERVER_AVATAR_URL", url);
    }
    public void setChannelName(String channelName) {
        file.replace("CHANNEL_NAME", channelName);
    }
    public void setChannelCreation(String channelCreation) {
        file.replace("CHANNEL_CREATED_AT", channelCreation);
    }
    public void setChannelTopic(String channelTopic) {
        file.replace("CHANNEL_TOPIC", channelTopic);
    }
    public void setChannelId(String channelId) {
        file.replace("CHANNEL_ID", channelId);
    }
}
