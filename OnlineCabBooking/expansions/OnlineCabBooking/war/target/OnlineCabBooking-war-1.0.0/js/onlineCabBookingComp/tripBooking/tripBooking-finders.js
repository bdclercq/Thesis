// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'tripBooking' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var tripBookingElement = nsxApplication.getElement("onlineCabBookingComp", "tripBooking");
  var tripBookingAgent = nsxAgent.createAgent(tripBookingElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllTripBookings";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findAllTripBookings");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTripBookings",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
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
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
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

  function findByCustomerEq(options) {
    var name = "findByCustomerEq";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByCustomerEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCustomerEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCustomerEq.fields = ["customer"];
  findByCustomerEq.fieldOperatorPairs = ["customer:eq"];

  function findByDriverEq(options) {
    var name = "findByDriverEq";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByDriverEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByDriverEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByDriverEq.fields = ["driver"];
  findByDriverEq.fieldOperatorPairs = ["driver:eq"];

  function findAll(options) {
    var name = "findAllTripBooking";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findAllTripBooking");

    function find(options){
      var findOptions = {
        searchMethod: "findAllTripBooking",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
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

  function findByFromDateTimeEq(options) {
    var name = "findByFromDateTimeEq";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByFromDateTimeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByFromDateTimeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByFromDateTimeEq.fields = ["fromDateTime"];
  findByFromDateTimeEq.fieldOperatorPairs = ["fromDateTime:eq"];

  function findByToDateTimeEq(options) {
    var name = "findByToDateTimeEq";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByToDateTimeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByToDateTimeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByToDateTimeEq.fields = ["toDateTime"];
  findByToDateTimeEq.fieldOperatorPairs = ["toDateTime:eq"];

  function findByCustomerEq_FromDateTimeEq(options) {
    var name = "findByCustomerEq_FromDateTimeEq";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByCustomerEq_FromDateTimeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCustomerEq_FromDateTimeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCustomerEq_FromDateTimeEq.fields = ["customer", "fromDateTime"];
  findByCustomerEq_FromDateTimeEq.fieldOperatorPairs = ["customer:eq", "fromDateTime:eq"];

  function findByStatusEq(options) {
    var name = "findByStatusEq";
    var component = "onlineCabBookingComp";
    var element = "tripBooking";
    var translation = translate("onlineCabBookingComp.tripBooking.findByStatusEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStatusEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return tripBookingAgent.find(findOptions);
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
      findByCustomerEq,
      findByDriverEq,
      findByFromDateTimeEq,
      findByToDateTimeEq,
      findByCustomerEq_FromDateTimeEq,
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
    findByCustomerEq: findByCustomerEq,
    findByDriverEq: findByDriverEq,
    findAll: findAll,
    findByFromDateTimeEq: findByFromDateTimeEq,
    findByToDateTimeEq: findByToDateTimeEq,
    findByCustomerEq_FromDateTimeEq: findByCustomerEq_FromDateTimeEq,
    findByStatusEq: findByStatusEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
