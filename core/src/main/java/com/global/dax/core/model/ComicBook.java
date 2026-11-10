package com.global.dax.core.model;

import java.util.Set;

public record ComicBook(String series, int volume, int issue, Set<String> characters) {
    public ComicBook {
        characters = characters == null ? Set.of() : Set.copyOf(characters); // create immutable copy of Set passed as argument
    }

    public ComicBook(String series, int volume, int issue) {
        this(series, volume, issue, null);
    }
}

