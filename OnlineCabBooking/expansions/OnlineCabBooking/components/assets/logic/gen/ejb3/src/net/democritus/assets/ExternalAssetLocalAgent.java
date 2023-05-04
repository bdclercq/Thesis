package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.search.SearchDetails;
import net.democritus.support.Paging;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ExternalAssetLocalAgent implements ExternalAssetAgentIf {

  private final ExternalAssetLocal externalAssetLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static ExternalAssetLocalAgent getExternalAssetAgent(UserContext userContext) {
    return new ExternalAssetLocalAgent(getLocalInstance(), userContext);
  }

  public static ExternalAssetLocalAgent getExternalAssetAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new ExternalAssetLocalAgent(getLocalInstance(), userContext, context);
  }

  private static ExternalAssetLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("assets");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("ExternalAsset");
      }

      return (ExternalAssetLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.assets.ExternalAssetLocal'", e);
    }
  }

  public ExternalAssetLocalAgent(ExternalAssetLocal externalAssetLocal, UserContext userContext) {
    this.externalAssetLocal = externalAssetLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public ExternalAssetLocalAgent(ExternalAssetLocal externalAssetLocal, UserContext userContext, Context context) {
    this.externalAssetLocal = externalAssetLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new ExternalAsset instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(ExternalAssetDetails externalAssetDetails) {
    return externalAssetLocal.create(createParameter(externalAssetDetails));
  }

  /**
   * Modify an existing ExternalAsset instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(ExternalAssetDetails externalAssetDetails) {
    return externalAssetLocal.modify(createParameter(externalAssetDetails));
  }

  /**
   * Create a ExternalAsset instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return externalAssetLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target ExternalAsset instance by id.
   *
   * @deprecated Use {@link ExternalAssetAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return externalAssetLocal.delete(createParameter(id));
  }

  /**
   * Delete target ExternalAsset instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return externalAssetLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return externalAssetLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return externalAssetLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<ExternalAssetDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the ExternalAsset element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends ExternalAssetFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return externalAssetLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<ExternalAssetDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    ExternalAssetFindAllExternalAssetsDetails finder = new ExternalAssetFindAllExternalAssetsDetails();
    return externalAssetLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<ExternalAssetInfo> findAllInfos() {
    ExternalAssetFindAllExternalAssetsDetails finder = new ExternalAssetFindAllExternalAssetsDetails();
    return externalAssetLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return externalAssetLocal.getId(createParameter(name));
  }

  private <T> ParameterContext<T> createParameter(T value) {
    return context.withParameter(value);
  }

  private ParameterContext<Void> createEmptyParameter() {
    return context.emptyParameter();
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
