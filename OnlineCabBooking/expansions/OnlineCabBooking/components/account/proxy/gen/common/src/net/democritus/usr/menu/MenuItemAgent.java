package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.ArrayList;
import java.util.List;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;


import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;

import net.democritus.sys.search.SearchDetails;
import net.democritus.support.Paging;
// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.menu.MenuAgent;
import net.democritus.usr.menu.ScreenAgent;
import net.democritus.usr.menu.MenuItemAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class MenuItemAgent implements MenuItemAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(MenuItemAgent.class);

  private static final MenuItemProxy menuItemProxy = MenuItemProxy.getMenuItemProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private MenuItemAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static MenuItemAgent getMenuItemAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new MenuItemAgent(context, userContext);
  }

  @Deprecated
  public static MenuItemAgent getMenuItemAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new MenuItemAgent(context, userContext);
  }

  @Deprecated
  public static MenuItemAgent getMenuItemAgent() {
    return getMenuItemAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    MenuItemFindAllMenuItemsDetails finder = new MenuItemFindAllMenuItemsDetails();
    return menuItemProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<MenuItemInfo> findAllInfos() {
    MenuItemFindAllMenuItemsDetails finder = new MenuItemFindAllMenuItemsDetails();
    return menuItemProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long menuItemId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(menuItemId));
    CrudsResult<DataRef> result = menuItemProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public MenuItemInfo getInfo(Long menuItemId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(menuItemId));
    CrudsResult<MenuItemInfo> result = menuItemProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new MenuItemInfo();
    }
  }

  public MenuItemDetails getDetails_old(Long menuItemId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(menuItemId));
    CrudsResult<MenuItemDetails> result = menuItemProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new MenuItemDetails();
    }
  }

  public CrudsResult<MenuItemDetails> getDetails(Long menuItemId) {
    return menuItemProxy.getDetails(createParameter(menuItemId));
  }

  public CrudsResult<MenuItemDetails> getDetails(DataRef dataRef) {
    return menuItemProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return menuItemProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return menuItemProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long menuItemId) {
    CrudsResult<String> result = menuItemProxy.getName(createParameter(menuItemId));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      if (logger.isErrorEnabled()) {
          logger.error(
            "getName() failed"
          );
      }
      return "";
    }
  }

  public Long getId(String menuItemName) {
    CrudsResult<DataRef> result = menuItemProxy.getId(createParameter(menuItemName));
    if (result.isSuccess()) {
      return result.getValue().getId();
    } else {
      if (logger.isErrorEnabled()) {
          logger.error(
            "getId() failed"
          );
      }
      return null;
    }
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return menuItemProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new MenuItem instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(MenuItemDetails details) {
    return menuItemProxy.create(createParameter(details));
  }

  /**
   * Modify an existing MenuItem instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(MenuItemDetails details) {
    return menuItemProxy.modify(createParameter(details));
  }

  /**
   * Create a MenuItem instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return menuItemProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target MenuItem instance by id.
   *
   * @deprecated Use {@link MenuItemAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return menuItemProxy.delete(createParameter(oid));
  }

  /**
   * Delete target MenuItem instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return menuItemProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the MenuItem element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends MenuItemFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return menuItemProxy.find(createParameter(searchDetails));
  }

  /*========== Context ==========*/

  private <T> ParameterContext<T> createParameter(T value) {
    return mContext.withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

