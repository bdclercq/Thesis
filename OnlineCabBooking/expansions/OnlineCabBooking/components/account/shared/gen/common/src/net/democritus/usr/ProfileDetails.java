package net.democritus.usr;

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
 * Transport detailed class for the entity bean Profile,
 */

public class ProfileDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private List<DataRef> mScreens;
  private List<net.democritus.usr.menu.ScreenDetails> mScreensDetails;
  private DataRef mUserGroup;
  private Integer mWeight;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ProfileDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mScreens = new ArrayList<DataRef>();
    this.mScreensDetails = new ArrayList<net.democritus.usr.menu.ScreenDetails>();
    this.mUserGroup = DataRef.withId(0L);
    this.mWeight = null;
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ProfileDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , List<DataRef> screens
      , List<net.democritus.usr.menu.ScreenDetails> screensDetails
      , DataRef userGroup
      , Integer weight
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mScreens = screens;
    this.mScreensDetails = screensDetails;
    this.mUserGroup = userGroup;
    this.mWeight = weight;
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
    return "Profile";
  }

  public String getElementPackage() {
    return "net.democritus.usr";
  }
  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public List<DataRef> getScreens() {
    return this.mScreens;
  }

  public void setScreens(List<DataRef> screens) {
    this.mScreens = screens;
  }

  public List<Long> getScreensIds() {
    final List<Long> screensIds = new ArrayList<Long>();
    for (DataRef dataRef : mScreens) {
      screensIds.add(dataRef.getId());
    }
    return screensIds;
  }

  public void setScreensIds(String[] screensIds) {
    this.mScreens = new ArrayList<DataRef>();
    for (final String string : screensIds) {
      mScreens.add(new DataRef(new Long(string), "", "", "", ""));
    }
  }

  public void setScreensDetails(List<net.democritus.usr.menu.ScreenDetails> collection) {
    this.mScreensDetails = collection;
  }

  public List<net.democritus.usr.menu.ScreenDetails> getScreensDetails() {
    return this.mScreensDetails;
  }

  public DataRef getUserGroup() {
    return this.mUserGroup;
  }

  public void setUserGroup(DataRef userGroup) {
    this.mUserGroup = userGroup;
  }

  public Integer getWeight() {
    return this.mWeight;
  }

  public void setWeight(Integer weight) {
    this.mWeight = weight;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.usr", "Profile");
  }
}
