package com.global.dax.testing;

import com.global.dax.core.model.ComicBook;

import java.util.Set;

/**
 * All comics can be found in the <a href="https://leagueofcomicgeeks.com/">League of Geeks Database</a>
 */
public class TestConstants {

    public static final String BATMAN = "Batman";
    public static final String GREEN_LANTERN = "Green Lantern";
    public static final String SUPERMAN = "Superman";
    public static final String BLACKEST_NIGHT = "Blackest Night";
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/8782949/batman-1">Batman - Volume 2 - Issue 1</a>
     * <p>
     * Characters:
     * <p>
     * * Batman
     */
    public static final ComicBook BATMAN_V2_1 = new ComicBook(BATMAN, 2, 1);

    /**
     * <a href="https://leagueofcomicgeeks.com/comic/2894159/batman-2">Batman - Volume 2 - Issue 2</a>
     * <p>
     * Characters:
     * <p>
     * * Batman
     */
    public static final ComicBook BATMAN_V2_2 = new ComicBook(BATMAN, 2, 2, Set.of(BATMAN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/4579722/green-lantern-1">Green Lantern - Volume 4 - Issue 1</a>
     * <p>
     * Characters:
     * <p>
     * * Green Lantern
     */
    public static final ComicBook GREEN_LANTERN_V4_1 = new ComicBook(GREEN_LANTERN, 4, 1, Set.of(GREEN_LANTERN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/6196487/green-lantern-2">Green Lantern - Volume 4 - Issue 2</a>
     * <p>
     * Characters:
     * <p>
     * * Green Lantern
     */
    public static final ComicBook GREEN_LANTERN_V4_2 = new ComicBook(GREEN_LANTERN, 4, 2, Set.of(GREEN_LANTERN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/7072448/green-lantern-1">Green Lantern - Volume 5 - Issue 1</a>
     * <p>
     * Characters:
     * <p>
     * * Green Lantern
     */
    public static final ComicBook GREEN_LANTERN_V5_1 = new ComicBook(GREEN_LANTERN, 5, 1, Set.of(GREEN_LANTERN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/6849765/green-lantern-2">Green Lantern - Volume 5 - Issue 2</a>
     * <p>
     * Characters:
     * <p>
     * * Green Lantern
     */
    public static final ComicBook GREEN_LANTERN_V5_2 = new ComicBook(GREEN_LANTERN, 5, 2, Set.of(GREEN_LANTERN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/9908040/superman-1">Superman - Volume 4 - Issue 1</a>
     * <p>
     * Characters:
     * <p>
     * * Superman
     */
    public static final ComicBook SUPERMAN_V4_1 = new ComicBook(SUPERMAN, 4, 1, Set.of(SUPERMAN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/1520497/superman-2">Superman - Volume 4 - Issue 2</a>
     * <p>
     * Characters:
     * <p>
     * * Superman
     */
    public static final ComicBook SUPERMAN_V4_2 = new ComicBook(SUPERMAN, 4, 2, Set.of(SUPERMAN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/2710631/blackest-night-1">Blackest Night - Volume 1 - Issue 1</a>
     * <p>
     * Characters:
     * <p>
     * * Green Lantern
     * <p>
     * * Batman
     */
    public static final ComicBook BLACKEST_NIGHT_V1_1 = new ComicBook(BLACKEST_NIGHT, 1, 1, Set.of(GREEN_LANTERN, BATMAN));
    /**
     * <a href="https://leagueofcomicgeeks.com/comic/1789209/blackest-night-8">Blackest Night - Volume 1 - Issue 8</a>
     * <p>
     * Characters:
     * <p>
     * * Green Lantern
     * <p>
     * * Superman
     */
    public static final ComicBook BLACKEST_NIGHT_V1_8 = new ComicBook(BLACKEST_NIGHT, 1, 8, Set.of(GREEN_LANTERN, SUPERMAN));

    private TestConstants() {
    }
}
