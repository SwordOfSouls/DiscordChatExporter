package org.swordofsouls.discord.chatexporter.Html.Builders.Message;

import org.swordofsouls.discord.chatexporter.Html.Builders.HtmlBase;
import org.swordofsouls.discord.chatexporter.Html.HtmlFile;
import org.swordofsouls.discord.chatexporter.Utils.File.FileUtils;

public class PinMessageBuilder extends HtmlBase {
    public PinMessageBuilder(HtmlFile file) {
        super(file);
        file.replace("PIN_URL", FileUtils.PINNED_MESSAGE_ICON);
    }

    @Override
    public HtmlFile build() {
        return getFile();
    }

    public void setMessageId(Long messageId) {
        file.replace("MESSAGE_ID", messageId.toString());
    }

    public void setRefMessageId(Long refMessageId) {
        file.replace("REF_MESSAGE_ID", refMessageId.toString());
    }

    public void setName(String name) {
        file.replace("NAME_TAG", name);
        file.replace("NAME", name);
    }
}
