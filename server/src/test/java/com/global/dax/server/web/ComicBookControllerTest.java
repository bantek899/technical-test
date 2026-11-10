package com.global.dax.server.web;

import com.global.dax.core.ComicBookRepository;
import com.global.dax.core.model.ComicBook;
import com.global.dax.core.model.ComicBookSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static com.global.dax.testing.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ComicBookControllerTest {

    @Autowired
    private ComicBookRepository comicBookRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {

        comicBookRepository.clear();
    }

    @Test
    void comicBooksCanBeRetrieved() {

        final var comicBooksResponse = getComicBooks();
        assertThat(comicBooksResponse.getBody()).isEmpty();

        comicBookRepository.add(GREEN_LANTERN_V4_1);
        comicBookRepository.add(GREEN_LANTERN_V4_2);

        final var comicBooksResponse2 = getComicBooks();

        assertThat(comicBooksResponse2.getBody()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2);
    }

    @Test
    void comicBooksCanBeAdded() {

        addComicBook(GREEN_LANTERN_V4_1);

        assertThat(comicBookRepository.getComicBooks()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1);

        addComicBook(GREEN_LANTERN_V4_2);

        assertThat(comicBookRepository.getComicBooks()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2);
    }

    @Test
    void comicBooksCanBeSearched() {

        comicBookRepository.add(GREEN_LANTERN_V4_1);
        comicBookRepository.add(GREEN_LANTERN_V4_2);
        comicBookRepository.add(GREEN_LANTERN_V5_1);
        comicBookRepository.add(GREEN_LANTERN_V5_2);

        final var searchResponse = searchComicBooksBySeries("Green Lantern");

        assertThat(searchResponse.getBody()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2,
                GREEN_LANTERN_V5_1,
                GREEN_LANTERN_V5_2);

    }

    @Test
    void comicBooksCanBeSearchedByAnyAttribute() {

        comicBookRepository.add(GREEN_LANTERN_V4_1);
        comicBookRepository.add(GREEN_LANTERN_V4_2);
        comicBookRepository.add(GREEN_LANTERN_V5_1);
        comicBookRepository.add(GREEN_LANTERN_V5_2);

        ComicBookSearchCriteria issue = new ComicBookSearchCriteria(null, null, 1);
        final var searchResponse = searchComicBooksByComicBookSearchCriteria(issue);

        assertThat(searchResponse.getBody()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V5_1);

        ComicBookSearchCriteria volume = new ComicBookSearchCriteria(null, 4, null);
        final var searchResponseVol = searchComicBooksByComicBookSearchCriteria(volume);

        assertThat(searchResponseVol.getBody()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2);

    }

    @Test
    void comicBooksSearchWithNoAttributesReturnsAllComicBooks() {

        comicBookRepository.add(GREEN_LANTERN_V4_1);
        comicBookRepository.add(GREEN_LANTERN_V4_2);
        comicBookRepository.add(GREEN_LANTERN_V5_1);
        comicBookRepository.add(GREEN_LANTERN_V5_2);

        ComicBookSearchCriteria criteria = new ComicBookSearchCriteria(null, null, null);
        final var searchResponse = searchComicBooksByComicBookSearchCriteria(criteria);

        assertThat(searchResponse.getBody()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2,
                GREEN_LANTERN_V5_1,
                GREEN_LANTERN_V5_2);

    }

    @Test
    void comicBooksCanBeSearchedByMultipleAttributes() {

        comicBookRepository.add(GREEN_LANTERN_V4_1);
        comicBookRepository.add(GREEN_LANTERN_V4_2);
        comicBookRepository.add(GREEN_LANTERN_V5_1);
        comicBookRepository.add(GREEN_LANTERN_V5_2);

        ComicBookSearchCriteria criteria = new ComicBookSearchCriteria(GREEN_LANTERN, 4, 1);
        final var searchResponse = searchComicBooksByComicBookSearchCriteria(criteria);

        assertThat(searchResponse.getBody()).containsExactly(GREEN_LANTERN_V4_1);

    }

    @Test
    void comicBooksCanBeSearchedByCharacters() {

        comicBookRepository.add(GREEN_LANTERN_V4_1);
        comicBookRepository.add(GREEN_LANTERN_V4_2);
        comicBookRepository.add(GREEN_LANTERN_V5_1);
        comicBookRepository.add(GREEN_LANTERN_V5_2);

        List<String> characters = List.of(GREEN_LANTERN, BATMAN);
        final var searchResponse = searchComicBooksByCharacters(characters);

        assertThat(searchResponse.getBody()).containsExactly(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2,
                GREEN_LANTERN_V5_1,
                GREEN_LANTERN_V5_2);

    }

    @Test
    void comicBooksCanBeRemoved() {

        addComicBook(GREEN_LANTERN_V4_1);
        addComicBook(GREEN_LANTERN_V4_2);

        assertThat(comicBookRepository.getComicBooks()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1,
                GREEN_LANTERN_V4_2);

        removeComicBook(GREEN_LANTERN_V4_2);

        assertThat(comicBookRepository.getComicBooks()).containsExactlyInAnyOrder(GREEN_LANTERN_V4_1);

        removeComicBook(GREEN_LANTERN_V4_1);

        assertThat(comicBookRepository.getComicBooks()).isEmpty();
    }

    private ResponseEntity<ComicBook[]> getComicBooks() {

        final var responseEntity = testRestTemplate.getForEntity("/api/comic-books", ComicBook[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity;
    }

    private ResponseEntity<ComicBook[]> searchComicBooksBySeries(final String series) {

        final var responseEntity = testRestTemplate.getForEntity("/api/comic-books/search?series=" + series,
                ComicBook[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity;
    }

    private ResponseEntity<ComicBook[]> searchComicBooksByComicBookSearchCriteria(final ComicBookSearchCriteria criteria) {

        final var uri = UriComponentsBuilder.fromUriString(testRestTemplate.getRootUri())
                .path("/api/comic-books/search")
                .queryParamIfPresent("series", Optional.ofNullable(criteria.series()))
                .queryParamIfPresent("volume", Optional.ofNullable(criteria.volume()))
                .queryParamIfPresent("issue", Optional.ofNullable(criteria.issue()))
                .build()
                .encode()
                .toUri();

        final var responseEntity = testRestTemplate.getForEntity(uri, ComicBook[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity;
    }

    private ResponseEntity<ComicBook[]> searchComicBooksByCharacters(final List<String> characters) {

        final var responseEntity = testRestTemplate.getForEntity("/api/comic-books/search?characters=" + characters,
                ComicBook[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity;
    }

    private void addComicBook(final ComicBook comicBook) {

        final var responseEntity = testRestTemplate.postForEntity("/api/comic-books",
                comicBook,
                Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void removeComicBook(final ComicBook comicBook) {

        final var responseEntity = testRestTemplate.exchange("/api/comic-books",
                HttpMethod.DELETE,
                new HttpEntity<>(comicBook),
                Boolean.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
