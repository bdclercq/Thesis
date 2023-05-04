// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'screen' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var screenElement = nsxApplication.getElement("account", "screen");
  var screenAgent = nsxAgent.createAgent(screenElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllScreens";
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findAllScreens");

    function find(options){
      var findOptions = {
        searchMethod: "findAllScreens",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
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
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
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

  function findByComponentEq(options) {
    var name = "findByComponentEq";
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findByComponentEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByComponentEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByComponentEq.fields = ["component"];
  findByComponentEq.fieldOperatorPairs = ["component:eq"];

  function findByLinkEq(options) {
    var name = "findByLinkEq";
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findByLinkEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByLinkEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByLinkEq.fields = ["link"];
  findByLinkEq.fieldOperatorPairs = ["link:eq"];

  function findByNameEq_ComponentEq(options) {
    var name = "findByNameEq_ComponentEq";
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findByNameEq_ComponentEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq_ComponentEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByNameEq_ComponentEq.fields = ["component", "name"];
  findByNameEq_ComponentEq.fieldOperatorPairs = ["component:eq", "name:eq"];

  function findBySortOrderGt(options) {
    var name = "findBySortOrderGt";
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findBySortOrderGt");

    function find(options){
      var findOptions = {
        searchMethod: "findBySortOrderGt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findBySortOrderGt.fields = ["sortOrder"];
  findBySortOrderGt.fieldOperatorPairs = ["sortOrder:gt"];

  function findBySortOrderLt(options) {
    var name = "findBySortOrderLt";
    var component = "account";
    var element = "screen";
    var translation = translate("account.screen.findBySortOrderLt");

    function find(options){
      var findOptions = {
        searchMethod: "findBySortOrderLt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findBySortOrderLt.fields = ["sortOrder"];
  findBySortOrderLt.fieldOperatorPairs = ["sortOrder:lt"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByComponentEq,
      findByLinkEq,
      findByNameEq_ComponentEq,
      findBySortOrderGt,
      findBySortOrderLt
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
    findByComponentEq: findByComponentEq,
    findByLinkEq: findByLinkEq,
    findByNameEq_ComponentEq: findByNameEq_ComponentEq,
    findBySortOrderGt: findBySortOrderGt,
    findBySortOrderLt: findBySortOrderLt,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
