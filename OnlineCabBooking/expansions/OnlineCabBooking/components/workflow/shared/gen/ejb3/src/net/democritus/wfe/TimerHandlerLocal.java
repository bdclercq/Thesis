package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.wfe.EngineServiceDetails;
// anchor:custom-imports:start
import net.democritus.sys.SearchResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;

import java.util.Date;
// anchor:custom-imports:end

/**
 * Local interface for the session bean TimerHandler.
 */

public interface TimerHandlerLocal {

  // anchor:custom-methods:start
  Integer startTimer(String name);

  Integer stopTimer(String name);

  SearchResult<Date> getNextRun(ParameterContext<DataRef> parameter);

  Integer startTimer(String name, TimerOptions timerOptions);

  Integer refreshTimer(String name);
  // anchor:custom-methods:end
}

