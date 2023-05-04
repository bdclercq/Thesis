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
 * Transport detailed class for the entity bean Workflow,
 */

public class WorkflowDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mTarget;
  private String mComponentName;
  private String mClassName;
  private DataRef mResponsible;
  private String mSequencingStrategy;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public WorkflowDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mTarget = "";
    this.mComponentName = "";
    this.mClassName = "";
    this.mResponsible = DataRef.withId(0L);
    this.mSequencingStrategy = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public WorkflowDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String target
      , String componentName
      , String className
      , DataRef responsible
      , String sequencingStrategy
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mTarget = target;
    this.mComponentName = componentName;
    this.mClassName = className;
    this.mResponsible = responsible;
    this.mSequencingStrategy = sequencingStrategy;
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
    return "Workflow";
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

  public String getTarget() {
    return this.mTarget;
  }

  public void setTarget(String target) {
    this.mTarget = target;
  }

  public String getComponentName() {
    return this.mComponentName;
  }

  public void setComponentName(String componentName) {
    this.mComponentName = componentName;
  }

  public String getClassName() {
    return this.mClassName;
  }

  public void setClassName(String className) {
    this.mClassName = className;
  }

  public DataRef getResponsible() {
    return this.mResponsible;
  }

  public void setResponsible(DataRef responsible) {
    this.mResponsible = responsible;
  }

  public String getSequencingStrategy() {
    return this.mSequencingStrategy;
  }

  public void setSequencingStrategy(String sequencingStrategy) {
    this.mSequencingStrategy = sequencingStrategy;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.workflow", "Workflow");
  }
}
