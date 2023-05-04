package net.democritus.workflow;

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
 * Persistency pojo class for the entity Workflow,
 */

@Entity(name=WorkflowData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Workflow", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=WorkflowData.QUERY_FINDALL, query="select o FROM " + WorkflowData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class WorkflowData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.workflow.Workflow";
  public static final String QUERY_FINDALL = "net.democritus.workflow.Workflow.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mTarget;
  private String mComponentName;
  private String mClassName;
  private Long mResponsible;
  private String mSequencingStrategy;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public WorkflowData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public WorkflowData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String target
      , String componentName
      , String className
      , Long responsible
      , String sequencingStrategy
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mTarget = target;
    this.mComponentName = componentName;
    this.mClassName = className;
    this.mResponsible = responsible;
    this.mSequencingStrategy = sequencingStrategy;
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

  // @anchor:annotations-getter-componentName:start
  // @anchor:annotations-getter-componentName:end
  // anchor:custom-annotations-getter-componentName:start
  // anchor:custom-annotations-getter-componentName:end
  public String getComponentName() {
    return this.mComponentName;
  }

  // @anchor:annotations-setter-componentName:start
  // @anchor:annotations-setter-componentName:end
  // anchor:custom-annotations-setter-componentName:start
  // anchor:custom-annotations-setter-componentName:end
  public void setComponentName(String componentName) {
    this.mComponentName = componentName;
  }

  // @anchor:annotations-getter-className:start
  // @anchor:annotations-getter-className:end
  // anchor:custom-annotations-getter-className:start
  // anchor:custom-annotations-getter-className:end
  public String getClassName() {
    return this.mClassName;
  }

  // @anchor:annotations-setter-className:start
  // @anchor:annotations-setter-className:end
  // anchor:custom-annotations-setter-className:start
  // anchor:custom-annotations-setter-className:end
  public void setClassName(String className) {
    this.mClassName = className;
  }

  // @anchor:annotations-getter-responsible:start
  @Column(name="responsible_id")
  // @anchor:annotations-getter-responsible:end
  // anchor:custom-annotations-getter-responsible:start
  // anchor:custom-annotations-getter-responsible:end
  public Long getResponsible() {
    return this.mResponsible;
  }

  // @anchor:annotations-setter-responsible:start
  // @anchor:annotations-setter-responsible:end
  // anchor:custom-annotations-setter-responsible:start
  // anchor:custom-annotations-setter-responsible:end
  public void setResponsible(Long responsible) {
    this.mResponsible = responsible;
  }

  // @anchor:annotations-getter-sequencingStrategy:start
  // @anchor:annotations-getter-sequencingStrategy:end
  // anchor:custom-annotations-getter-sequencingStrategy:start
  // anchor:custom-annotations-getter-sequencingStrategy:end
  public String getSequencingStrategy() {
    return this.mSequencingStrategy;
  }

  // @anchor:annotations-setter-sequencingStrategy:start
  // @anchor:annotations-setter-sequencingStrategy:end
  // anchor:custom-annotations-setter-sequencingStrategy:start
  // anchor:custom-annotations-setter-sequencingStrategy:end
  public void setSequencingStrategy(String sequencingStrategy) {
    this.mSequencingStrategy = sequencingStrategy;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
