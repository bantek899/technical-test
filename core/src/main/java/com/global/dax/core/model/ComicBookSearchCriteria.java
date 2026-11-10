package com.global.dax.core.model;

import java.util.function.Predicate;

public record ComicBookSearchCriteria(String series, Integer volume, Integer issue) {
    public Predicate<ComicBook> toPredicate() {
        Predicate<ComicBook> p = c -> true; // predicate defaults to true if no search criteria provided

        if (series != null) p = p.and(c -> c.series().equals(series));
        if (volume != null) p = p.and(c -> c.volume() == volume);
        if (issue != null) p = p.and(c -> c.issue() == issue);

        return p;
    }
}
