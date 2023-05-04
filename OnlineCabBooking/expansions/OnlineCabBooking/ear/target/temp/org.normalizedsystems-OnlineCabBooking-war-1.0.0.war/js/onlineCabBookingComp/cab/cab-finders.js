// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'cab' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var cabElement = nsxApplication.getElement("onlineCabBookingComp", "cab");
  var cabAgent = nsxAgent.createAgent(cabElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllCabs";
    var component = "onlineCabBookingComp";
    var element = "cab";
    var translation = translate("onlineCabBookingComp.cab.findAllCabs");

    function find(options){
      var findOptions = {
        searchMethod: "findAllCabs",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return cabAgent.find(findOptions);
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
    var element = "cab";
    var translation = translate("onlineCabBookingComp.cab.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return cabAgent.find(findOptions);
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

  function findByDriverEq(options) {
    var name = "findByDriverEq";
    var component = "onlineCabBookingComp";
    var element = "cab";
    var translation = translate("onlineCabBookingComp.cab.findByDriverEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByDriverEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return cabAgent.find(findOptions);
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

  function findByCarTypeEq(options) {
    var name = "findByCarTypeEq";
    var component = "onlineCabBookingComp";
    var element = "cab";
    var translation = translate("onlineCabBookingComp.cab.findByCarTypeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCarTypeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return cabAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCarTypeEq.fields = ["carType"];
  findByCarTypeEq.fieldOperatorPairs = ["carType:eq"];

  function findAll(options) {
    var name = "findAllCab";
    var component = "onlineCabBookingComp";
    var element = "cab";
    var translation = translate("onlineCabBookingComp.cab.findAllCab");

    function find(options){
      var findOptions = {
        searchMethod: "findAllCab",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return cabAgent.find(findOptions);
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
      findByDriverEq,
      findByCarTypeEq
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
    findByDriverEq: findByDriverEq,
    findByCarTypeEq: findByCarTypeEq,
    findAll: findAll,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
