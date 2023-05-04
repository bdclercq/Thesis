package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.io.Serializable;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;
import java.util.ArrayList;
import java.util.List;

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean TimeWindowGroup,
 */

public class TimeWindowGroupDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mVisible;
  private List<DataRef> mTimeWindows;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TimeWindowGroupDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mVisible = "";
    this.mTimeWindows = new ArrayList<DataRef>();
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TimeWindowGroupDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String visible
      , List<DataRef> timeWindows
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mVisible = visible;
    this.mTimeWindows = timeWindows;
    // anchor:detailed-constructor-initialization:end
    // @anchor:detailed-constructor-initialization:start
    // @anchor:detailed-constructor-initialization:end

    // anchor:custom-detail-constructor:start
    // anchor:custom-detail-constructor:end
  }

  /*========== Getters and Setters ==========*/

  public Long getId() {
    return this.mId;
  }

  public void setId(Long id) {
    this.mId = id;
  }

  public String getElementName() {
    return "TimeWindowGroup";
  }

  public String getElementPackage() {
    return "net.democritus.wfe";
  }
  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getVisible() {
    return this.mVisible;
  }

  public void setVisible(String visible) {
    this.mVisible = visible;
  }

  public List<DataRef> getTimeWindows() {
    return this.mTimeWindows;
  }

  public void setTimeWindows(List<DataRef> timeWindows) {
    this.mTimeWindows = timeWindows;
  }

  public List<Long> getTimeWindowsIds() {
    final List<Long> timeWindowsIds = new ArrayList<Long>();
    for (DataRef dataRef : mTimeWindows) {
      timeWindowsIds.add(dataRef.getId());
    }
    return timeWindowsIds;
  }

  public void setTimeWindowsIds(String[] timeWindowsIds) {
    this.mTimeWindows = new ArrayList<DataRef>();
    for (final String string : timeWindowsIds) {
      mTimeWindows.add(new DataRef(new Long(string), "", "", "", ""));
    }
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.wfe", "TimeWindowGroup");
  }
}
