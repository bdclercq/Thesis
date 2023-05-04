package net.democritus.wfe;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkflowTimeTest {

  @Test
  public void firstMinuteIsInTimeWindow() throws Exception {
    WorkflowTime currentTime = new WorkflowTime("02:00:00");
    TimeWindowDetails fullDay = new TimeWindowDetails();
    fullDay.setStartTime("02:00:00");
    fullDay.setStopTime("20:59:59");
    assertTrue(currentTime.isInTimeWindow(fullDay));
  }

  @Test
  public void minuteBeforeIsNotInTimeWindow() throws Exception {
    WorkflowTime currentTime = new WorkflowTime("01:59:59");
    TimeWindowDetails fullDay = new TimeWindowDetails();
    fullDay.setStartTime("02:00:00");
    fullDay.setStopTime("20:59:59");
    assertFalse(currentTime.isInTimeWindow(fullDay));
  }

  @Test
  public void lastMinuteIsInTimeWindow() throws Exception {
    WorkflowTime currentTime = new WorkflowTime("20:59:59");
    TimeWindowDetails fullDay = new TimeWindowDetails();
    fullDay.setStartTime("02:00:00");
    fullDay.setStopTime("20:59:59");
    assertTrue(currentTime.isInTimeWindow(fullDay));
  }

  @Test
  public void minuteAfterIsNotInTimeWindow() throws Exception {
    WorkflowTime currentTime = new WorkflowTime("21:00:00");
    TimeWindowDetails fullDay = new TimeWindowDetails();
    fullDay.setStartTime("02:00:00");
    fullDay.setStopTime("20:59:59");
    assertFalse(currentTime.isInTimeWindow(fullDay));
  }

}