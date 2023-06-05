package net.democritus.workflow;

import net.democritus.sys.Context;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.palver.util.Options;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class SearchResultCacheProvider<S, T> {
  private static final long DEFAULT_EXPIRE_TIMEOUT = 10_000L;

  private final BiFunction<S, Context, SearchResult<T>> fetcher;
  private final Map<Long, SearchResultCache<T>> caches = new HashMap<>();
  private final long timeout;

  /**
   * Create a Cache provider for SearchResult caches.
   * The provider provides a separate cache for each user.
   *
   * @param fetcher function that fetches instances of the target element
   * @param <S> Bean the target element
   * @param <T> type of the target element
   * @return a Cache provider for SearchResult caches
   */
  public SearchResultCacheProvider(BiFunction<S, Context, SearchResult<T>> fetcher) {
    timeout = DEFAULT_EXPIRE_TIMEOUT;
    this.fetcher = fetcher;
  }

  public synchronized SearchResultCache<T> getCache(S bean, Context context) {
    Long userId = getUserId(context);
    return caches.computeIfAbsent(userId, uid ->
      new SearchResultCache<T>(timeout, () -> fetcher.apply(bean, context))
    );
  }

  public synchronized void expireCaches() {
    caches.forEach((id, cache) ->  cache.expire());
  }

  private Long getUserId(Context context) {
    Options.Option<UserContext> userContextOption = context.getContext(UserContext.class);
    if (userContextOption.isEmpty()) {
      return 0L;
    } else {
      return userContextOption.getValue().getId();
    }
  }
}
