// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'paramTargetValue' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var paramTargetValueElement = nsxApplication.getElement("utils", "paramTargetValue");
  var paramTargetValueAgent = nsxAgent.createAgent(paramTargetValueElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllParamTargetValues";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findAllParamTargetValues");

    function find(options){
      var findOptions = {
        searchMethod: "findAllParamTargetValues",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
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

  function findByParamEq(options) {
    var name = "findByParamEq";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findByParamEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByParamEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByParamEq.fields = ["param"];
  findByParamEq.fieldOperatorPairs = ["param:eq"];

  function findByParamEq_TargetEq(options) {
    var name = "findByParamEq_TargetEq";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findByParamEq_TargetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByParamEq_TargetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByParamEq_TargetEq.fields = ["param", "target"];
  findByParamEq_TargetEq.fieldOperatorPairs = ["param:eq", "target:eq"];

  function findByParamEq_ValueEq(options) {
    var name = "findByParamEq_ValueEq";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findByParamEq_ValueEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByParamEq_ValueEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByParamEq_ValueEq.fields = ["param", "value"];
  findByParamEq_ValueEq.fieldOperatorPairs = ["param:eq", "value:eq"];

  function findByTargetEq(options) {
    var name = "findByTargetEq";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findByTargetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTargetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTargetEq.fields = ["target"];
  findByTargetEq.fieldOperatorPairs = ["target:eq"];

  function findByTargetEq_ValueEq(options) {
    var name = "findByTargetEq_ValueEq";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findByTargetEq_ValueEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTargetEq_ValueEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTargetEq_ValueEq.fields = ["target", "value"];
  findByTargetEq_ValueEq.fieldOperatorPairs = ["target:eq", "value:eq"];

  function findByValueEq(options) {
    var name = "findByValueEq";
    var component = "utils";
    var element = "paramTargetValue";
    var translation = translate("utils.paramTargetValue.findByValueEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByValueEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return paramTargetValueAgent.find(findOptions);
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
      findByParamEq,
      findByParamEq_TargetEq,
      findByParamEq_ValueEq,
      findByTargetEq,
      findByTargetEq_ValueEq,
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
    findByParamEq: findByParamEq,
    findByParamEq_TargetEq: findByParamEq_TargetEq,
    findByParamEq_ValueEq: findByParamEq_ValueEq,
    findByTargetEq: findByTargetEq,
    findByTargetEq_ValueEq: findByTargetEq_ValueEq,
    findByValueEq: findByValueEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
