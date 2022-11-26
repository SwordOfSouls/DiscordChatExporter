package org.swordofsouls.discord.chatexporter.Utils.Color;

import org.javacord.api.entity.message.component.ButtonStyle;

import java.awt.*;

public class ButtonStyleUtils {
    public static Color buttonStyle(ButtonStyle style, boolean disabled) {
        Color color = null;
        if(!disabled) {
            switch (style) {
                case PRIMARY:
                    color = new Color(88, 101, 242);
                case SUCCESS:
                    color = new Color(67, 181, 129);
                case SECONDARY:
                    color = new Color(79, 84, 92);
                case DANGER:
                    color = new Color(240, 71, 71);
                case LINK:
                    color = new Color(79, 84, 92);
                case UNKNOWN:
                    color = new Color(240, 71, 71);
            }
        } else {
            switch (style) {
                case PRIMARY:
                    color = new Color(61, 66, 99);
                case SUCCESS:
                    color = new Color(57, 82, 76);
                case SECONDARY:
                    color = new Color(59, 62, 69);
                case DANGER:
                    color = new Color(91, 60, 65);
                case LINK:
                    color = new Color(79, 84, 92);
                case UNKNOWN:
                    color = new Color(91, 60, 65);
            }
        }
        return color;
    }
}
