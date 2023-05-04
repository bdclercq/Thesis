// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'engineNode' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var engineNodeElement = nsxApplication.getElement("workflow", "engineNode");
  var engineNodeAgent = nsxAgent.createAgent(engineNodeElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllEngineNodes";
    var component = "workflow";
    var element = "engineNode";
    var translation = translate("workflow.engineNode.findAllEngineNodes");

    function find(options){
      var findOptions = {
        searchMethod: "findAllEngineNodes",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeAgent.find(findOptions);
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
    var component = "workflow";
    var element = "engineNode";
    var translation = translate("workflow.engineNode.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeAgent.find(findOptions);
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

  function findByMasterEq(options) {
    var name = "findByMasterEq";
    var component = "workflow";
    var element = "engineNode";
    var translation = translate("workflow.engineNode.findByMasterEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByMasterEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByMasterEq.fields = ["master"];
  findByMasterEq.fieldOperatorPairs = ["master:eq"];

  function findByMasterEq_LastActiveLt(options) {
    var name = "findByMasterEq_LastActiveLt";
    var component = "workflow";
    var element = "engineNode";
    var translation = translate("workflow.engineNode.findByMasterEq_LastActiveLt");

    function find(options){
      var findOptions = {
        searchMethod: "findByMasterEq_LastActiveLt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByMasterEq_LastActiveLt.fields = ["master", "lastActive"];
  findByMasterEq_LastActiveLt.fieldOperatorPairs = ["master:eq", "lastActive:lt"];

  function findByLastActiveLt(options) {
    var name = "findByLastActiveLt";
    var component = "workflow";
    var element = "engineNode";
    var translation = translate("workflow.engineNode.findByLastActiveLt");

    function find(options){
      var findOptions = {
        searchMethod: "findByLastActiveLt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByLastActiveLt.fields = ["lastActive"];
  findByLastActiveLt.fieldOperatorPairs = ["lastActive:lt"];

  function findByStatusEq(options) {
    var name = "findByStatusEq";
    var component = "workflow";
    var element = "engineNode";
    var translation = translate("workflow.engineNode.findByStatusEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStatusEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStatusEq.fields = ["status"];
  findByStatusEq.fieldOperatorPairs = ["status:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByMasterEq,
      findByMasterEq_LastActiveLt,
      findByLastActiveLt,
      findByStatusEq
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
    findByMasterEq: findByMasterEq,
    findByMasterEq_LastActiveLt: findByMasterEq_LastActiveLt,
    findByLastActiveLt: findByLastActiveLt,
    findByStatusEq: findByStatusEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
