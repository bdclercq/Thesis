package net.democritus.acl;

// @feature:authorization
public class AccessQueryBuilder {

  private String component;
  private String element;
  private String functionality;
  private String target;

  public AccessQueryBuilder setComponent(String component) {
    this.component = component;
    return this;
  }

  public AccessQueryBuilder setElement(String element) {
    this.element = element;
    return this;
  }

  public AccessQueryBuilder setFunctionality(String functionality) {
    this.functionality = functionality;
    return this;
  }

  public AccessQueryBuilder setFunctionality(DataAccessFunctionality accessFunctionality) {
    this.functionality = accessFunctionality.getFunctionality();
    return this;
  }

  public AccessQueryBuilder setTarget(String target) {
    this.target = target;
    return this;
  }

  public DataAccessQuery createAccessQuery() {
    DataAccessQuery accessQuery = new DataAccessQuery();
    accessQuery.setComponent(component);
    accessQuery.setElement(element);
    accessQuery.setFunctionality(functionality);
    accessQuery.setTarget(target);
    return accessQuery;
  }

}