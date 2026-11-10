package com.global.dax.server.web;

import com.global.dax.core.ComicBookRepository;
import com.global.dax.core.model.ComicBook;
import com.global.dax.core.model.ComicBookSearchCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/comic-books")
@RestController
public class ComicBookController {

    private final ComicBookRepository comicBookRepository;

    public ComicBookController(final ComicBookRepository comicBookRepository) {

        this.comicBookRepository = comicBookRepository;
    }

    @PostMapping
    public void addComicBook(final @RequestBody ComicBook comicBook) {

        comicBookRepository.add(comicBook);
    }

    @GetMapping
    public List<ComicBook> getComicBooks() {

        return comicBookRepository.getComicBooks();
    }

    @GetMapping("/search/{series}")
    public List<ComicBook> searchBySeries(final @RequestParam("series") String series) {

        return comicBookRepository.searchBySeries(series);
    }

    @GetMapping("/search")
    public List<ComicBook> searchByAttributes(@ModelAttribute final ComicBookSearchCriteria criteria) {

        return comicBookRepository.search(criteria.toPredicate());
    }

    @GetMapping("/search/{characters}")
    public List<ComicBook> searchByCharacters(@RequestParam("characters") List<String> characters) {

        return comicBookRepository.searchByCharacters(new HashSet<>(characters));
    }

    @DeleteMapping
    public boolean remove(final @RequestBody ComicBook comicBook) {

        return comicBookRepository.remove(comicBook);
    }
}
