// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'stateTimer' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var stateTimerElement = nsxApplication.getElement("workflow", "stateTimer");
  var stateTimerAgent = nsxAgent.createAgent(stateTimerElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllStateTimers";
    var component = "workflow";
    var element = "stateTimer";
    var translation = translate("workflow.stateTimer.findAllStateTimers");

    function find(options){
      var findOptions = {
        searchMethod: "findAllStateTimers",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTimerAgent.find(findOptions);
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
    var element = "stateTimer";
    var translation = translate("workflow.stateTimer.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTimerAgent.find(findOptions);
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

  function findByAlteredStateEq(options) {
    var name = "findByAlteredStateEq";
    var component = "workflow";
    var element = "stateTimer";
    var translation = translate("workflow.stateTimer.findByAlteredStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByAlteredStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTimerAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByAlteredStateEq.fields = ["alteredState"];
  findByAlteredStateEq.fieldOperatorPairs = ["alteredState:eq"];

  function findByBeginStateEq(options) {
    var name = "findByBeginStateEq";
    var component = "workflow";
    var element = "stateTimer";
    var translation = translate("workflow.stateTimer.findByBeginStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByBeginStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTimerAgent.find(findOptions);
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

  function findByTargetStateEq(options) {
    var name = "findByTargetStateEq";
    var component = "workflow";
    var element = "stateTimer";
    var translation = translate("workflow.stateTimer.findByTargetStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTargetStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTimerAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTargetStateEq.fields = ["targetState"];
  findByTargetStateEq.fieldOperatorPairs = ["targetState:eq"];

  function findByWorkflowEq(options) {
    var name = "findByWorkflowEq";
    var component = "workflow";
    var element = "stateTimer";
    var translation = translate("workflow.stateTimer.findByWorkflowEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByWorkflowEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return stateTimerAgent.find(findOptions);
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
      findByAlteredStateEq,
      findByBeginStateEq,
      findByTargetStateEq,
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
    findByAlteredStateEq: findByAlteredStateEq,
    findByBeginStateEq: findByBeginStateEq,
    findByTargetStateEq: findByTargetStateEq,
    findByWorkflowEq: findByWorkflowEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
