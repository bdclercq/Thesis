// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'stateTask' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var stateTaskElement = nsxApplication.getElement("workflow", "stateTask");
  var stateTaskAgent = nsxAgent.createAgent(stateTaskElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllStateTasks";
    var component = "workflow";
    var element = "stateTask";
    var translation = translate("workflow.stateTask.findAllStateTasks");

    function find(options){
      var findOptions = {
        searchMethod: "findAllStateTasks",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTaskAgent.find(findOptions);
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
    var element = "stateTask";
    var translation = translate("workflow.stateTask.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTaskAgent.find(findOptions);
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

  function findByBeginStateEq(options) {
    var name = "findByBeginStateEq";
    var component = "workflow";
    var element = "stateTask";
    var translation = translate("workflow.stateTask.findByBeginStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByBeginStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTaskAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByBeginStateEq.fields = ["beginState"];
  findByBeginStateEq.fieldOperatorPairs = ["beginState:eq"];

  function findByEndStateEq(options) {
    var name = "findByEndStateEq";
    var component = "workflow";
    var element = "stateTask";
    var translation = translate("workflow.stateTask.findByEndStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEndStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTaskAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByEndStateEq.fields = ["endState"];
  findByEndStateEq.fieldOperatorPairs = ["endState:eq"];

  function findByFailedStateEq(options) {
    var name = "findByFailedStateEq";
    var component = "workflow";
    var element = "stateTask";
    var translation = translate("workflow.stateTask.findByFailedStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByFailedStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTaskAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByFailedStateEq.fields = ["failedState"];
  findByFailedStateEq.fieldOperatorPairs = ["failedState:eq"];

  function findByWorkflowEq(options) {
    var name = "findByWorkflowEq";
    var component = "workflow";
    var element = "stateTask";
    var translation = translate("workflow.stateTask.findByWorkflowEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByWorkflowEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTaskAgent.find(findOptions);
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
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByBeginStateEq,
      findByEndStateEq,
      findByFailedStateEq,
      findByWorkflowEq
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
    findByBeginStateEq: findByBeginStateEq,
    findByEndStateEq: findByEndStateEq,
    findByFailedStateEq: findByFailedStateEq,
    findByWorkflowEq: findByWorkflowEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
