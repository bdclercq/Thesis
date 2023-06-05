package net.democritus.workflow;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SearchResultCache<T> {

  private static final Logger logger = LoggerFactory.getLogger(SearchResultCache.class);

  private final long timeout;
  private final Supplier<SearchResult<T>> fetcher;
  private long expirationTime;
  private List<T> instances;

  public SearchResultCache(long timeout, Supplier<SearchResult<T>> fetcher) {
    this.timeout = timeout;
    this.expirationTime = 0L;
    this.fetcher = fetcher;
    this.instances = null;
  }

  public synchronized List<T> get() {
    if (instances == null || isExpired()) {
      refresh();
    }
    return instances;
  }

  public synchronized CrudsResult<T> getInstance(Predicate<T> predicate) {
    List<T> list = get();
    if (list == null) {
      return CrudsResult.error();
    }
    return list.stream().filter(predicate)
        .map(CrudsResult::success)
        .findAny()
        .orElse(CrudsResult.error());
  }

  public synchronized SearchResult<T> filter(Predicate<T> predicate) {
    List<T> list = get();
    if (list == null) {
      return SearchResult.success(Collections.emptyList());
    }
    return SearchResult.success(
        list.stream()
            .filter(predicate)
            .collect(Collectors.toList()));
  }

  private void refresh() {
    SearchResult<T> searchResult = fetcher.get();
    if (searchResult.isError()) {
      logger.error("Error searching workflow configuration");
      return;
    }
    this.instances = searchResult.getResults();
    this.expirationTime = System.currentTimeMillis() + timeout;
  }

  public boolean isExpired() {
    return System.currentTimeMillis() > expirationTime;
  }

  public void expire() {
    this.expirationTime = 0L;
  }

}
