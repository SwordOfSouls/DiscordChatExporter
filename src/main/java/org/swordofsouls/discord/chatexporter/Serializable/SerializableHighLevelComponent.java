package org.swordofsouls.discord.chatexporter.Serializable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.core.entity.message.component.ButtonImpl;
import org.javacord.core.entity.message.component.SelectMenuImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SerializableHighLevelComponent {
    private List<String> serializedComponents = new ArrayList<>();

    public SerializableHighLevelComponent(HighLevelComponent highLevelComponent) {
        highLevelComponent.asActionRow().get().getComponents().forEach(lowLevelComponent -> {
            JsonNode node = null;
            try {
                Method method = lowLevelComponent.getClass().getDeclaredMethod("toJsonNode");
                method.setAccessible(true);
                node = (JsonNode) method.invoke(lowLevelComponent);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            serializedComponents.add(node.toString());
        });
    }

    public List<LowLevelComponent> convert() {
        List<LowLevelComponent> components = new ArrayList<>();
        serializedComponents.forEach(serializedJsonNode -> {
            JsonNode jsonNode = null;
            try {
                jsonNode = new ObjectMapper().readTree(serializedJsonNode);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            ComponentType componentType = ComponentType.fromId(jsonNode.get("type").asInt());
            switch (componentType) {
                case BUTTON: {
                    components.add(new ButtonImpl(jsonNode));
                    break;
                }
                case SELECT_MENU_CHANNEL:
                case SELECT_MENU_MENTIONABLE:
                case SELECT_MENU_ROLE:
                case SELECT_MENU_STRING:
                case SELECT_MENU_USER:
                {
                    components.add(new SelectMenuImpl(jsonNode));
                    break;
                }
            }
        });
        return components;
    }


}
