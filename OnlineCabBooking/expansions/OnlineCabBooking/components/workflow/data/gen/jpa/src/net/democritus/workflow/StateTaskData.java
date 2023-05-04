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
 * Persistency pojo class for the entity StateTask,
 */

@Entity(name=StateTaskData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="StateTask", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=StateTaskData.QUERY_FINDALL, query="select o FROM " + StateTaskData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class StateTaskData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.workflow.StateTask";
  public static final String QUERY_FINDALL = "net.democritus.workflow.StateTask.findAll";
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
  private String mInterimState;
  private String mFailedState;
  private String mEndState;
  private Long mWorkflow;
  private Integer mMaxConcurrentTasks;
  private Long mTimeout;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public StateTaskData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public StateTaskData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String processor
      , String implementation
      , String params
      , String beginState
      , String interimState
      , String failedState
      , String endState
      , Long workflow
      , Integer maxConcurrentTasks
      , Long timeout
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
    this.mInterimState = interimState;
    this.mFailedState = failedState;
    this.mEndState = endState;
    this.mWorkflow = workflow;
    this.mMaxConcurrentTasks = maxConcurrentTasks;
    this.mTimeout = timeout;
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

  // @anchor:annotations-getter-interimState:start
  // @anchor:annotations-getter-interimState:end
  // anchor:custom-annotations-getter-interimState:start
  // anchor:custom-annotations-getter-interimState:end
  public String getInterimState() {
    return this.mInterimState;
  }

  // @anchor:annotations-setter-interimState:start
  // @anchor:annotations-setter-interimState:end
  // anchor:custom-annotations-setter-interimState:start
  // anchor:custom-annotations-setter-interimState:end
  public void setInterimState(String interimState) {
    this.mInterimState = interimState;
  }

  // @anchor:annotations-getter-failedState:start
  // @anchor:annotations-getter-failedState:end
  // anchor:custom-annotations-getter-failedState:start
  // anchor:custom-annotations-getter-failedState:end
  public String getFailedState() {
    return this.mFailedState;
  }

  // @anchor:annotations-setter-failedState:start
  // @anchor:annotations-setter-failedState:end
  // anchor:custom-annotations-setter-failedState:start
  // anchor:custom-annotations-setter-failedState:end
  public void setFailedState(String failedState) {
    this.mFailedState = failedState;
  }

  // @anchor:annotations-getter-endState:start
  // @anchor:annotations-getter-endState:end
  // anchor:custom-annotations-getter-endState:start
  // anchor:custom-annotations-getter-endState:end
  public String getEndState() {
    return this.mEndState;
  }

  // @anchor:annotations-setter-endState:start
  // @anchor:annotations-setter-endState:end
  // anchor:custom-annotations-setter-endState:start
  // anchor:custom-annotations-setter-endState:end
  public void setEndState(String endState) {
    this.mEndState = endState;
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

  // @anchor:annotations-getter-maxConcurrentTasks:start
  // @anchor:annotations-getter-maxConcurrentTasks:end
  // anchor:custom-annotations-getter-maxConcurrentTasks:start
  // anchor:custom-annotations-getter-maxConcurrentTasks:end
  public Integer getMaxConcurrentTasks() {
    return this.mMaxConcurrentTasks;
  }

  // @anchor:annotations-setter-maxConcurrentTasks:start
  // @anchor:annotations-setter-maxConcurrentTasks:end
  // anchor:custom-annotations-setter-maxConcurrentTasks:start
  // anchor:custom-annotations-setter-maxConcurrentTasks:end
  public void setMaxConcurrentTasks(Integer maxConcurrentTasks) {
    this.mMaxConcurrentTasks = maxConcurrentTasks;
  }

  // @anchor:annotations-getter-timeout:start
  // @anchor:annotations-getter-timeout:end
  // anchor:custom-annotations-getter-timeout:start
  // anchor:custom-annotations-getter-timeout:end
  public Long getTimeout() {
    return this.mTimeout;
  }

  // @anchor:annotations-setter-timeout:start
  // @anchor:annotations-setter-timeout:end
  // anchor:custom-annotations-setter-timeout:start
  // anchor:custom-annotations-setter-timeout:end
  public void setTimeout(Long timeout) {
    this.mTimeout = timeout;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
