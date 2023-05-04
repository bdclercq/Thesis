// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'address' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var addressElement = nsxApplication.getElement("onlineCabBookingComp", "address");
  var addressAgent = nsxAgent.createAgent(addressElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllAddresss";
    var component = "onlineCabBookingComp";
    var element = "address";
    var translation = translate("onlineCabBookingComp.address.findAllAddresss");

    function find(options){
      var findOptions = {
        searchMethod: "findAllAddresss",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return addressAgent.find(findOptions);
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
    var element = "address";
    var translation = translate("onlineCabBookingComp.address.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return addressAgent.find(findOptions);
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

  function findByPincodeEq(options) {
    var name = "findByPincodeEq";
    var component = "onlineCabBookingComp";
    var element = "address";
    var translation = translate("onlineCabBookingComp.address.findByPincodeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByPincodeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return addressAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByPincodeEq.fields = ["pincode"];
  findByPincodeEq.fieldOperatorPairs = ["pincode:eq"];

  function findByCityEq(options) {
    var name = "findByCityEq";
    var component = "onlineCabBookingComp";
    var element = "address";
    var translation = translate("onlineCabBookingComp.address.findByCityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return addressAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCityEq.fields = ["city"];
  findByCityEq.fieldOperatorPairs = ["city:eq"];

  function findByStateEq(options) {
    var name = "findByStateEq";
    var component = "onlineCabBookingComp";
    var element = "address";
    var translation = translate("onlineCabBookingComp.address.findByStateEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStateEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return addressAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStateEq.fields = ["state"];
  findByStateEq.fieldOperatorPairs = ["state:eq"];

  function findByStreetEq(options) {
    var name = "findByStreetEq";
    var component = "onlineCabBookingComp";
    var element = "address";
    var translation = translate("onlineCabBookingComp.address.findByStreetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStreetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return addressAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStreetEq.fields = ["street"];
  findByStreetEq.fieldOperatorPairs = ["street:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByPincodeEq,
      findByCityEq,
      findByStateEq,
      findByStreetEq
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
    findByPincodeEq: findByPincodeEq,
    findByCityEq: findByCityEq,
    findByStateEq: findByStateEq,
    findByStreetEq: findByStreetEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
