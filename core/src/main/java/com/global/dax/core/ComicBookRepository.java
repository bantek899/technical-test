package com.global.dax.core;

import com.global.dax.core.model.ComicBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class ComicBookRepository {

    private final List<ComicBook> comicBooks;

    public ComicBookRepository() {

        this.comicBooks = new ArrayList<>();
    }

    public ComicBookRepository(final List<ComicBook> comicBooks) {

        this.comicBooks = comicBooks;
    }

    public void add(final ComicBook comicBook) {
        if (!comicBooks.contains(comicBook))
            comicBooks.add(comicBook);
    }

    public List<ComicBook> getComicBooks() {

        return comicBooks;
    }

    public void clear() {

        comicBooks.clear();
    }

    public List<ComicBook> searchBySeries(final String series) {
        return comicBooks.stream().filter(comicBook -> comicBook.series().equals(series)).toList();
    }

    public List<ComicBook> search(final Predicate<ComicBook> predicate) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        return comicBooks.stream().filter(predicate).toList();
    }

    public List<ComicBook> searchByCharacters(final Set<String> characters) {
        Predicate<ComicBook> predicate = comic -> comic.characters().stream().anyMatch(characters::contains);
        return comicBooks.stream().filter(predicate).toList();
    }

    public boolean remove(final ComicBook comicBook) {
        return comicBooks.remove(comicBook);
    }
}
