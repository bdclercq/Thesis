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
 * Persistency pojo class for the entity Screen,
 */

@Entity(name=ScreenData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Screen", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ScreenData.QUERY_FINDALL, query="select o FROM " + ScreenData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class ScreenData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.menu.Screen";
  public static final String QUERY_FINDALL = "net.democritus.usr.menu.Screen.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mLink;
  private Integer mSortOrder;
  private Long mComponent;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ScreenData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ScreenData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String link
      , Integer sortOrder
      , Long component
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mLink = link;
    this.mSortOrder = sortOrder;
    this.mComponent = component;
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
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-name:start
  // @anchor:annotations-getter-name:end
  // anchor:custom-annotations-getter-name:start
  // anchor:custom-annotations-getter-name:end
  public String getName() {
    return this.mName;
  }

  // @anchor:annotations-setter-name:start
  // @anchor:annotations-setter-name:end
  // anchor:custom-annotations-setter-name:start
  // anchor:custom-annotations-setter-name:end
  public void setName(String name) {
    this.mName = name;
  }

  // @anchor:annotations-getter-link:start
  // @anchor:annotations-getter-link:end
  // anchor:custom-annotations-getter-link:start
  // anchor:custom-annotations-getter-link:end
  public String getLink() {
    return this.mLink;
  }

  // @anchor:annotations-setter-link:start
  // @anchor:annotations-setter-link:end
  // anchor:custom-annotations-setter-link:start
  // anchor:custom-annotations-setter-link:end
  public void setLink(String link) {
    this.mLink = link;
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

  // @anchor:annotations-getter-component:start
  @Column(name="component_id")
  // @anchor:annotations-getter-component:end
  // anchor:custom-annotations-getter-component:start
  // anchor:custom-annotations-getter-component:end
  public Long getComponent() {
    return this.mComponent;
  }

  // @anchor:annotations-setter-component:start
  // @anchor:annotations-setter-component:end
  // anchor:custom-annotations-setter-component:start
  // anchor:custom-annotations-setter-component:end
  public void setComponent(Long component) {
    this.mComponent = component;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
