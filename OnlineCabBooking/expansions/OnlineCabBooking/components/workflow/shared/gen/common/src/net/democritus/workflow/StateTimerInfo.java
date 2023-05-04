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

public class StateTimerInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mProcessor;
  private String mBeginState;
  private String mTargetState;
  private Long mAllowedPeriod;
  private String mRequiredAction;
  private DataRef mWorkflow;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public StateTimerInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mProcessor = "";
    this.mBeginState = "";
    this.mTargetState = "";
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

  public StateTimerInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String processor
      , String beginState
      , String targetState
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
    this.mBeginState = beginState;
    this.mTargetState = targetState;
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

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("Name");
    fieldOrder.add("Processor");
    fieldOrder.add("BeginState");
    fieldOrder.add("TargetState");
    fieldOrder.add("AllowedPeriod");
    fieldOrder.add("RequiredAction");
    fieldOrder.add("Workflow");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.workflow", "StateTimer");
  }
}
