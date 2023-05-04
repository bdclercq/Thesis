// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'internalAssetChunk' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var internalAssetChunkElement = nsxApplication.getElement("assets", "internalAssetChunk");
  var internalAssetChunkAgent = nsxAgent.createAgent(internalAssetChunkElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllInternalAssetChunks";
    var component = "assets";
    var element = "internalAssetChunk";
    var translation = translate("assets.internalAssetChunk.findAllInternalAssetChunks");

    function find(options){
      var findOptions = {
        searchMethod: "findAllInternalAssetChunks",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return internalAssetChunkAgent.find(findOptions);
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
    var element = "internalAssetChunk";
    var translation = translate("assets.internalAssetChunk.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return internalAssetChunkAgent.find(findOptions);
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

  function findByInternalAssetEq(options) {
    var name = "findByInternalAssetEq";
    var component = "assets";
    var element = "internalAssetChunk";
    var translation = translate("assets.internalAssetChunk.findByInternalAssetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByInternalAssetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return internalAssetChunkAgent.find(findOptions);
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

  function findByInternalAssetEq_PositionEq(options) {
    var name = "findByInternalAssetEq_PositionEq";
    var component = "assets";
    var element = "internalAssetChunk";
    var translation = translate("assets.internalAssetChunk.findByInternalAssetEq_PositionEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByInternalAssetEq_PositionEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return internalAssetChunkAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByInternalAssetEq_PositionEq.fields = ["internalAsset", "position"];
  findByInternalAssetEq_PositionEq.fieldOperatorPairs = ["internalAsset:eq", "position:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByInternalAssetEq,
      findByInternalAssetEq_PositionEq
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
    findByInternalAssetEq: findByInternalAssetEq,
    findByInternalAssetEq_PositionEq: findByInternalAssetEq_PositionEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
