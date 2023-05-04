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
 * Persistency pojo class for the entity StateTimer,
 */

@Entity(name=StateTimerData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="StateTimer", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=StateTimerData.QUERY_FINDALL, query="select o FROM " + StateTimerData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class StateTimerData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.workflow.StateTimer";
  public static final String QUERY_FINDALL = "net.democritus.workflow.StateTimer.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mProcessor;
  private String mImplementation;
  private String mParams;
  private String mBeginState;
  private String mTargetState;
  private String mAlteredState;
  private Long mAllowedPeriod;
  private String mRequiredAction;
  private Long mWorkflow;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public StateTimerData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public StateTimerData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String processor
      , String implementation
      , String params
      , String beginState
      , String targetState
      , String alteredState
      , Long allowedPeriod
      , String requiredAction
      , Long workflow
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mProcessor = processor;
    this.mImplementation = implementation;
    this.mParams = params;
    this.mBeginState = beginState;
    this.mTargetState = targetState;
    this.mAlteredState = alteredState;
    this.mAllowedPeriod = allowedPeriod;
    this.mRequiredAction = requiredAction;
    this.mWorkflow = workflow;
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

  // @anchor:annotations-getter-processor:start
  // @anchor:annotations-getter-processor:end
  // anchor:custom-annotations-getter-processor:start
  // anchor:custom-annotations-getter-processor:end
  public String getProcessor() {
    return this.mProcessor;
  }

  // @anchor:annotations-setter-processor:start
  // @anchor:annotations-setter-processor:end
  // anchor:custom-annotations-setter-processor:start
  // anchor:custom-annotations-setter-processor:end
  public void setProcessor(String processor) {
    this.mProcessor = processor;
  }

  // @anchor:annotations-getter-implementation:start
  // @anchor:annotations-getter-implementation:end
  // anchor:custom-annotations-getter-implementation:start
  // anchor:custom-annotations-getter-implementation:end
  public String getImplementation() {
    return this.mImplementation;
  }

  // @anchor:annotations-setter-implementation:start
  // @anchor:annotations-setter-implementation:end
  // anchor:custom-annotations-setter-implementation:start
  // anchor:custom-annotations-setter-implementation:end
  public void setImplementation(String implementation) {
    this.mImplementation = implementation;
  }

  // @anchor:annotations-getter-params:start
  // @anchor:annotations-getter-params:end
  // anchor:custom-annotations-getter-params:start
  // anchor:custom-annotations-getter-params:end
  public String getParams() {
    return this.mParams;
  }

  // @anchor:annotations-setter-params:start
  // @anchor:annotations-setter-params:end
  // anchor:custom-annotations-setter-params:start
  // anchor:custom-annotations-setter-params:end
  public void setParams(String params) {
    this.mParams = params;
  }

  // @anchor:annotations-getter-beginState:start
  // @anchor:annotations-getter-beginState:end
  // anchor:custom-annotations-getter-beginState:start
  // anchor:custom-annotations-getter-beginState:end
  public String getBeginState() {
    return this.mBeginState;
  }

  // @anchor:annotations-setter-beginState:start
  // @anchor:annotations-setter-beginState:end
  // anchor:custom-annotations-setter-beginState:start
  // anchor:custom-annotations-setter-beginState:end
  public void setBeginState(String beginState) {
    this.mBeginState = beginState;
  }

  // @anchor:annotations-getter-targetState:start
  // @anchor:annotations-getter-targetState:end
  // anchor:custom-annotations-getter-targetState:start
  // anchor:custom-annotations-getter-targetState:end
  public String getTargetState() {
    return this.mTargetState;
  }

  // @anchor:annotations-setter-targetState:start
  // @anchor:annotations-setter-targetState:end
  // anchor:custom-annotations-setter-targetState:start
  // anchor:custom-annotations-setter-targetState:end
  public void setTargetState(String targetState) {
    this.mTargetState = targetState;
  }

  // @anchor:annotations-getter-alteredState:start
  // @anchor:annotations-getter-alteredState:end
  // anchor:custom-annotations-getter-alteredState:start
  // anchor:custom-annotations-getter-alteredState:end
  public String getAlteredState() {
    return this.mAlteredState;
  }

  // @anchor:annotations-setter-alteredState:start
  // @anchor:annotations-setter-alteredState:end
  // anchor:custom-annotations-setter-alteredState:start
  // anchor:custom-annotations-setter-alteredState:end
  public void setAlteredState(String alteredState) {
    this.mAlteredState = alteredState;
  }

  // @anchor:annotations-getter-allowedPeriod:start
  // @anchor:annotations-getter-allowedPeriod:end
  // anchor:custom-annotations-getter-allowedPeriod:start
  // anchor:custom-annotations-getter-allowedPeriod:end
  public Long getAllowedPeriod() {
    return this.mAllowedPeriod;
  }

  // @anchor:annotations-setter-allowedPeriod:start
  // @anchor:annotations-setter-allowedPeriod:end
  // anchor:custom-annotations-setter-allowedPeriod:start
  // anchor:custom-annotations-setter-allowedPeriod:end
  public void setAllowedPeriod(Long allowedPeriod) {
    this.mAllowedPeriod = allowedPeriod;
  }

  // @anchor:annotations-getter-requiredAction:start
  // @anchor:annotations-getter-requiredAction:end
  // anchor:custom-annotations-getter-requiredAction:start
  // anchor:custom-annotations-getter-requiredAction:end
  public String getRequiredAction() {
    return this.mRequiredAction;
  }

  // @anchor:annotations-setter-requiredAction:start
  // @anchor:annotations-setter-requiredAction:end
  // anchor:custom-annotations-setter-requiredAction:start
  // anchor:custom-annotations-setter-requiredAction:end
  public void setRequiredAction(String requiredAction) {
    this.mRequiredAction = requiredAction;
  }

  // @anchor:annotations-getter-workflow:start
  @Column(name="workflow_id")
  // @anchor:annotations-getter-workflow:end
  // anchor:custom-annotations-getter-workflow:start
  // anchor:custom-annotations-getter-workflow:end
  public Long getWorkflow() {
    return this.mWorkflow;
  }

  // @anchor:annotations-setter-workflow:start
  // @anchor:annotations-setter-workflow:end
  // anchor:custom-annotations-setter-workflow:start
  // anchor:custom-annotations-setter-workflow:end
  public void setWorkflow(Long workflow) {
    this.mWorkflow = workflow;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
