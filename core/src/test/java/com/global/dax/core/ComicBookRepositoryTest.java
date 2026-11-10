package com.global.dax.core;

import com.global.dax.core.model.ComicBook;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import static com.global.dax.testing.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class ComicBookRepositoryTest {

    @Test
    void addStoresComicBookToRepository() {

        final var comicBooks = new ArrayList<ComicBook>();

        final var underTest = new ComicBookRepository(comicBooks);

        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);

        assertThat(comicBooks).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1, GREEN_LANTERN_V4_2);
    }

    @Test
    void addDoesNotStoreDuplicatedComicBookIfComicBookIsAddedTwice() {

        final var comicBooks = new ArrayList<ComicBook>();

        final var underTest = new ComicBookRepository(comicBooks);

        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_1);

        assertThat(comicBooks).containsOnlyOnce(GREEN_LANTERN_V4_1);
    }

    @Test
    void getComicBooksReturnsAllStoredComicBooks() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1);
        underTest.add(BATMAN_V2_2);
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);
        underTest.add(GREEN_LANTERN_V5_1);
        underTest.add(GREEN_LANTERN_V5_2);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(SUPERMAN_V4_2);

        final var foundComicBooks = underTest.getComicBooks();

        assertThat(foundComicBooks).containsExactlyInAnyOrder(BATMAN_V2_1, BATMAN_V2_2, GREEN_LANTERN_V4_1, GREEN_LANTERN_V4_2, GREEN_LANTERN_V5_1, GREEN_LANTERN_V5_2, SUPERMAN_V4_1, SUPERMAN_V4_2);
    }

    @Test
    void clearRemovesAllStoredComicBooks() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1);
        underTest.add(BATMAN_V2_2);
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);
        underTest.add(GREEN_LANTERN_V5_1);
        underTest.add(GREEN_LANTERN_V5_2);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(SUPERMAN_V4_2);

        underTest.clear();

        final var foundComicBooks = underTest.getComicBooks();

        assertThat(foundComicBooks).isEmpty();
    }

    @Test
    void searchBySeriesReturnsCorrectComicBooksForProvidedSeries() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1);
        underTest.add(BATMAN_V2_2);
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);
        underTest.add(GREEN_LANTERN_V5_1);
        underTest.add(GREEN_LANTERN_V5_2);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(SUPERMAN_V4_2);

        final var foundComicBooks = underTest.searchBySeries(GREEN_LANTERN);

        assertThat(foundComicBooks).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1, GREEN_LANTERN_V4_2, GREEN_LANTERN_V5_1, GREEN_LANTERN_V5_2);
    }

    @Test
    void searchByAnyAttributeReturnsCorrectComicBooksForProvidedAttribute() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1);
        underTest.add(BATMAN_V2_2);
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);
        underTest.add(GREEN_LANTERN_V5_1);
        underTest.add(GREEN_LANTERN_V5_2);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(SUPERMAN_V4_2);

        final var foundComicBooks = underTest.search(comicBook -> comicBook.issue() == 1);

        assertThat(foundComicBooks).containsExactlyInAnyOrder(BATMAN_V2_1, GREEN_LANTERN_V4_1, GREEN_LANTERN_V5_1, SUPERMAN_V4_1);
    }

    @Test
    void searchByMultipleAttributeReturnsCorrectComicBooksForProvidedAttribute() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1);
        underTest.add(BATMAN_V2_2);
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);
        underTest.add(GREEN_LANTERN_V5_1);
        underTest.add(GREEN_LANTERN_V5_2);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(SUPERMAN_V4_2);

        Predicate<ComicBook> p1 = comicBook -> comicBook.series().equals(BATMAN);
        Predicate<ComicBook> p2 = comicBook -> comicBook.volume() == 2;
        Predicate<ComicBook> p3 = comicBook -> comicBook.issue() == 1;

        final var foundComicBooks = underTest.search(p1.and(p2).and(p3));

        assertThat(foundComicBooks).containsExactlyInAnyOrder(BATMAN_V2_1);
    }

    @Test
    void searchByCharactersReturnsCorrectComicBooksForProvidedCharacters() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1); // character set is empty, should not be included in results.
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(BLACKEST_NIGHT_V1_1);
        underTest.add(BLACKEST_NIGHT_V1_8);

        final var foundComicBooks = underTest.searchByCharacters(Set.of(BATMAN, GREEN_LANTERN));

        assertThat(foundComicBooks).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1, BLACKEST_NIGHT_V1_1, BLACKEST_NIGHT_V1_8);
    }

    @Test
    void emptyCharacterSetSearchReturnsNoComics() {

        final var underTest = new ComicBookRepository();

        underTest.add(BATMAN_V2_1);
        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(SUPERMAN_V4_1);
        underTest.add(BLACKEST_NIGHT_V1_1);
        underTest.add(BLACKEST_NIGHT_V1_8);

        final var foundComicBooks = underTest.searchByCharacters(Set.of());

        assertThat(foundComicBooks).isEmpty();
    }

    @Test
    void removeSelectedComicBookFromRepository() {

        final var comicBooks = new ArrayList<ComicBook>();

        final var underTest = new ComicBookRepository(comicBooks);

        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);

        final var result = underTest.remove(GREEN_LANTERN_V4_1);

        assertThat(result).isTrue();
        assertThat(comicBooks).containsExactlyInAnyOrder(GREEN_LANTERN_V4_2);
    }

    @Test
    void removeNothingIfComicNotFound() {

        final var comicBooks = new ArrayList<ComicBook>();

        final var underTest = new ComicBookRepository(comicBooks);

        underTest.add(GREEN_LANTERN_V4_1);
        underTest.add(GREEN_LANTERN_V4_2);

        final var result = underTest.remove(BLACKEST_NIGHT_V1_1);

        assertThat(result).isFalse();
        assertThat(comicBooks).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1, GREEN_LANTERN_V4_2);
    }
}
