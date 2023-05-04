// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'engineService' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var engineServiceElement = nsxApplication.getElement("workflow", "engineService");
  var engineServiceAgent = nsxAgent.createAgent(engineServiceElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllEngineServices";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findAllEngineServices");

    function find(options){
      var findOptions = {
        searchMethod: "findAllEngineServices",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
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
    var element = "engineService";
    var translation = translate("workflow.engineService.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
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

  function findByBusyEq(options) {
    var name = "findByBusyEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByBusyEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByBusyEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByBusyEq.fields = ["busy"];
  findByBusyEq.fieldOperatorPairs = ["busy:eq"];

  function findByChangedEq(options) {
    var name = "findByChangedEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByChangedEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByChangedEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByChangedEq.fields = ["changed"];
  findByChangedEq.fieldOperatorPairs = ["changed:eq"];

  function findByCollectorEq(options) {
    var name = "findByCollectorEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByCollectorEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCollectorEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCollectorEq.fields = ["collector"];
  findByCollectorEq.fieldOperatorPairs = ["collector:eq"];

  function findByLastRunAtGt(options) {
    var name = "findByLastRunAtGt";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByLastRunAtGt");

    function find(options){
      var findOptions = {
        searchMethod: "findByLastRunAtGt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByLastRunAtGt.fields = ["lastRunAt"];
  findByLastRunAtGt.fieldOperatorPairs = ["lastRunAt:gt"];

  function findByLastRunAtLt(options) {
    var name = "findByLastRunAtLt";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByLastRunAtLt");

    function find(options){
      var findOptions = {
        searchMethod: "findByLastRunAtLt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByLastRunAtLt.fields = ["lastRunAt"];
  findByLastRunAtLt.fieldOperatorPairs = ["lastRunAt:lt"];

  function findByNameEq_CollectorEq(options) {
    var name = "findByNameEq_CollectorEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByNameEq_CollectorEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq_CollectorEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByNameEq_CollectorEq.fields = ["collector", "name"];
  findByNameEq_CollectorEq.fieldOperatorPairs = ["collector:eq", "name:eq"];

  function findByStatusEq(options) {
    var name = "findByStatusEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByStatusEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStatusEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
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

  function findByWorkflowEq(options) {
    var name = "findByWorkflowEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByWorkflowEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByWorkflowEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByWorkflowEq.fields = ["workflow"];
  findByWorkflowEq.fieldOperatorPairs = ["workflow:eq"];

  function findByTimeWindowGroupEq(options) {
    var name = "findByTimeWindowGroupEq";
    var component = "workflow";
    var element = "engineService";
    var translation = translate("workflow.engineService.findByTimeWindowGroupEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTimeWindowGroupEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return engineServiceAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTimeWindowGroupEq.fields = ["timeWindowGroup"];
  findByTimeWindowGroupEq.fieldOperatorPairs = ["timeWindowGroup:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByBusyEq,
      findByChangedEq,
      findByCollectorEq,
      findByLastRunAtGt,
      findByLastRunAtLt,
      findByNameEq_CollectorEq,
      findByStatusEq,
      findByWorkflowEq,
      findByTimeWindowGroupEq
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
    findByBusyEq: findByBusyEq,
    findByChangedEq: findByChangedEq,
    findByCollectorEq: findByCollectorEq,
    findByLastRunAtGt: findByLastRunAtGt,
    findByLastRunAtLt: findByLastRunAtLt,
    findByNameEq_CollectorEq: findByNameEq_CollectorEq,
    findByStatusEq: findByStatusEq,
    findByWorkflowEq: findByWorkflowEq,
    findByTimeWindowGroupEq: findByTimeWindowGroupEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
