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
 * Persistency pojo class for the entity Execution,
 */

@Entity(name=ExecutionData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Execution", schema="UTILS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ExecutionData.QUERY_FINDALL, query="select o FROM " + ExecutionData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class ExecutionData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.sys.Execution";
  public static final String QUERY_FINDALL = "net.democritus.sys.Execution.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mComponent;
  private String mElement;
  private String mPackageName;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ExecutionData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ExecutionData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String component
      , String element
      , String packageName
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mComponent = component;
    this.mElement = element;
    this.mPackageName = packageName;
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

  // @anchor:annotations-getter-component:start
  // @anchor:annotations-getter-component:end
  // anchor:custom-annotations-getter-component:start
  // anchor:custom-annotations-getter-component:end
  public String getComponent() {
    return this.mComponent;
  }

  // @anchor:annotations-setter-component:start
  // @anchor:annotations-setter-component:end
  // anchor:custom-annotations-setter-component:start
  // anchor:custom-annotations-setter-component:end
  public void setComponent(String component) {
    this.mComponent = component;
  }

  // @anchor:annotations-getter-element:start
  // @anchor:annotations-getter-element:end
  // anchor:custom-annotations-getter-element:start
  // anchor:custom-annotations-getter-element:end
  public String getElement() {
    return this.mElement;
  }

  // @anchor:annotations-setter-element:start
  // @anchor:annotations-setter-element:end
  // anchor:custom-annotations-setter-element:start
  // anchor:custom-annotations-setter-element:end
  public void setElement(String element) {
    this.mElement = element;
  }

  // @anchor:annotations-getter-packageName:start
  // @anchor:annotations-getter-packageName:end
  // anchor:custom-annotations-getter-packageName:start
  // anchor:custom-annotations-getter-packageName:end
  public String getPackageName() {
    return this.mPackageName;
  }

  // @anchor:annotations-setter-packageName:start
  // @anchor:annotations-setter-packageName:end
  // anchor:custom-annotations-setter-packageName:start
  // anchor:custom-annotations-setter-packageName:end
  public void setPackageName(String packageName) {
    this.mPackageName = packageName;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
