package net.democritus.sys;

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
// anchor:imports:end

// anchor:custom-imports:start
import java.util.stream.Collectors;
// anchor:custom-imports:end

public class TagValuePairAgent implements TagValuePairAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(TagValuePairAgent.class);

  private static final TagValuePairProxy tagValuePairProxy = TagValuePairProxy.getTagValuePairProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private TagValuePairAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static TagValuePairAgent getTagValuePairAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new TagValuePairAgent(context, userContext);
  }

  @Deprecated
  public static TagValuePairAgent getTagValuePairAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new TagValuePairAgent(context, userContext);
  }

  @Deprecated
  public static TagValuePairAgent getTagValuePairAgent() {
    return getTagValuePairAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    TagValuePairFindAllTagValuePairsDetails finder = new TagValuePairFindAllTagValuePairsDetails();
    return tagValuePairProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<TagValuePairInfo> findAllInfos() {
    TagValuePairFindAllTagValuePairsDetails finder = new TagValuePairFindAllTagValuePairsDetails();
    return tagValuePairProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long tagValuePairId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(tagValuePairId));
    CrudsResult<DataRef> result = tagValuePairProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public TagValuePairInfo getInfo(Long tagValuePairId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(tagValuePairId));
    CrudsResult<TagValuePairInfo> result = tagValuePairProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new TagValuePairInfo();
    }
  }

  public TagValuePairDetails getDetails_old(Long tagValuePairId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(tagValuePairId));
    CrudsResult<TagValuePairDetails> result = tagValuePairProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new TagValuePairDetails();
    }
  }

  public CrudsResult<TagValuePairDetails> getDetails(Long tagValuePairId) {
    return tagValuePairProxy.getDetails(createParameter(tagValuePairId));
  }

  public CrudsResult<TagValuePairDetails> getDetails(DataRef dataRef) {
    return tagValuePairProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return tagValuePairProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return tagValuePairProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long tagValuePairId) {
    CrudsResult<String> result = tagValuePairProxy.getName(createParameter(tagValuePairId));
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

  public Long getId(String tagValuePairName) {
    CrudsResult<DataRef> result = tagValuePairProxy.getId(createParameter(tagValuePairName));
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
    return tagValuePairProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new TagValuePair instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(TagValuePairDetails details) {
    return tagValuePairProxy.create(createParameter(details));
  }

  /**
   * Modify an existing TagValuePair instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(TagValuePairDetails details) {
    return tagValuePairProxy.modify(createParameter(details));
  }

  /**
   * Create a TagValuePair instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return tagValuePairProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target TagValuePair instance by id.
   *
   * @deprecated Use {@link TagValuePairAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return tagValuePairProxy.delete(createParameter(oid));
  }

  /**
   * Delete target TagValuePair instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return tagValuePairProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the TagValuePair element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends TagValuePairFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return tagValuePairProxy.find(createParameter(searchDetails));
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
  // @feature:deprecated:start
  private static final String[] YES_NO = {"yes", "no"};

  @Deprecated
  public String[] getTagValueList(String tag) {
    try {
      if (tag.equals("yesNo")) {
        return YES_NO;
      } else {
        TagValuePairFindByTagEqDetails finder = new TagValuePairFindByTagEqDetails();
        finder.setTag(tag);
        SearchResult<DataRef> result = find(SearchDetails.fetchAllDataRef(finder));

        List<String> valueList = new ArrayList<String>();

        for (DataRef dataRef : result.getResults()) {
          TagValuePairDetails tvpDetails = getDetails_old(dataRef.getId());
          valueList.add(tvpDetails.getValue());
        }
        return valueList.toArray(new String[valueList.size()]);
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve tag-value pair parameters", e
        );
      }
      return new String[0];
    }
  }
  // @feature:deprecated:end
  // @feature:tag-value:start
  /**
   * Returns all values for the target Tag.
   *
   * @param tag Tag to search by, usually structured as `dataElementName_fieldName`, with the DataElement name starting
   *            with lowercase. Mind that the tag is case-sensitive.
   * @return SearchResult.success with a list of Strings if successful, SearchResult.error if an unexpected error occurred
   */
  public SearchResult<String> getValuesForTag(String tag) {
    if (tag == null || tag.isEmpty()) {
      return SearchResult.error(Diagnostic.error("utils", "TagValuePair", "utils.tagValuePair.tag.empty"));
    }
    try {
      TagValuePairFindByTagEqDetails finder = new TagValuePairFindByTagEqDetails();
      finder.setTag(tag);
      SearchDetails<TagValuePairFindByTagEqDetails> searchDetails = SearchDetails.fetchAllDetails(finder);
      searchDetails.setSkipCount(true);
      SearchResult<TagValuePairDetails> searchResult = find(searchDetails);
      if (searchResult.isError()) {
        return SearchResult.error(searchResult.getDiagnostics());
      }
      return SearchResult.success(
          searchResult.getResults().stream()
              .map(TagValuePairDetails::getValue)
              .collect(Collectors.toList()));
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve tag-value pairs", e
        );
      }
      return SearchResult.error(Diagnostic.error("utils", "TagValuePair", "utils.tagValuePair.findFailed"));
    }
  }
  // @feature:tag-value:end
  // @feature:listField-validation:start
  /**
   * Checks if the tag-value combination exists
   *
   * @param tag   Tag to search by, usually structured as `dataElementName_fieldName`, with the DataElement name starting
   *              with lowercase. Mind that the tag is case-sensitive.
   * @param value Provided value
   * @return CrudsResult.success if the TagValuePair exists, CrudsResult.error otherwise
   */
  public CrudsResult<Void> isValidTagValuePair(String tag, String value) {
    try {
      if (tag == null || tag.isEmpty()) {
        return CrudsResult.error(Diagnostic.error("utils", "TagValuePair", "utils.tagValuePair.tag.empty"));
      }
      TagValuePairFindByTagEq_ValueEqDetails finder = new TagValuePairFindByTagEq_ValueEqDetails();
      finder.setTag(tag);
      finder.setValue(value);

      SearchResult<Object> searchResult = find(SearchDetails.doCountOnly(finder));
      if (searchResult.isError()) {
        return CrudsResult.error(searchResult.getDiagnostics());
      }
      return searchResult.getTotalNumberOfItems() > 0 ? CrudsResult.success() : CrudsResult.error();
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve tag-value pairs", e
        );
      }
      return CrudsResult.error(Diagnostic.error("utils", "TagValuePair", "utils.tagValuePair.findFailed"));
    }
  }
  // @feature:listField-validation:end
  // anchor:custom-methods:end
}

