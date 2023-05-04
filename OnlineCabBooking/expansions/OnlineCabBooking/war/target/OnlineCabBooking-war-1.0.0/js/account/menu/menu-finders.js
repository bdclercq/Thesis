// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'menu' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var menuElement = nsxApplication.getElement("account", "menu");
  var menuAgent = nsxAgent.createAgent(menuElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllMenus";
    var component = "account";
    var element = "menu";
    var translation = translate("account.menu.findAllMenus");

    function find(options){
      var findOptions = {
        searchMethod: "findAllMenus",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuAgent.find(findOptions);
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
    var element = "menu";
    var translation = translate("account.menu.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuAgent.find(findOptions);
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

  function findByPortalEq(options) {
    var name = "findByPortalEq";
    var component = "account";
    var element = "menu";
    var translation = translate("account.menu.findByPortalEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByPortalEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByPortalEq.fields = ["portal"];
  findByPortalEq.fieldOperatorPairs = ["portal:eq"];

  function findByPortalEq_ProfileEq(options) {
    var name = "findByPortalEq_ProfileEq";
    var component = "account";
    var element = "menu";
    var translation = translate("account.menu.findByPortalEq_ProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByPortalEq_ProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByPortalEq_ProfileEq.fields = ["portal", "profile"];
  findByPortalEq_ProfileEq.fieldOperatorPairs = ["portal:eq", "profile:eq"];

  function findByProfileEq(options) {
    var name = "findByProfileEq";
    var component = "account";
    var element = "menu";
    var translation = translate("account.menu.findByProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByProfileEq.fields = ["profile"];
  findByProfileEq.fieldOperatorPairs = ["profile:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByPortalEq,
      findByPortalEq_ProfileEq,
      findByProfileEq
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
    findByPortalEq: findByPortalEq,
    findByPortalEq_ProfileEq: findByPortalEq_ProfileEq,
    findByProfileEq: findByProfileEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
