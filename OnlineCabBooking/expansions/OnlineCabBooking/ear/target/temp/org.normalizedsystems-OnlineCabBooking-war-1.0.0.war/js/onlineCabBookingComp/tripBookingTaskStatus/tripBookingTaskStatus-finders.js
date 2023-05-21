// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'tripBookingTaskStatus' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var tripBookingTaskStatusElement = nsxApplication.getElement("onlineCabBookingComp", "tripBookingTaskStatus");
  var tripBookingTaskStatusAgent = nsxAgent.createAgent(tripBookingTaskStatusElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllTripBookingTaskStatuss";
    var component = "onlineCabBookingComp";
    var element = "tripBookingTaskStatus";
    var translation = translate("onlineCabBookingComp.tripBookingTaskStatus.findAllTripBookingTaskStatuss");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTripBookingTaskStatuss",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingTaskStatusAgent.find(findOptions);
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
    var component = "onlineCabBookingComp";
    var element = "tripBookingTaskStatus";
    var translation = translate("onlineCabBookingComp.tripBookingTaskStatus.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingTaskStatusAgent.find(findOptions);
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
    var component = "onlineCabBookingComp";
    var element = "tripBookingTaskStatus";
    var translation = translate("onlineCabBookingComp.tripBookingTaskStatus.findByStatusEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStatusEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingTaskStatusAgent.find(findOptions);
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

  function findByTripBookingEq(options) {
    var name = "findByTripBookingEq";
    var component = "onlineCabBookingComp";
    var element = "tripBookingTaskStatus";
    var translation = translate("onlineCabBookingComp.tripBookingTaskStatus.findByTripBookingEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTripBookingEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingTaskStatusAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTripBookingEq.fields = ["tripBooking"];
  findByTripBookingEq.fieldOperatorPairs = ["tripBooking:eq"];

  function findByStateTaskEq(options) {
    var name = "findByStateTaskEq";
    var component = "onlineCabBookingComp";
    var element = "tripBookingTaskStatus";
    var translation = translate("onlineCabBookingComp.tripBookingTaskStatus.findByStateTaskEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStateTaskEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingTaskStatusAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStateTaskEq.fields = ["stateTask"];
  findByStateTaskEq.fieldOperatorPairs = ["stateTask:eq"];

  function findAll(options) {
    var name = "findAllTripBookingTaskStatusses";
    var component = "onlineCabBookingComp";
    var element = "tripBookingTaskStatus";
    var translation = translate("onlineCabBookingComp.tripBookingTaskStatus.findAllTripBookingTaskStatusses");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTripBookingTaskStatusses",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingTaskStatusAgent.find(findOptions);
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
      findByTripBookingEq,
      findByStateTaskEq
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
    findByTripBookingEq: findByTripBookingEq,
    findByStateTaskEq: findByStateTaskEq,
    findAll: findAll,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
