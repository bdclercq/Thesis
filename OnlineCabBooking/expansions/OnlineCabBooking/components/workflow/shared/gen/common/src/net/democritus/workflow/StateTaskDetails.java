package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.io.Serializable;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;
import java.util.ArrayList;
import java.util.List;

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean StateTask,
 */

public class StateTaskDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mProcessor;
  private String mImplementation;
  private String mParams;
  private String mBeginState;
  private String mInterimState;
  private String mFailedState;
  private String mEndState;
  private DataRef mWorkflow;
  private Integer mMaxConcurrentTasks;
  private Long mTimeout;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public StateTaskDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mProcessor = "";
    this.mImplementation = "";
    this.mParams = "";
    this.mBeginState = "";
    this.mInterimState = "";
    this.mFailedState = "";
    this.mEndState = "";
    this.mWorkflow = DataRef.withId(0L);
    this.mMaxConcurrentTasks = null;
    this.mTimeout = null;
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public StateTaskDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String processor
      , String implementation
      , String params
      , String beginState
      , String interimState
      , String failedState
      , String endState
      , DataRef workflow
      , Integer maxConcurrentTasks
      , Long timeout
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
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
    // anchor:detailed-constructor-initialization:end
    // @anchor:detailed-constructor-initialization:start
    // @anchor:detailed-constructor-initialization:end

    // anchor:custom-detail-constructor:start
    // anchor:custom-detail-constructor:end
  }

  /*========== Getters and Setters ==========*/

  public Long getId() {
    return this.mId;
  }

  public void setId(Long id) {
    this.mId = id;
  }

  public String getElementName() {
    return "StateTask";
  }

  public String getElementPackage() {
    return "net.democritus.workflow";
  }
  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getProcessor() {
    return this.mProcessor;
  }

  public void setProcessor(String processor) {
    this.mProcessor = processor;
  }

  public String getImplementation() {
    return this.mImplementation;
  }

  public void setImplementation(String implementation) {
    this.mImplementation = implementation;
  }

  public String getParams() {
    return this.mParams;
  }

  public void setParams(String params) {
    this.mParams = params;
  }

  public String getBeginState() {
    return this.mBeginState;
  }

  public void setBeginState(String beginState) {
    this.mBeginState = beginState;
  }

  public String getInterimState() {
    return this.mInterimState;
  }

  public void setInterimState(String interimState) {
    this.mInterimState = interimState;
  }

  public String getFailedState() {
    return this.mFailedState;
  }

  public void setFailedState(String failedState) {
    this.mFailedState = failedState;
  }

  public String getEndState() {
    return this.mEndState;
  }

  public void setEndState(String endState) {
    this.mEndState = endState;
  }

  public DataRef getWorkflow() {
    return this.mWorkflow;
  }

  public void setWorkflow(DataRef workflow) {
    this.mWorkflow = workflow;
  }

  public Integer getMaxConcurrentTasks() {
    return this.mMaxConcurrentTasks;
  }

  public void setMaxConcurrentTasks(Integer maxConcurrentTasks) {
    this.mMaxConcurrentTasks = maxConcurrentTasks;
  }

  public Long getTimeout() {
    return this.mTimeout;
  }

  public void setTimeout(Long timeout) {
    this.mTimeout = timeout;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.workflow", "StateTask");
  }
}
