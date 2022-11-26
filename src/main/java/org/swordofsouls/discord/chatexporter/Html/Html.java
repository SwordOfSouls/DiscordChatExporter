package org.swordofsouls.discord.chatexporter.Html;

import org.swordofsouls.discord.chatexporter.Html.Builders.BaseBuilder;

public class Html {
    public static final class Attachment {
        public static final String ROOT = "Attachment/";

        public static HtmlFile AUDIO() { return new HtmlFile(ROOT +"audio"); }
        public static HtmlFile IMAGE() { return new HtmlFile(ROOT +"image"); }
        public static HtmlFile MESSAGE() { return new HtmlFile(ROOT +"message"); }
        public static HtmlFile VIDEO() { return new HtmlFile(ROOT +"video"); }
    }
    public static final class Component {
        public static final String ROOT = "Component/";

        public static final class Menu {
            public static final String ROOT = "Component/Menu/";

            public static HtmlFile MENU() { return new HtmlFile(ROOT +"menu"); }
            public static HtmlFile OPTIONS() { return new HtmlFile(ROOT +"options"); }
            public static HtmlFile EMOJI() { return new HtmlFile(ROOT +"emoji"); }
        }
        public static HtmlFile BUTTON() { return new HtmlFile(ROOT +"button"); }
    }
    public static final class Embed {
        public static final String ROOT = "Embed/";

        public static HtmlFile AUTHOR() { return new HtmlFile(ROOT +"author"); }
        public static HtmlFile AUTHOR_ICON() { return new HtmlFile(ROOT +"author_icon"); }
        public static HtmlFile BODY() { return new HtmlFile(ROOT +"body"); }
        public static HtmlFile DESCRIPTION() { return new HtmlFile(ROOT +"description"); }
        public static HtmlFile FIELD() { return new HtmlFile(ROOT +"field"); }
        public static HtmlFile FIELD_INLINE() { return new HtmlFile(ROOT +"field_inline"); }
        public static HtmlFile FOOTER() { return new HtmlFile(ROOT +"footer"); }
        public static HtmlFile FOOTER_IMAGE() { return new HtmlFile(ROOT +"footer_image"); }
        public static HtmlFile IMAGE() { return new HtmlFile(ROOT +"image"); }
        public static HtmlFile THUMBNAIL() { return new HtmlFile(ROOT +"thumbnail"); }
        public static HtmlFile TITLE() { return new HtmlFile(ROOT +"title"); }
    }
    public static final class Message {
        public static final String ROOT = "Message/";

        public static final class Bot {
            public static final String ROOT = "Message/Bot/";

            public static HtmlFile TAG() { return new HtmlFile(ROOT +"tag"); }
            public static HtmlFile TAG_VERIFIED() { return new HtmlFile(ROOT +"tag_verified"); }
        }
        public static HtmlFile CONTENT() { return new HtmlFile(ROOT +"content"); }
        public static HtmlFile END() { return new HtmlFile(ROOT +"end"); }
        public static HtmlFile INTERACTION() { return new HtmlFile(ROOT +"interaction"); }
        public static HtmlFile MESSAGE() { return new HtmlFile(ROOT +"message"); }
        public static HtmlFile META() { return new HtmlFile(ROOT +"meta"); }
        public static HtmlFile PIN() { return new HtmlFile(ROOT +"pin"); }
        public static HtmlFile REFERENCE() { return new HtmlFile(ROOT +"reference"); }
        public static HtmlFile REFERENCE_UNKNOWN() { return new HtmlFile(ROOT +"reference_unknown"); }
        public static HtmlFile START() { return new HtmlFile(ROOT +"start"); }
        public static HtmlFile THREAD() { return new HtmlFile(ROOT +"thread"); }
        public static HtmlFile EDITED() { return new HtmlFile(ROOT +"edited"); }
    }
    public static final class Reaction {
        public static final String ROOT = "Reaction/";

        public static HtmlFile EMOJI_CUSTOM() { return new HtmlFile(ROOT +"emoji_custom"); }
        public static HtmlFile EMOJI() { return new HtmlFile(ROOT +"emoji"); }
    }
    public static final class Script {
        public static final String ROOT = "Script/";

        public static HtmlFile CHANNEL_SUBJECT() { return new HtmlFile(ROOT +"channel_subject"); }
        public static HtmlFile CHANNEL_TOPIC() { return new HtmlFile(ROOT +"channel_topic"); }
        public static HtmlFile FANCY_TIME() { return new HtmlFile(ROOT +"fancy_time"); }
    }

    public static HtmlFile BASE() { return new HtmlFile("base"); }
}
