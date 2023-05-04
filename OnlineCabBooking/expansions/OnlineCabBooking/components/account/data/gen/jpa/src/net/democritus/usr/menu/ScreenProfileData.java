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
 * Persistency pojo class for the entity ScreenProfile,
 */

@Entity(name=ScreenProfileData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="ScreenProfile", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ScreenProfileData.QUERY_FINDALL, query="select o FROM " + ScreenProfileData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class ScreenProfileData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.menu.ScreenProfile";
  public static final String QUERY_FINDALL = "net.democritus.usr.menu.ScreenProfile.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private Long mScreen;
  private Long mProfile;
  private Collection<ScreenData> mScreens;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ScreenProfileData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ScreenProfileData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Long screen
      , Long profile
      , Collection<ScreenData> screens
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mScreen = screen;
    this.mProfile = profile;
    this.mScreens = screens;
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
  @Transient
  public String getName() {
    return this.getId() == null ? "" : this.getId().toString();
  }
  // @anchor:methods:end
  // anchor:getters-and-setters:start
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

  // @anchor:annotations-getter-profile:start
  @Column(name="profile_id")
  // @anchor:annotations-getter-profile:end
  // anchor:custom-annotations-getter-profile:start
  // anchor:custom-annotations-getter-profile:end
  public Long getProfile() {
    return this.mProfile;
  }

  // @anchor:annotations-setter-profile:start
  // @anchor:annotations-setter-profile:end
  // anchor:custom-annotations-setter-profile:start
  // anchor:custom-annotations-setter-profile:end
  public void setProfile(Long profile) {
    this.mProfile = profile;
  }

  // @anchor:annotations-getter-screens:start
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="screenProfile_screens",
    schema="ACCOUNT",
    joinColumns=@JoinColumn(name="screenProfile_id"),
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
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
