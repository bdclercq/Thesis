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
 * Persistency pojo class for the entity Menu,
 */

@Entity(name=MenuData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Menu", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=MenuData.QUERY_FINDALL, query="select o FROM " + MenuData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class MenuData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.menu.Menu";
  public static final String QUERY_FINDALL = "net.democritus.usr.menu.Menu.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private Long mPortal;
  private Long mProfile;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public MenuData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public MenuData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , Long portal
      , Long profile
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mPortal = portal;
    this.mProfile = profile;
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

  // @anchor:annotations-getter-portal:start
  @Column(name="portal_id")
  // @anchor:annotations-getter-portal:end
  // anchor:custom-annotations-getter-portal:start
  // anchor:custom-annotations-getter-portal:end
  public Long getPortal() {
    return this.mPortal;
  }

  // @anchor:annotations-setter-portal:start
  // @anchor:annotations-setter-portal:end
  // anchor:custom-annotations-setter-portal:start
  // anchor:custom-annotations-setter-portal:end
  public void setPortal(Long portal) {
    this.mPortal = portal;
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
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
