// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'timeWindow' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var timeWindowElement = nsxApplication.getElement("workflow", "timeWindow");
  var timeWindowAgent = nsxAgent.createAgent(timeWindowElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllTimeWindows";
    var component = "workflow";
    var element = "timeWindow";
    var translation = translate("workflow.timeWindow.findAllTimeWindows");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTimeWindows",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeWindowAgent.find(findOptions);
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
    var element = "timeWindow";
    var translation = translate("workflow.timeWindow.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeWindowAgent.find(findOptions);
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

  function findByStartTimeEq(options) {
    var name = "findByStartTimeEq";
    var component = "workflow";
    var element = "timeWindow";
    var translation = translate("workflow.timeWindow.findByStartTimeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStartTimeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeWindowAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStartTimeEq.fields = ["startTime"];
  findByStartTimeEq.fieldOperatorPairs = ["startTime:eq"];

  function findByStopTimeEq(options) {
    var name = "findByStopTimeEq";
    var component = "workflow";
    var element = "timeWindow";
    var translation = translate("workflow.timeWindow.findByStopTimeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStopTimeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return timeWindowAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStopTimeEq.fields = ["stopTime"];
  findByStopTimeEq.fieldOperatorPairs = ["stopTime:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByStartTimeEq,
      findByStopTimeEq
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
    findByStartTimeEq: findByStartTimeEq,
    findByStopTimeEq: findByStopTimeEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
