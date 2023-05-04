package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.democritus.sys.DataRef;

// anchor:imports:start
// anchor:imports:end

// @anchor:imports:start
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Persistency pojo class for the entity MenuItem,
 */

@Entity(name=MenuItemData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="MenuItem", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=MenuItemData.QUERY_FINDALL, query="select o FROM " + MenuItemData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class MenuItemData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.menu.MenuItem";
  public static final String QUERY_FINDALL = "net.democritus.usr.menu.MenuItem.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Long mMenu;
  private Long mScreen;
  private Long mMenuItem;
  private Integer mSortOrder;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public MenuItemData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public MenuItemData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Long menu
      , Long screen
      , Long menuItem
      , Integer sortOrder
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mMenu = menu;
    this.mScreen = screen;
    this.mMenuItem = menuItem;
    this.mSortOrder = sortOrder;
    // anchor:constructor-assign:end
  }

  /*========== Getters and Setters ==========*/

  @Id
  // @anchor:generateId:start
  @GeneratedValue(strategy=GenerationType.AUTO)
  // @anchor:generateId:end
  // anchor:custom-annotations-getter-id:start
  // anchor:custom-annotations-getter-id:end
  public Long getId() {
    return this.mId;
  }

  // anchor:custom-annotations-setter-id:start
  // anchor:custom-annotations-setter-id:end
  public void setId(Long id) {
    this.mId = id;
  }

  // @anchor:methods:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-menu:start
  @Column(name="menu_id")
  // @anchor:annotations-getter-menu:end
  // anchor:custom-annotations-getter-menu:start
  // anchor:custom-annotations-getter-menu:end
  public Long getMenu() {
    return this.mMenu;
  }

  // @anchor:annotations-setter-menu:start
  // @anchor:annotations-setter-menu:end
  // anchor:custom-annotations-setter-menu:start
  // anchor:custom-annotations-setter-menu:end
  public void setMenu(Long menu) {
    this.mMenu = menu;
  }

  // @anchor:annotations-getter-screen:start
  @Column(name="screen_id")
  // @anchor:annotations-getter-screen:end
  // anchor:custom-annotations-getter-screen:start
  // anchor:custom-annotations-getter-screen:end
  public Long getScreen() {
    return this.mScreen;
  }

  // @anchor:annotations-setter-screen:start
  // @anchor:annotations-setter-screen:end
  // anchor:custom-annotations-setter-screen:start
  // anchor:custom-annotations-setter-screen:end
  public void setScreen(Long screen) {
    this.mScreen = screen;
  }

  // @anchor:annotations-getter-menuItem:start
  @Column(name="menuItem_id")
  // @anchor:annotations-getter-menuItem:end
  // anchor:custom-annotations-getter-menuItem:start
  // anchor:custom-annotations-getter-menuItem:end
  public Long getMenuItem() {
    return this.mMenuItem;
  }

  // @anchor:annotations-setter-menuItem:start
  // @anchor:annotations-setter-menuItem:end
  // anchor:custom-annotations-setter-menuItem:start
  // anchor:custom-annotations-setter-menuItem:end
  public void setMenuItem(Long menuItem) {
    this.mMenuItem = menuItem;
  }

  // @anchor:annotations-getter-sortOrder:start
  // @anchor:annotations-getter-sortOrder:end
  // anchor:custom-annotations-getter-sortOrder:start
  // anchor:custom-annotations-getter-sortOrder:end
  public Integer getSortOrder() {
    return this.mSortOrder;
  }

  // @anchor:annotations-setter-sortOrder:start
  // @anchor:annotations-setter-sortOrder:end
  // anchor:custom-annotations-setter-sortOrder:start
  // anchor:custom-annotations-setter-sortOrder:end
  public void setSortOrder(Integer sortOrder) {
    this.mSortOrder = sortOrder;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
