package net.democritus.usr.menu;

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
 * Transport detailed class for the entity bean ScreenProfile,
 */

public class ScreenProfileDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private DataRef mScreen;
  private DataRef mProfile;
  private List<DataRef> mScreens;
  private List<net.democritus.usr.menu.ScreenDetails> mScreensDetails;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ScreenProfileDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mScreen = DataRef.withId(0L);
    this.mProfile = DataRef.withId(0L);
    this.mScreens = new ArrayList<DataRef>();
    this.mScreensDetails = new ArrayList<net.democritus.usr.menu.ScreenDetails>();
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ScreenProfileDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , DataRef screen
      , DataRef profile
      , List<DataRef> screens
      , List<net.democritus.usr.menu.ScreenDetails> screensDetails
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mScreen = screen;
    this.mProfile = profile;
    this.mScreens = screens;
    this.mScreensDetails = screensDetails;
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
    return "ScreenProfile";
  }

  public String getElementPackage() {
    return "net.democritus.usr.menu";
  }
  // anchor:getters-setters:start
  public DataRef getScreen() {
    return this.mScreen;
  }

  public void setScreen(DataRef screen) {
    this.mScreen = screen;
  }

  public DataRef getProfile() {
    return this.mProfile;
  }

  public void setProfile(DataRef profile) {
    this.mProfile = profile;
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
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, "", "account", "net.democritus.usr.menu", "ScreenProfile");
  }
}
