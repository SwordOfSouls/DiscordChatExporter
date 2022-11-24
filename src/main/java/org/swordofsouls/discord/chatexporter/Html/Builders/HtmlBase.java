package org.swordofsouls.discord.chatexporter.Html.Builders;

import org.swordofsouls.discord.chatexporter.Html.HtmlFile;

public abstract class HtmlBase {
    protected final HtmlFile file;

    public HtmlBase(HtmlFile file) {
        this.file = file;
    }

    public HtmlFile getFile() {
        return file;
    }
    public abstract HtmlFile build();
}
