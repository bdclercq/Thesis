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
 * Transport detailed class for the entity bean TimeTask,
 */

public class TimeTaskDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mProcessor;
  private String mImplementation;
  private String mParams;
  private String mTriggerState;
  private Long mIntervalPeriod;
  private String mRequiredAction;
  private DataRef mWorkflow;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TimeTaskDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mProcessor = "";
    this.mImplementation = "";
    this.mParams = "";
    this.mTriggerState = "";
    this.mIntervalPeriod = null;
    this.mRequiredAction = "";
    this.mWorkflow = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TimeTaskDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String processor
      , String implementation
      , String params
      , String triggerState
      , Long intervalPeriod
      , String requiredAction
      , DataRef workflow
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
    this.mTriggerState = triggerState;
    this.mIntervalPeriod = intervalPeriod;
    this.mRequiredAction = requiredAction;
    this.mWorkflow = workflow;
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
    return "TimeTask";
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

  public String getTriggerState() {
    return this.mTriggerState;
  }

  public void setTriggerState(String triggerState) {
    this.mTriggerState = triggerState;
  }

  public Long getIntervalPeriod() {
    return this.mIntervalPeriod;
  }

  public void setIntervalPeriod(Long intervalPeriod) {
    this.mIntervalPeriod = intervalPeriod;
  }

  public String getRequiredAction() {
    return this.mRequiredAction;
  }

  public void setRequiredAction(String requiredAction) {
    this.mRequiredAction = requiredAction;
  }

  public DataRef getWorkflow() {
    return this.mWorkflow;
  }

  public void setWorkflow(DataRef workflow) {
    this.mWorkflow = workflow;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.workflow", "TimeTask");
  }
}
