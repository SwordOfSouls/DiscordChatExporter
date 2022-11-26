package org.swordofsouls.discord.chatexporter.Html.Builders.Message;

import org.swordofsouls.discord.chatexporter.Html.HtmlFile;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MessageBuilder extends MessageCore {
    public MessageBuilder(HtmlFile file) {
        super(file);
    }

    public void setTimestamp(Instant instant, ZoneId timeZone) {
        file.replace("TIME", instant.atZone(timeZone).format(DateTimeFormatter.ofPattern("hh:mm a")));
        file.replace("TIMESTAMP", instant.atZone(timeZone).format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }

}
