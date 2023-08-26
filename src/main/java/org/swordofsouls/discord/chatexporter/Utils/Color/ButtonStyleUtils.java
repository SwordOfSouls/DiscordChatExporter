package org.swordofsouls.discord.chatexporter.Utils.Color;

import org.javacord.api.entity.message.component.ButtonStyle;

import java.awt.*;

public class ButtonStyleUtils {
    public static Color buttonStyle(ButtonStyle style, boolean disabled) {
        Color color = null;
        if (!disabled) {
            switch (style) {
                case PRIMARY:
                    color = new Color(88, 101, 242);
                    break;
                case SUCCESS:
                    color = new Color(67, 181, 129);
                    break;
                case SECONDARY:
                    color = new Color(79, 84, 92);
                    break;
                case DANGER:
                    color = new Color(240, 71, 71);
                    break;
                case LINK:
                    color = new Color(79, 84, 92);
                    break;
                case UNKNOWN:
                    color = new Color(240, 71, 71);
                    break;
            }
        } else {
            switch (style) {
                case PRIMARY:
                    color = new Color(61, 66, 99);
                    break;
                case SUCCESS:
                    color = new Color(57, 82, 76);
                    break;
                case SECONDARY:
                    color = new Color(59, 62, 69);
                    break;
                case DANGER:
                    color = new Color(91, 60, 65);
                    break;
                case LINK:
                    color = new Color(79, 84, 92);
                    break;
                case UNKNOWN:
                    color = new Color(91, 60, 65);
                    break;
            }
        }
        return color;
    }
}
