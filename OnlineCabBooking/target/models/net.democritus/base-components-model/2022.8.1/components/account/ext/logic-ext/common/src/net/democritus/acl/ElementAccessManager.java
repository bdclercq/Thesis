package net.democritus.acl;

import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

import java.util.ArrayList;
import java.util.List;

// @feature:authorization
public class ElementAccessManager {

  private final Context context;
  private final AccountInformationManager accountInformationManager;

  public ElementAccessManager(Context context) {
    this.context = context;
    this.accountInformationManager = new AccountInformationManager(context);
  }

  public List<DataAccessDetails> getUserDataAccess(FunctionalityDescriptor functionalityDescriptor) throws Exception {
    DataAccessFindBySpecificationOrWildcardDetails findDetails = new DataAccessFindBySpecificationOrWildcardDetails();
    findDetails.setComponent(functionalityDescriptor.getComponent());
    findDetails.setElement(functionalityDescriptor.getElement());
    findDetails.setFunctionality(functionalityDescriptor.getFunctionality());
    findDetails.setForUser(accountInformationManager.getUser());
    SearchDetails<DataAccessFindBySpecificationOrWildcardDetails> searchDetails =
        SearchDetails.fetchAllDetails(findDetails);
    DataAccessLocalAgent agent = DataAccessLocalAgent.getDataAccessAgent(context);
    SearchResult<DataAccessDetails> searchResult = agent.find(searchDetails);
    if (searchResult.isError()) {
      throw new Exception("error finding dataAccess in database for element '" + functionalityDescriptor.getElement() + "' and functionality '");
    }
    return searchResult.getResults();
  }

  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getUserDataAccess(FunctionalityDescriptor)} has been introduced which can be used to find the rights for an
   * element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getUserDataAccess(String element) throws Exception {
    return getUserDataAccess(new FunctionalityDescriptor().setElement(element));
  }

  public List<DataAccessDetails> getProfileDataAccess(FunctionalityDescriptor functionalityDescriptor) throws Exception {
    DataRef profile = accountInformationManager.getProfile();
    if (profile == null || profile.getId().equals(0L)) {
      return new ArrayList<>();
    }
    DataAccessFindBySpecificationOrWildcardDetails findDetails = new DataAccessFindBySpecificationOrWildcardDetails();
    findDetails.setComponent(functionalityDescriptor.getComponent());
    findDetails.setElement(functionalityDescriptor.getElement());
    findDetails.setFunctionality(functionalityDescriptor.getFunctionality());
    findDetails.setForProfile(accountInformationManager.getProfile());
    SearchDetails<DataAccessFindBySpecificationOrWildcardDetails> searchDetails =
        SearchDetails.fetchAllDetails(findDetails);
    DataAccessLocalAgent agent = DataAccessLocalAgent.getDataAccessAgent(context);
    SearchResult<DataAccessDetails> searchResult = agent.find(searchDetails);
    if (searchResult.isError()) {
      throw new Exception("error finding dataAccess in database for element '" + functionalityDescriptor.getFunctionality() + "' and functionality '");
    }
    return searchResult.getResults();
  }


  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getProfileDataAccess(FunctionalityDescriptor)} has been introduced which can be used to find the rights for
   * an element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getProfileDataAccess(String element) throws Exception {
    return getProfileDataAccess(new FunctionalityDescriptor().setElement(element));
  }

  public List<DataAccessDetails> getUserGroupDataAccess(FunctionalityDescriptor functionalityDescriptor) throws Exception {
    List<DataAccessDetails> list = new ArrayList<>();
    for (String userGroup : accountInformationManager.getUserGroups()) {
      list.addAll(getUserGroupDataAccess(userGroup, functionalityDescriptor));
    }
    return list;
  }

  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getUserGroupDataAccess(FunctionalityDescriptor)} has been introduced which can be used to find the rights
   * for an element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getUserGroupDataAccess(String element) throws Exception {
    return getUserGroupDataAccess(new FunctionalityDescriptor().setElement(element));
  }

  public List<DataAccessDetails> getUserGroupDataAccess(String userGroup, FunctionalityDescriptor functionalityDescriptor) throws Exception {
    DataAccessFindBySpecificationOrWildcardDetails findDetails = new DataAccessFindBySpecificationOrWildcardDetails();
    findDetails.setComponent(functionalityDescriptor.getComponent());
    findDetails.setElement(functionalityDescriptor.getElement());
    findDetails.setFunctionality(functionalityDescriptor.getFunctionality());
    findDetails.setForUserGroup(accountInformationManager.getUserGroup(userGroup));
    SearchDetails<DataAccessFindBySpecificationOrWildcardDetails> searchDetails =
        SearchDetails.fetchAllDetails(findDetails);
    DataAccessLocalAgent agent = DataAccessLocalAgent.getDataAccessAgent(context);
    SearchResult<DataAccessDetails> searchResult = agent.find(searchDetails);
    if (searchResult.isError()) {
      throw new Exception("error finding dataAccess in database for element '" + functionalityDescriptor.getElement() + "' and functionality '");
    }
    return searchResult.getResults();
  }

  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getUserGroupDataAccess(String, FunctionalityDescriptor)} has been introduced which can be used to find the
   * rights for an element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getUserGroupDataAccess(String userGroup, String element) throws Exception {
    return getUserGroupDataAccess(userGroup, new FunctionalityDescriptor().setElement(element));
  }

  public List<DataAccessDetails> getUserTaskAccess(FunctionalityDescriptor functionalityDescriptor) throws Exception {
    return getUserDataAccess(functionalityDescriptor);
  }

  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getUserTaskAccess(FunctionalityDescriptor)} has been introduced which can be used to find the rights for a
   * task element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getUserTaskAccess(String taskElement) throws Exception {
    return getUserTaskAccess(new FunctionalityDescriptor().setElement(taskElement));
  }

  public List<DataAccessDetails> getProfileTaskAccess(FunctionalityDescriptor functionalityDescriptor) throws Exception {
    return getProfileDataAccess(functionalityDescriptor);
  }

  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getProfileTaskAccess(FunctionalityDescriptor)} has been introduced which can be used to find the rights for
   * a task element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getProfileTaskAccess(String taskElement) throws Exception {
    return getProfileTaskAccess(new FunctionalityDescriptor().setElement(taskElement));
  }

  public List<DataAccessDetails> getUserGroupTaskAccess(FunctionalityDescriptor functionalityDescriptor) throws Exception {
    return getUserGroupDataAccess(functionalityDescriptor);
  }

  /**
   * @deprecated A new implementation allows you to specify the component name in the {@code DataAccess.element} field.
   * This is formatted as {@code componentName_dataElementName}. Therefore a new method
   * {@link #getUserGroupTaskAccess(FunctionalityDescriptor)} has been introduced which can be used to find the rights
   * for a task element in a specific component (as well as a specific functionality).
   */
  @Deprecated
  public List<DataAccessDetails> getUserGroupTaskAccess(String taskElement) throws Exception {
    return getUserGroupTaskAccess(new FunctionalityDescriptor().setElement(taskElement));
  }

}
