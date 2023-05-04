// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'person' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var personElement = nsxApplication.getElement("onlineCabBookingComp", "person");
  var personAgent = nsxAgent.createAgent(personElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllPersons";
    var component = "onlineCabBookingComp";
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findAllPersons");

    function find(options){
      var findOptions = {
        searchMethod: "findAllPersons",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
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
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
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

  function findByEmailEq(options) {
    var name = "findByEmailEq";
    var component = "onlineCabBookingComp";
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findByEmailEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEmailEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByEmailEq.fields = ["email"];
  findByEmailEq.fieldOperatorPairs = ["email:eq"];

  function findByAddressEq(options) {
    var name = "findByAddressEq";
    var component = "onlineCabBookingComp";
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findByAddressEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByAddressEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByAddressEq.fields = ["address"];
  findByAddressEq.fieldOperatorPairs = ["address:eq"];

  function findByMobileEq(options) {
    var name = "findByMobileEq";
    var component = "onlineCabBookingComp";
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findByMobileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByMobileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByMobileEq.fields = ["mobile"];
  findByMobileEq.fieldOperatorPairs = ["mobile:eq"];

  function findByUsernameEq(options) {
    var name = "findByUsernameEq";
    var component = "onlineCabBookingComp";
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findByUsernameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByUsernameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByUsernameEq.fields = ["username"];
  findByUsernameEq.fieldOperatorPairs = ["username:eq"];

  function findAll(options) {
    var name = "findAllPerson";
    var component = "onlineCabBookingComp";
    var element = "person";
    var translation = translate("onlineCabBookingComp.person.findAllPerson");

    function find(options){
      var findOptions = {
        searchMethod: "findAllPerson",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return personAgent.find(findOptions);
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
      findByEmailEq,
      findByAddressEq,
      findByMobileEq,
      findByUsernameEq
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
    findByEmailEq: findByEmailEq,
    findByAddressEq: findByAddressEq,
    findByMobileEq: findByMobileEq,
    findByUsernameEq: findByUsernameEq,
    findAll: findAll,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
