package net.democritus.wfe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

// @feature:engine-service-time-window
public class WorkflowTime {
  private final Date date;
  private final SimpleDateFormat oneHourMinutesFormat = new SimpleDateFormat("H:mm");
  private final SimpleDateFormat hoursMinutesFormat = new SimpleDateFormat("HH:mm");
  private final SimpleDateFormat hoursMinutesSecondsFormat = new SimpleDateFormat("HH:mm:ss");

  private final Pattern oneHourMinutesPattern = Pattern.compile("\\d{1}:\\d{2}");
  private final Pattern hoursMinutesPattern = Pattern.compile("\\d{2}:\\d{2}");
  private final Pattern hoursMinutesSecondsPattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");

  public WorkflowTime(String time) throws Exception {
    if (time.matches(oneHourMinutesPattern.pattern())) {
      this.date = oneHourMinutesFormat.parse(time);
    } else if (time.matches(hoursMinutesPattern.pattern())) {
      this.date = hoursMinutesFormat.parse(time);
    } else if (time.matches(hoursMinutesSecondsPattern.pattern())) {
      this.date = hoursMinutesSecondsFormat.parse(time);
    } else {
      date = null;
    }
  }

  public int getSeconds() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
  }

  public boolean isInTimeWindow(TimeWindowDetails timeWindowDetails) {
    if (date == null) {
      return true;
    }
    try {
      WorkflowTime beginTime = new WorkflowTime(timeWindowDetails.getStartTime());
      WorkflowTime endTime = new WorkflowTime(timeWindowDetails.getStopTime());
      return (isAfter(beginTime) && isBefore(endTime));
    } catch (Exception e) {
      return true;
    }
  }

  private boolean isBefore(WorkflowTime workflowTime) {
    return this.getSeconds() <= workflowTime.getSeconds();
  }

  private boolean isAfter(WorkflowTime workflowTime) {
    return this.getSeconds() >= workflowTime.getSeconds();
  }
}
