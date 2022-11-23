package org.swordofsouls.discord.chatexporter.Html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlReader {
    public static String read(String fileName) {
        InputStream stream =
                ClassLoader.getSystemResourceAsStream("Html/"+ fileName + ".html");
        String output = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public static enum Attachment {

    }
}
