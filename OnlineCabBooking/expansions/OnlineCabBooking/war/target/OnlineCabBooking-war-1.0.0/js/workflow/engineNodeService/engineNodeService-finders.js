// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'engineNodeService' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var engineNodeServiceElement = nsxApplication.getElement("workflow", "engineNodeService");
  var engineNodeServiceAgent = nsxAgent.createAgent(engineNodeServiceElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllEngineNodeServices";
    var component = "workflow";
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findAllEngineNodeServices");

    function find(options){
      var findOptions = {
        searchMethod: "findAllEngineNodeServices",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
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
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
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

  function findByStatusEq(options) {
    var name = "findByStatusEq";
    var component = "workflow";
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findByStatusEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStatusEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
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

  function findByEngineNodeEq(options) {
    var name = "findByEngineNodeEq";
    var component = "workflow";
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findByEngineNodeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEngineNodeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByEngineNodeEq.fields = ["engineNode"];
  findByEngineNodeEq.fieldOperatorPairs = ["engineNode:eq"];

  function findByEngineServiceEq_StatusNe(options) {
    var name = "findByEngineServiceEq_StatusNe";
    var component = "workflow";
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findByEngineServiceEq_StatusNe");

    function find(options){
      var findOptions = {
        searchMethod: "findByEngineServiceEq_StatusNe",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByEngineServiceEq_StatusNe.fields = ["engineService", "status"];
  findByEngineServiceEq_StatusNe.fieldOperatorPairs = ["engineService:eq", "status:ne"];

  function findByEngineServiceEq(options) {
    var name = "findByEngineServiceEq";
    var component = "workflow";
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findByEngineServiceEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEngineServiceEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByEngineServiceEq.fields = ["engineService"];
  findByEngineServiceEq.fieldOperatorPairs = ["engineService:eq"];

  function findByEngineNodeEq_EngineServiceEq(options) {
    var name = "findByEngineNodeEq_EngineServiceEq";
    var component = "workflow";
    var element = "engineNodeService";
    var translation = translate("workflow.engineNodeService.findByEngineNodeEq_EngineServiceEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEngineNodeEq_EngineServiceEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineNodeServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByEngineNodeEq_EngineServiceEq.fields = ["engineNode", "engineService"];
  findByEngineNodeEq_EngineServiceEq.fieldOperatorPairs = ["engineNode:eq", "engineService:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByStatusEq,
      findByEngineNodeEq,
      findByEngineServiceEq_StatusNe,
      findByEngineServiceEq,
      findByEngineNodeEq_EngineServiceEq
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
    findByStatusEq: findByStatusEq,
    findByEngineNodeEq: findByEngineNodeEq,
    findByEngineServiceEq_StatusNe: findByEngineServiceEq_StatusNe,
    findByEngineServiceEq: findByEngineServiceEq,
    findByEngineNodeEq_EngineServiceEq: findByEngineNodeEq_EngineServiceEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
