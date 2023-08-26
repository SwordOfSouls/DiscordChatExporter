package org.swordofsouls.discord.chatexporter.Serializable;

import lombok.Getter;

import java.time.Instant;

@Getter
public class SerializableInstant {
    private String store;

    public SerializableInstant(Instant instant) {
        store = instant.toString();
    }

    public Instant back() {
        return  Instant.parse(store);
    }
}
