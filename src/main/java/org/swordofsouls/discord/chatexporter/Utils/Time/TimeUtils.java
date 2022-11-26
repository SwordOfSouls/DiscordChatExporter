package org.swordofsouls.discord.chatexporter.Utils.Time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeUtils {
    public static String getFullFormattedTime(Instant instant, ZoneId zoneId) {
        return
                instant.atZone(zoneId).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US) + ", " +
                instant.atZone(zoneId).getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " +
                mapInt(instant.atZone(zoneId).getDayOfMonth(), 2) + ", " + instant.atZone(zoneId).getYear() +
                instant.atZone(zoneId).format(DateTimeFormatter.ofPattern(" hh:mm a"));
    }
    public static String getFormattedTime(Instant instant, ZoneId zoneId) {
        return
                instant.atZone(zoneId).getMonth().getDisplayName(TextStyle.SHORT, Locale.US) + " " +
                mapInt(instant.atZone(zoneId).getDayOfMonth(), 2) + ", " + instant.atZone(zoneId).getYear();
    }
    private static String mapInt(Integer value, Integer digits) {
        StringBuilder intBuilder = new StringBuilder();
        for (int i = 0; i < (digits - value.toString().length()); i++) intBuilder.append("0");
        intBuilder.append(value.toString());
        return intBuilder.toString();
    }
}
