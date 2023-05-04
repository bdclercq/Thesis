// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'tagValuePair' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var tagValuePairElement = nsxApplication.getElement("utils", "tagValuePair");
  var tagValuePairAgent = nsxAgent.createAgent(tagValuePairElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllTagValuePairs";
    var component = "utils";
    var element = "tagValuePair";
    var translation = translate("utils.tagValuePair.findAllTagValuePairs");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTagValuePairs",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tagValuePairAgent.find(findOptions);
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

  function findByTagEq(options) {
    var name = "findByTagEq";
    var component = "utils";
    var element = "tagValuePair";
    var translation = translate("utils.tagValuePair.findByTagEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTagEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tagValuePairAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTagEq.fields = ["tag"];
  findByTagEq.fieldOperatorPairs = ["tag:eq"];

  function findByTagEq_ValueEq(options) {
    var name = "findByTagEq_ValueEq";
    var component = "utils";
    var element = "tagValuePair";
    var translation = translate("utils.tagValuePair.findByTagEq_ValueEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTagEq_ValueEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tagValuePairAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTagEq_ValueEq.fields = ["tag", "value"];
  findByTagEq_ValueEq.fieldOperatorPairs = ["tag:eq", "value:eq"];

  function findByValueEq(options) {
    var name = "findByValueEq";
    var component = "utils";
    var element = "tagValuePair";
    var translation = translate("utils.tagValuePair.findByValueEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByValueEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tagValuePairAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByValueEq.fields = ["value"];
  findByValueEq.fieldOperatorPairs = ["value:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByTagEq,
      findByTagEq_ValueEq,
      findByValueEq
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
    findByTagEq: findByTagEq,
    findByTagEq_ValueEq: findByTagEq_ValueEq,
    findByValueEq: findByValueEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
