package org.swordofsouls.discord.chatexporter.Html.Builders.Message;

import org.swordofsouls.discord.chatexporter.Html.HtmlFile;
import org.swordofsouls.discord.chatexporter.Utils.Time.TimeUtils;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MessageGroupBuilder extends MessageCore {
    public MessageGroupBuilder(HtmlFile file) {
        super(file);
    }

    public void setAvatar(String url) {
        file.replace("AVATAR_URL", url);
    }
    public void setUserId(Long userId) {
        file.replace("USER_ID", userId.toString());
    }
    public void setUserColor(Color color) {
        file.replace("USER_COLOUR", "color: " +String.format("#%02x%02x%02x", color.getRed(), color.getGreen(),color.getBlue()));
    }
    public void setUserName(String name) {
        file.replace("NAME_TAG", name);
        file.replace("NAME", name);
    }
    public void setMessageIcon(String messageIcon) {
        file.replace("ICON", messageIcon);
    }
    public void setBot(String botTag) {
        file.replace("BOT_TAG", botTag);
    }
    public void setTimestamp(Instant instant, ZoneId timeZone) {
        file.replace("TIME", instant.atZone(timeZone).format(DateTimeFormatter.ofPattern("HH:mm a")));
        file.replace("TIMESTAMP", TimeUtils.getFullFormattedTime(instant, timeZone));
        file.replace("DEFAULT_TIMESTAMP",
                instant.atZone(timeZone).format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a")));
    }

    @Override
    public HtmlFile build() {
        file.replace("USER_ICON","");
        file.replace("REFERENCE","");
        file.replace("REFERENCE_SYMBOL","");
        return getFile();
    }
}
