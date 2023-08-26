package org.swordofsouls.discord.chatexporter.Html.Builders;

import org.swordofsouls.discord.chatexporter.Html.HtmlFile;
import org.swordofsouls.discord.chatexporter.Utils.File.FileUtils;

public class MetaBuilder extends HtmlBase {
    public MetaBuilder(HtmlFile file) {
        super(file);
    }

    public void setUserId(Long userId) {
        file.replace("USER_ID", userId.toString());
        file.replace("MEMBER_ID", userId.toString());
    }

    public void setUserAvatar(String userAvatar) {
        file.replace("USER_AVATAR", userAvatar);
    }

    public void setUserName(String userName) {
        file.replace("USERNAME", userName);
    }

    public void setUserDiscrim(String userDiscrim) {
        file.replace("DISCRIMINATOR", userDiscrim);
    }

    public void setBotTag(String botTag) {
        file.replace("BOT", botTag);
    }

    public void setUserCreation(String userCreation) {
        file.replace("CREATED_AT", userCreation);
    }

    public void setUserJoin(String userJoin) {
        file.replace("JOINED_AT", userJoin);
    }

    public void setServerAvatar(String serverAvatar) {
        file.replace("GUILD_ICON", serverAvatar);
    }

    public void setMessageCount(Integer messageCount) {
        file.replace("MESSAGE_COUNT", messageCount.toString());
    }

    @Override
    public HtmlFile build() {
        file.replace("BOT", "");
        file.replace("DISCORD_ICON", FileUtils.LOGO);
        file.replace("DISPLAY", "");
        return getFile();
    }
}
