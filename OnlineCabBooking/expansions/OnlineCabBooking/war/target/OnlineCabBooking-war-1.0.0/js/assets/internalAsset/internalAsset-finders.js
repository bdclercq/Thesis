// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'internalAsset' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var internalAssetElement = nsxApplication.getElement("assets", "internalAsset");
  var internalAssetAgent = nsxAgent.createAgent(internalAssetElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllInternalAssets";
    var component = "assets";
    var element = "internalAsset";
    var translation = translate("assets.internalAsset.findAllInternalAssets");

    function find(options){
      var findOptions = {
        searchMethod: "findAllInternalAssets",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return internalAssetAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findAll.fields = [];
  findAll.fieldOperatorPairs = [];

  function findByNameEq(options) {
    var name = "findByNameEq";
    var component = "assets";
    var element = "internalAsset";
    var translation = translate("assets.internalAsset.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return internalAssetAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByNameEq.fields = ["name"];
  findByNameEq.fieldOperatorPairs = ["name:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq
      // anchor:getAllFinders:end
    ]
    // @anchor:getAllFinders:start
    // @anchor:getAllFinders:end
    // anchor:custom-getAllFinders:start
    // anchor:custom-getAllFinders:end
    return allFinders;
  }

  function getAllFindersWith(fieldNames){
    return getAllFinders().filter(function(finder) {
      return utils.arrayContainsAll(finder.fields, fieldNames)
          || utils.arrayContainsAll(finder.fieldOperatorPairs, fieldNames);
    });
  }

  function getFinderWith(fieldNames){
    if (fieldNames.length == 0) return findAll();
    var finders = getAllFindersWith(fieldNames);
    var finder = utils.findMin(finders, function numberOfFields(finder) {
      return finder.fields.length;
    })
    return finder == null ? null : finder();
  }

  return {
    // anchor:finder-returns:start
    findAll: findAll,
    findByNameEq: findByNameEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
