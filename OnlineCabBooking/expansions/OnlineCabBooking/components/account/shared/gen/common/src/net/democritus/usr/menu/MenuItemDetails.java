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
 * Transport detailed class for the entity bean MenuItem,
 */

public class MenuItemDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private DataRef mMenu;
  private DataRef mScreen;
  private net.democritus.usr.menu.ScreenDetails mScreenDetails;
  private DataRef mMenuItem;
  private List<DataRef> mSubmenuItems;
  private List<net.democritus.usr.menu.MenuItemDetails> mSubmenuItemsDetails;
  private Integer mSortOrder;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public MenuItemDetails() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mMenu = DataRef.withId(0L);
    this.mScreen = DataRef.withId(0L);
    this.mScreenDetails = new net.democritus.usr.menu.ScreenDetails();
    this.mMenuItem = DataRef.withId(0L);
    this.mSubmenuItems = new ArrayList<DataRef>();
    this.mSubmenuItemsDetails = new ArrayList<net.democritus.usr.menu.MenuItemDetails>();
    this.mSortOrder = null;
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public MenuItemDetails(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , DataRef menu
      , DataRef screen
      , net.democritus.usr.menu.ScreenDetails screenDetails
      , DataRef menuItem
      , List<DataRef> submenuItems
      , List<net.democritus.usr.menu.MenuItemDetails> submenuItemsDetails
      , Integer sortOrder
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mMenu = menu;
    this.mScreen = screen;
    this.mScreenDetails = screenDetails;
    this.mMenuItem = menuItem;
    this.mSubmenuItems = submenuItems;
    this.mSubmenuItemsDetails = submenuItemsDetails;
    this.mSortOrder = sortOrder;
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
    return "MenuItem";
  }

  public String getElementPackage() {
    return "net.democritus.usr.menu";
  }
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  // anchor:getters-setters:start
  public DataRef getMenu() {
    return this.mMenu;
  }

  public void setMenu(DataRef menu) {
    this.mMenu = menu;
  }

  public DataRef getScreen() {
    return this.mScreen;
  }

  public void setScreen(DataRef screen) {
    this.mScreen = screen;
  }

  public void setScreenDetails(net.democritus.usr.menu.ScreenDetails screenDetails) {
    this.mScreenDetails = screenDetails;
  }

  public net.democritus.usr.menu.ScreenDetails getScreenDetails() {
    return this.mScreenDetails;
  }

  public DataRef getMenuItem() {
    return this.mMenuItem;
  }

  public void setMenuItem(DataRef menuItem) {
    this.mMenuItem = menuItem;
  }

  public List<DataRef> getSubmenuItems() {
    return this.mSubmenuItems;
  }

  public void setSubmenuItems(List<DataRef> submenuItems) {
    this.mSubmenuItems = submenuItems;
  }

  public List<Long> getSubmenuItemsIds() {
    final List<Long> submenuItemsIds = new ArrayList<Long>();
    for (DataRef dataRef : mSubmenuItems) {
      submenuItemsIds.add(dataRef.getId());
    }
    return submenuItemsIds;
  }

  public void setSubmenuItemsIds(String[] submenuItemsIds) {
    this.mSubmenuItems = new ArrayList<DataRef>();
    for (final String string : submenuItemsIds) {
      mSubmenuItems.add(new DataRef(new Long(string), "", "", "", ""));
    }
  }

  public void setSubmenuItemsDetails(List<net.democritus.usr.menu.MenuItemDetails> collection) {
    this.mSubmenuItemsDetails = collection;
  }

  public List<net.democritus.usr.menu.MenuItemDetails> getSubmenuItemsDetails() {
    return this.mSubmenuItemsDetails;
  }

  public Integer getSortOrder() {
    return this.mSortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.mSortOrder = sortOrder;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.usr.menu", "MenuItem");
  }
}
