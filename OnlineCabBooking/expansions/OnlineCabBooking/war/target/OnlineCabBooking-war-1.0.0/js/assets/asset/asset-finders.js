// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'asset' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var assetElement = nsxApplication.getElement("assets", "asset");
  var assetAgent = nsxAgent.createAgent(assetElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllAssets";
    var component = "assets";
    var element = "asset";
    var translation = translate("assets.asset.findAllAssets");

    function find(options){
      var findOptions = {
        searchMethod: "findAllAssets",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return assetAgent.find(findOptions);
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
    var element = "asset";
    var translation = translate("assets.asset.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return assetAgent.find(findOptions);
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

  function findByFileIdEq(options) {
    var name = "findByFileIdEq";
    var component = "assets";
    var element = "asset";
    var translation = translate("assets.asset.findByFileIdEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByFileIdEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return assetAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByFileIdEq.fields = ["fileId"];
  findByFileIdEq.fieldOperatorPairs = ["fileId:eq"];

  function findByFileAssetEq(options) {
    var name = "findByFileAssetEq";
    var component = "assets";
    var element = "asset";
    var translation = translate("assets.asset.findByFileAssetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByFileAssetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return assetAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByFileAssetEq.fields = ["fileAsset"];
  findByFileAssetEq.fieldOperatorPairs = ["fileAsset:eq"];

  function findByInternalAssetEq(options) {
    var name = "findByInternalAssetEq";
    var component = "assets";
    var element = "asset";
    var translation = translate("assets.asset.findByInternalAssetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByInternalAssetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return assetAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByInternalAssetEq.fields = ["internalAsset"];
  findByInternalAssetEq.fieldOperatorPairs = ["internalAsset:eq"];

  function findByExternalAssetEq(options) {
    var name = "findByExternalAssetEq";
    var component = "assets";
    var element = "asset";
    var translation = translate("assets.asset.findByExternalAssetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByExternalAssetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return assetAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByExternalAssetEq.fields = ["externalAsset"];
  findByExternalAssetEq.fieldOperatorPairs = ["externalAsset:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByFileIdEq,
      findByFileAssetEq,
      findByInternalAssetEq,
      findByExternalAssetEq
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
    findByFileIdEq: findByFileIdEq,
    findByFileAssetEq: findByFileAssetEq,
    findByInternalAssetEq: findByInternalAssetEq,
    findByExternalAssetEq: findByExternalAssetEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
