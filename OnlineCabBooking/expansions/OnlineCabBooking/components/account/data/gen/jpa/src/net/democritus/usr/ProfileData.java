package net.democritus.usr;

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
import net.democritus.usr.menu.ScreenData;
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
 * Persistency pojo class for the entity Profile,
 */

@Entity(name=ProfileData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Profile", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ProfileData.QUERY_FINDALL, query="select o FROM " + ProfileData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // custom query used by UserGroupScreenInfoRetrieverAction
  , @NamedQuery(name = ProfileData.QUERY_FINDBY_USERGROUP_PROFILE, query="SELECT o FROM " + ProfileData.ENTITY_NAME + " o WHERE o.userGroup IN ( :UserGroup ) OR o.id = :Profile ORDER BY o.weight DESC")
  // anchor:custom-queries:end
})
public class ProfileData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.Profile";
  public static final String QUERY_FINDALL = "net.democritus.usr.Profile.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  public static final String QUERY_FINDBY_USERGROUP_PROFILE = ENTITY_NAME + "_findByUserGroupEq_ProfileEq";
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private Collection<ScreenData> mScreens;
  private Long mUserGroup;
  private Integer mWeight;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ProfileData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ProfileData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , Collection<ScreenData> screens
      , Long userGroup
      , Integer weight
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mScreens = screens;
    this.mUserGroup = userGroup;
    this.mWeight = weight;
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

  // @anchor:annotations-getter-screens:start
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="profile_screens",
    schema="ACCOUNT",
    joinColumns=@JoinColumn(name="profile_id"),
    inverseJoinColumns=@JoinColumn(name="screen_id"))
  // @anchor:annotations-getter-screens:end
  // anchor:custom-annotations-getter-screens:start
  // anchor:custom-annotations-getter-screens:end
  public Collection<ScreenData> getScreens() {
    return this.mScreens;
  }

  // @anchor:annotations-setter-screens:start
  // @anchor:annotations-setter-screens:end
  // anchor:custom-annotations-setter-screens:start
  // anchor:custom-annotations-setter-screens:end
  public void setScreens(final Collection<ScreenData> screens) {
    this.mScreens = screens;
  }

  // @anchor:annotations-getter-userGroup:start
  @Column(name="userGroup_id")
  // @anchor:annotations-getter-userGroup:end
  // anchor:custom-annotations-getter-userGroup:start
  // anchor:custom-annotations-getter-userGroup:end
  public Long getUserGroup() {
    return this.mUserGroup;
  }

  // @anchor:annotations-setter-userGroup:start
  // @anchor:annotations-setter-userGroup:end
  // anchor:custom-annotations-setter-userGroup:start
  // anchor:custom-annotations-setter-userGroup:end
  public void setUserGroup(Long userGroup) {
    this.mUserGroup = userGroup;
  }

  // @anchor:annotations-getter-weight:start
  // @anchor:annotations-getter-weight:end
  // anchor:custom-annotations-getter-weight:start
  // anchor:custom-annotations-getter-weight:end
  public Integer getWeight() {
    return this.mWeight;
  }

  // @anchor:annotations-setter-weight:start
  // @anchor:annotations-setter-weight:end
  // anchor:custom-annotations-setter-weight:start
  // anchor:custom-annotations-setter-weight:end
  public void setWeight(Integer weight) {
    this.mWeight = weight;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
