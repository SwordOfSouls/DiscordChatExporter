package org.swordofsouls.discord.chatexporter.Html.Builders.Components;

import org.swordofsouls.discord.chatexporter.Html.Builders.HtmlBase;
import org.swordofsouls.discord.chatexporter.Html.HtmlFile;

import java.awt.*;

public class ButtonBuilder extends HtmlBase {
    public ButtonBuilder(HtmlFile file) {
        super(file);
    }

    public void setColor(Color color) {
        file.replace("STYLE", String.format("#%02x%02x%02x", color.getRed(), color.getGreen(),color.getBlue()));
    }
    public void setDisabled(Boolean value) {
        if(value) file.replace("DISABLED", "chatlog__component-disabled");
    }
    public void setUrl(String url) {
        file.replace("URL", url);
    }
    public void setEmoji(String emoji) {
        file.replace("EMOJI", emoji);
    }
    public void setLabel(String label) {
        file.replace("LABEL", label);
    }
    public void setIcon(String icon) {
        file.replace("ICON", icon);
    }

    @Override
    public HtmlFile build() {
        file.replace("DISABLED","");
        file.replace("URL","");
        file.replace("ICON","");
        file.replace("LABEL","");
        file.replace("EMOJI","");
        return getFile();
    }
}
