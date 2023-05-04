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
 * Transport detailed class for the entity bean StateTimer,
 */

public class StateTimerDetails
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
  private String mTargetState;
  private String mAlteredState;
  private Long mAllowedPeriod;
  private String mRequiredAction;
  private DataRef mWorkflow;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public StateTimerDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mProcessor = "";
    this.mImplementation = "";
    this.mParams = "";
    this.mBeginState = "";
    this.mTargetState = "";
    this.mAlteredState = "";
    this.mAllowedPeriod = null;
    this.mRequiredAction = "";
    this.mWorkflow = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public StateTimerDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String processor
      , String implementation
      , String params
      , String beginState
      , String targetState
      , String alteredState
      , Long allowedPeriod
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
    this.mBeginState = beginState;
    this.mTargetState = targetState;
    this.mAlteredState = alteredState;
    this.mAllowedPeriod = allowedPeriod;
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
    return "StateTimer";
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

  public String getTargetState() {
    return this.mTargetState;
  }

  public void setTargetState(String targetState) {
    this.mTargetState = targetState;
  }

  public String getAlteredState() {
    return this.mAlteredState;
  }

  public void setAlteredState(String alteredState) {
    this.mAlteredState = alteredState;
  }

  public Long getAllowedPeriod() {
    return this.mAllowedPeriod;
  }

  public void setAllowedPeriod(Long allowedPeriod) {
    this.mAllowedPeriod = allowedPeriod;
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
    return new DataRef(mId, mName, "workflow", "net.democritus.workflow", "StateTimer");
  }
}
