// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'timeTask' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var timeTaskElement = nsxApplication.getElement("workflow", "timeTask");
  var timeTaskAgent = nsxAgent.createAgent(timeTaskElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllTimeTasks";
    var component = "workflow";
    var element = "timeTask";
    var translation = translate("workflow.timeTask.findAllTimeTasks");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTimeTasks",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeTaskAgent.find(findOptions);
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
    var element = "timeTask";
    var translation = translate("workflow.timeTask.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeTaskAgent.find(findOptions);
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

  function findByIntervalPeriodEq(options) {
    var name = "findByIntervalPeriodEq";
    var component = "workflow";
    var element = "timeTask";
    var translation = translate("workflow.timeTask.findByIntervalPeriodEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByIntervalPeriodEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeTaskAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByIntervalPeriodEq.fields = ["intervalPeriod"];
  findByIntervalPeriodEq.fieldOperatorPairs = ["intervalPeriod:eq"];

  function findByTriggerStateEq(options) {
    var name = "findByTriggerStateEq";
    var component = "workflow";
    var element = "timeTask";
    var translation = translate("workflow.timeTask.findByTriggerStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTriggerStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeTaskAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTriggerStateEq.fields = ["triggerState"];
  findByTriggerStateEq.fieldOperatorPairs = ["triggerState:eq"];

  function findByWorkflowEq(options) {
    var name = "findByWorkflowEq";
    var component = "workflow";
    var element = "timeTask";
    var translation = translate("workflow.timeTask.findByWorkflowEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByWorkflowEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeTaskAgent.find(findOptions);
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
      findByIntervalPeriodEq,
      findByTriggerStateEq,
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
    findByIntervalPeriodEq: findByIntervalPeriodEq,
    findByTriggerStateEq: findByTriggerStateEq,
    findByWorkflowEq: findByWorkflowEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
