package org.swordofsouls.discord.chatexporter.Utils.File;

import java.util.List;

public class FileUtils {
    public static String LOGO = "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-logo.svg";
    public static String DEFAULT_AVATAR = "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-default.png";
    public static String PINNED_MESSAGE_ICON = "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-pinned.svg";
    public static String THREAD_CHANNEL_ICON = "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord" +
            "-thread" +
            ".svg";
    public static String FILE_ATTACHMENT_AUDIO = "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master" +
            "/discord-audio.svg";
    public static String FILE_ATTACHMENT_ACROBAT =
            "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-acrobat.svg";
    public static String FILE_ATTACHMENT_WEBCODE =
            "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-webcode.svg";
    public static String FILE_ATTACHMENT_CODE =
            "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-code.svg";
    public static String FILE_ATTACHMENT_DOCUMENT =
            "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-document.svg";
    public static String FILE_ATTACHMENT_ARCHIVE =
            "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-archive.svg";
    public static String FILE_ATTACHMENT_UNKNOWN =
            "https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-unknown.svg";
    public static String BUTTON_EXTERNAL_LINK =
            "<img class=chatlog__reference-icon src=https://cdn.jsdelivr" +
                    ".net/gh/mahtoid/DiscordUtils@master/discord-external-link.svg>";
    public static String REFERENCE_ATTACHMENT_ICON =
            "<img class=chatlog__reference-icon src=https://cdn.jsdelivr" +
                    ".net/gh/mahtoid/DiscordUtils@master/discord-attachment.svg>";
    public static String INTERACTION_COMMAND_ICON =
            "<img class=chatlog__interaction-icon src=https://cdn.jsdelivr" +
                    ".net/gh/mahtoid/DiscordUtils@master/discord-command.svg>";
    public static String INTERACTION_DROPDOWN_ICON =
            "<img class=chatlog__dropdown-icon src=https://cdn.jsdelivr" +
                    ".net/gh/mahtoid/DiscordUtils@master/discord-dropdown.svg>";



    public static List<String> ACROBAT_TYPES = List.of("pdf");
    public static List<String> WEBCODE_TYPES = List.of("html", "htm", "css", "rss", "xhtml", "xml");
    public static List<String> CODE_TYPES = List.of("py", "cgi", "pl", "gadget", "jar", "msi", "wsf", "bat","php","js");
    public static List<String> IMAGE_TYPES = List.of("jpeg", "jpg", "png", "gif", "tiff", "raw");
    public static List<String> VIDEO_TYPES = List.of("mp4", "mov", "wmv", "avi", "mkv", "raw", "webm");
    public static List<String> DOCUMENT_TYPES = List.of("txt", "doc", "docx", "rtf", "xls", "xlsx", "ppt","pptx",
            "odt","odp", "ods", "odg", "odf", "swx","sxi", "sxc", "sxd", "stw");
    public static List<String> ARCHIVE_TYPES = List.of("br", "rpm", "dcm", "epub", "zip", "tar", "rar", "gz", "bz2", "7x", "deb", "ar", "Z", "lzo", "lz", "lz4",
            "arj", "pkg", "z");
    public static List<String> AUDIO_TYPES = List.of("3gp","aa","aac","aax","act","aiff","alac","amr","ape","au","awb","dss","dvf","flac","gsm","iklax",
            "ivs", "m4a","m4b", "m4p","mmf","mp3","mpc","msv","nmf","ogg","oga","mogg","opus","ra","rm","rf64",
            "webm","cda");

    public static String getIcon(String fileName) {
        String extension = fileName.split("\\.", 2)[1];
        if(ACROBAT_TYPES.contains(extension)) return FILE_ATTACHMENT_ACROBAT;
        if(WEBCODE_TYPES.contains(extension)) return FILE_ATTACHMENT_WEBCODE;
        if(CODE_TYPES.contains(extension)) return FILE_ATTACHMENT_CODE;
        if(DOCUMENT_TYPES.contains(extension)) return FILE_ATTACHMENT_DOCUMENT;
        if(ARCHIVE_TYPES.contains(extension)) return FILE_ATTACHMENT_ARCHIVE;
        if(AUDIO_TYPES.contains(extension)) return FILE_ATTACHMENT_AUDIO;
        return FILE_ATTACHMENT_UNKNOWN;
    }
}
