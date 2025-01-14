package org.swordofsouls.discord.chatexporter.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class HtmlReader {
    public static String read(String fileName) {
        InputStream stream =
                ClassLoader.getSystemResourceAsStream("Html/" + fileName + ".html");
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
