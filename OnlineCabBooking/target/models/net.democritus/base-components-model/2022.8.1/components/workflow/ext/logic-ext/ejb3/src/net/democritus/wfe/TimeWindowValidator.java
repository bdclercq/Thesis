package net.democritus.wfe;

import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// @feature:engine-service-time-window
class TimeWindowValidator {

    private final TimeWindowGroupLocalAgent timeWindowGroupLocalAgent;
    private final TimeWindowLocalAgent timeWindowLocalAgent;

    public TimeWindowValidator() {
        this.timeWindowGroupLocalAgent = TimeWindowGroupLocalAgent.getTimeWindowGroupAgent(Context.emptyContext());
        this.timeWindowLocalAgent = TimeWindowLocalAgent.getTimeWindowAgent(Context.emptyContext());
    }

    boolean validateTimeWindowGroup(DataRef timeWindowGroupDataRef) throws Exception {
        if (isTimeWindowGroupNotDefined(timeWindowGroupDataRef)) {
            return true;
        }

        List<TimeWindowDetails> timeWindowDetailsList = getTimeWindowDetailsFromTimeWindowGroup(timeWindowGroupDataRef);
        if (areTimeWindowsNotDefined(timeWindowDetailsList)) {
            return true;
        }

        return isTimeInAnyTimeWindow(new WorkflowTime(getCurrentTimeAsString()), timeWindowDetailsList);
    }

    boolean isTimeInAnyTimeWindow(WorkflowTime timeToCheck, List<TimeWindowDetails> timeWindowDetailsList) {
        for (TimeWindowDetails timeWindowDetails : timeWindowDetailsList) {
            if (timeToCheck.isInTimeWindow(timeWindowDetails)) {
                return true;
            }
        }

        return false;
    }

    private List<TimeWindowDetails> getTimeWindowDetailsFromTimeWindowGroup(DataRef timeWindowGroupDataRef) throws Exception {
        List<Long> timeWindowsIds = getTimeWindowGroupDetails(timeWindowGroupDataRef).getTimeWindowsIds();
        return getTimeWindows(timeWindowsIds);
    }

    private List<TimeWindowDetails> getTimeWindows(List<Long> timeWindowIds) throws Exception {
        List<TimeWindowDetails> timeWindowDetailsList = new ArrayList<TimeWindowDetails>();
        for (Long timeWindowId : timeWindowIds) {
            TimeWindowDetails timeWindowDetails = getTimeWindowDetails(timeWindowId);
            timeWindowDetailsList.add(timeWindowDetails);
        }
        return timeWindowDetailsList;
    }

    private String getCurrentTimeAsString() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(currentDate);
    }

    private boolean isTimeWindowGroupNotDefined(DataRef timeWindowGroupDataRef) {
        return timeWindowGroupDataRef == null || timeWindowGroupDataRef.getId() == null || timeWindowGroupDataRef.getId().equals(0L);
    }

    private boolean areTimeWindowsNotDefined(List<TimeWindowDetails> timeWindowDetailsList) {
        return timeWindowDetailsList == null || timeWindowDetailsList.isEmpty();
    }

    private TimeWindowGroupDetails getTimeWindowGroupDetails(DataRef timeWindowGroupDataRef) throws Exception {
        CrudsResult<TimeWindowGroupDetails> crudsResult = timeWindowGroupLocalAgent.getTimeWindowGroupById(timeWindowGroupDataRef.getId());
        if (crudsResult.isError()) {
            throw new Exception("Failed to retrieve details for timeWindowGroup with ID: " + timeWindowGroupDataRef.getId());
        }
        return crudsResult.getValue();
    }

    private TimeWindowDetails getTimeWindowDetails(Long timeWindowId) throws Exception {
        CrudsResult<TimeWindowDetails> crudsResult = timeWindowLocalAgent.getTimeWindowById(timeWindowId);
        if (crudsResult.isError()) {
            throw new Exception("Failed to retrieve details for timeWindow with ID: " + timeWindowId);
        }
        return crudsResult.getValue();
    }
}
