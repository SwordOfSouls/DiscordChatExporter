package org.swordofsouls.discord.chatexporter.Html.Builders;

import org.swordofsouls.discord.chatexporter.Html.Html;
import org.swordofsouls.discord.chatexporter.Html.HtmlFile;
import org.swordofsouls.discord.chatexporter.Utils.Time.TimeUtils;

import java.time.Instant;
import java.time.ZoneId;

public class BaseBuilder extends HtmlBase {
    public BaseBuilder(HtmlFile file) {
        super(file);
    }

    @Override
    public HtmlFile build() {
        file.replace("SD", "");
        file.replace("CSS", "");
        file.replace("MEDIA_BLOCK", "");
        file.replace("INSERT_TOP", "");
        file.replace("INSERT_PANEL", "");
        file.replace("TITLE", "");
        file.replace("FAVICON", "");
        return getFile();
    }

    public void setTitle(String name) {
        file.replace("TITLE", name);
    }

    public void setFavicon(String favicon) {
        file.replace("FAVICON", favicon);
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
        file.replace("CHANNEL_TOPIC", Html.Script.CHANNEL_TOPIC().replace("CHANNEL_TOPIC", channelTopic).getContent());
    }

    public void setChannelSubject(String channelSubject) {
        file.replace("SUBJECT", Html.Script.CHANNEL_SUBJECT().replace("RAW_CHANNEL_TOPIC", channelSubject).getContent());
    }

    public void setChannelId(String channelId) {
        file.replace("CHANNEL_ID", channelId);
    }

    public void setMessageCount(int messageCount) {
        file.replace("MESSAGE_COUNT", String.valueOf(messageCount));
    }

    public void setMessageParticipants(int messageParticipants) {
        file.replace("MESSAGE_PARTICIPANTS", String.valueOf(messageParticipants));
    }

    public void setMessages(String messages) {
        file.replace("MESSAGES", messages);
    }

    public void setMetaData(String metaData) {
        file.replace("META_DATA", metaData);
    }

    public void setCustomCss(String css) {
        file.replace("CSS", css);
    }

    public void setCustomMediaBlock(String mediaBlock) {
        file.replace("MEDIA_BLOCK", mediaBlock);
    }

    public void setCustomTop(String htmlTop) {
        file.replace("INSERT_TOP", htmlTop);
    }

    public void setCustomMiddle(String htmlMiddle) {
        file.replace("INSERT_PANEL", htmlMiddle);
    }

    public void setCreationTime(Instant instant, ZoneId zone) {
        file.replace("DATE_TIME", TimeUtils.getFullFormattedTime(instant, zone));
    }

    public void setOverflow(Integer overflow) {
        file.replace("OVERFLOW", String.valueOf(overflow));
    }

}
