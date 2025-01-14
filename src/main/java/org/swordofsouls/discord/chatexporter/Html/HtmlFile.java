package org.swordofsouls.discord.chatexporter.Html;

import lombok.Getter;

import java.util.regex.Matcher;

@Getter
public final class HtmlFile {
    private String content;

    public HtmlFile(String name) {
        this.content = HtmlReader.read(name);
    }

    public HtmlFile replace(String key, String value) {
        content = content.replaceAll("\\{\\{" + Matcher.quoteReplacement(key.toUpperCase()) + "}}", Matcher.quoteReplacement(value));
        return this;
    }
}
