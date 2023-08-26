package org.swordofsouls.discord.chatexporter.Serializable;

import java.util.NoSuchElementException;
import java.util.Optional;

public class SerializableOptional<T> {
    private final T value;

    public SerializableOptional() {
        this.value = null;
    }
    public SerializableOptional(T value) {
        this.value = value;
    }
    public SerializableOptional(Optional<T> value) {
        this.value = value.orElse(null);
    }

    public boolean isPresent() {
        return value != null;
    }

    public static <T> SerializableOptional<T> empty() {
        return new SerializableOptional<>();
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }
}
