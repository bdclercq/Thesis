// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'driver' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var driverElement = nsxApplication.getElement("onlineCabBookingComp", "driver");
  var driverAgent = nsxAgent.createAgent(driverElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllDrivers";
    var component = "onlineCabBookingComp";
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findAllDrivers");

    function find(options){
      var findOptions = {
        searchMethod: "findAllDrivers",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
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
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
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

  function findByLicenseNoEq(options) {
    var name = "findByLicenseNoEq";
    var component = "onlineCabBookingComp";
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findByLicenseNoEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByLicenseNoEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByLicenseNoEq.fields = ["licenseNo"];
  findByLicenseNoEq.fieldOperatorPairs = ["licenseNo:eq"];

  function findByRatingEq(options) {
    var name = "findByRatingEq";
    var component = "onlineCabBookingComp";
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findByRatingEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByRatingEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByRatingEq.fields = ["rating"];
  findByRatingEq.fieldOperatorPairs = ["rating:eq"];

  function findByIsAvailableEq(options) {
    var name = "findByIsAvailableEq";
    var component = "onlineCabBookingComp";
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findByIsAvailableEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByIsAvailableEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByIsAvailableEq.fields = ["isAvailable"];
  findByIsAvailableEq.fieldOperatorPairs = ["isAvailable:eq"];

  function findByTripBookingEq(options) {
    var name = "findByTripBookingEq";
    var component = "onlineCabBookingComp";
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findByTripBookingEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTripBookingEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
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

  function findByCabEq(options) {
    var name = "findByCabEq";
    var component = "onlineCabBookingComp";
    var element = "driver";
    var translation = translate("onlineCabBookingComp.driver.findByCabEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCabEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return driverAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCabEq.fields = ["cab"];
  findByCabEq.fieldOperatorPairs = ["cab:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByLicenseNoEq,
      findByRatingEq,
      findByIsAvailableEq,
      findByTripBookingEq,
      findByCabEq
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
    findByLicenseNoEq: findByLicenseNoEq,
    findByRatingEq: findByRatingEq,
    findByIsAvailableEq: findByIsAvailableEq,
    findByTripBookingEq: findByTripBookingEq,
    findByCabEq: findByCabEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
