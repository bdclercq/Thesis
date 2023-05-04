package net.democritus.acl;

// @feature:authorization
public class FunctionalityDescriptor {

  private String component;
  private String element;
  private String functionality;

  public String getComponent() {
    return component;
  }

  public FunctionalityDescriptor setComponent(String component) {
    this.component = component;
    return this;
  }

  public String getElement() {
    return element;
  }

  public FunctionalityDescriptor setElement(String element) {
    this.element = element;
    return this;
  }

  public String getFunctionality() {
    return functionality;
  }

  public FunctionalityDescriptor setFunctionality(String functionality) {
    this.functionality = functionality;
    return this;
  }

}
