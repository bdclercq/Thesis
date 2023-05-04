package net.democritus.sys;

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
 * Persistency pojo class for the entity ParamTargetValue,
 */

@Entity(name=ParamTargetValueData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="ParamTargetValue", schema="UTILS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ParamTargetValueData.QUERY_FINDALL, query="select o FROM " + ParamTargetValueData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class ParamTargetValueData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.sys.ParamTargetValue";
  public static final String QUERY_FINDALL = "net.democritus.sys.ParamTargetValue.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mParam;
  private String mTarget;
  private String mValue;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ParamTargetValueData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ParamTargetValueData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String param
      , String target
      , String value
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mParam = param;
    this.mTarget = target;
    this.mValue = value;
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
  // @anchor:annotations-getter-param:start
  // @anchor:annotations-getter-param:end
  // anchor:custom-annotations-getter-param:start
  // anchor:custom-annotations-getter-param:end
  public String getParam() {
    return this.mParam;
  }

  // @anchor:annotations-setter-param:start
  // @anchor:annotations-setter-param:end
  // anchor:custom-annotations-setter-param:start
  // anchor:custom-annotations-setter-param:end
  public void setParam(String param) {
    this.mParam = param;
  }

  // @anchor:annotations-getter-target:start
  // @anchor:annotations-getter-target:end
  // anchor:custom-annotations-getter-target:start
  // anchor:custom-annotations-getter-target:end
  public String getTarget() {
    return this.mTarget;
  }

  // @anchor:annotations-setter-target:start
  // @anchor:annotations-setter-target:end
  // anchor:custom-annotations-setter-target:start
  // anchor:custom-annotations-setter-target:end
  public void setTarget(String target) {
    this.mTarget = target;
  }

  // @anchor:annotations-getter-value:start
  // @anchor:annotations-getter-value:end
  // anchor:custom-annotations-getter-value:start
  // anchor:custom-annotations-getter-value:end
  public String getValue() {
    return this.mValue;
  }

  // @anchor:annotations-setter-value:start
  // @anchor:annotations-setter-value:end
  // anchor:custom-annotations-setter-value:start
  // anchor:custom-annotations-setter-value:end
  public void setValue(String value) {
    this.mValue = value;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
