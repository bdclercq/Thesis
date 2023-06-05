package net.democritus.wfe;

import net.democritus.workflow.StateTaskDetails;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class TimeoutCalculatorTest {

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private Date startDate;

  @Before
  public void setUp() throws Exception {
    startDate = dateFormat.parse("2018-05-16 11:59:50");
  }

  @Test
  public void shouldAddTimeout() throws Exception {
    StateTaskDetails stateTaskDetails = new StateTaskDetails();
    stateTaskDetails.setTimeout(20000L);

    Date timeoutTime = new TimeoutCalculator().calculateTimeout(stateTaskDetails, startDate);

    assertEquals("2018-05-16 12:00:10", dateFormat.format(timeoutTime));
  }

  @Test
  public void shouldAddDefaultTimeoutIfNoTimeoutGiven() throws Exception {
    StateTaskDetails stateTaskDetails = new StateTaskDetails();
    stateTaskDetails.setTimeout(null);

    Date timeoutTime = new TimeoutCalculator().calculateTimeout(stateTaskDetails, startDate);

    assertEquals("2018-05-16 12:04:50", dateFormat.format(timeoutTime));
  }
}