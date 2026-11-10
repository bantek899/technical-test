# Notes

Please add any notes you'd like to add here!

ComicBookRepository changes:
- The parameterized constructor should make a defensive copy of `List<ComicBook>` passed to it to so any modifications to the caller's collection do not affect the repository's internal data. 
- Checking for duplicates in a `List` is an O(n) operation. For performance optimisation where we don't want duplicates we should make `comicBooks` a `Set` (a `HashSet` if insertion order doesn't matter) which gives O(1) average-case lookups, insertions and removals.


List, search and filter endpoint improvements
-  Endpoints from the API should allow for pagination by supporting a `size` or `page` parameter - this improves performance and response times as it allows data to be fetched incrementally and lets us control the size of the response. The response should include pagination metadata (such as current page, total pages) and enforce a sensible default or maximum page size. 

Remove API improvements
- The remove request should return helpful HTTP response codes - 204 for successful delete, 404 if comic not found 