// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'menuItem' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var menuItemElement = nsxApplication.getElement("account", "menuItem");
  var menuItemAgent = nsxAgent.createAgent(menuItemElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllMenuItems";
    var component = "account";
    var element = "menuItem";
    var translation = translate("account.menuItem.findAllMenuItems");

    function find(options){
      var findOptions = {
        searchMethod: "findAllMenuItems",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
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
    var element = "menuItem";
    var translation = translate("account.menuItem.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
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

  function findByMenuEq(options) {
    var name = "findByMenuEq";
    var component = "account";
    var element = "menuItem";
    var translation = translate("account.menuItem.findByMenuEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByMenuEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByMenuEq.fields = ["menu"];
  findByMenuEq.fieldOperatorPairs = ["menu:eq"];

  function findByMenuEq_ScreenEq(options) {
    var name = "findByMenuEq_ScreenEq";
    var component = "account";
    var element = "menuItem";
    var translation = translate("account.menuItem.findByMenuEq_ScreenEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByMenuEq_ScreenEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByMenuEq_ScreenEq.fields = ["menu", "screen"];
  findByMenuEq_ScreenEq.fieldOperatorPairs = ["menu:eq", "screen:eq"];

  function findByMenuItemEq(options) {
    var name = "findByMenuItemEq";
    var component = "account";
    var element = "menuItem";
    var translation = translate("account.menuItem.findByMenuItemEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByMenuItemEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByMenuItemEq.fields = ["menuItem"];
  findByMenuItemEq.fieldOperatorPairs = ["menuItem:eq"];

  function findByScreenEq(options) {
    var name = "findByScreenEq";
    var component = "account";
    var element = "menuItem";
    var translation = translate("account.menuItem.findByScreenEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByScreenEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByScreenEq.fields = ["screen"];
  findByScreenEq.fieldOperatorPairs = ["screen:eq"];

  function findBySortOrderGt(options) {
    var name = "findBySortOrderGt";
    var component = "account";
    var element = "menuItem";
    var translation = translate("account.menuItem.findBySortOrderGt");

    function find(options){
      var findOptions = {
        searchMethod: "findBySortOrderGt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
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
    var element = "menuItem";
    var translation = translate("account.menuItem.findBySortOrderLt");

    function find(options){
      var findOptions = {
        searchMethod: "findBySortOrderLt",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return menuItemAgent.find(findOptions);
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
      findByMenuEq,
      findByMenuEq_ScreenEq,
      findByMenuItemEq,
      findByScreenEq,
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
    findByMenuEq: findByMenuEq,
    findByMenuEq_ScreenEq: findByMenuEq_ScreenEq,
    findByMenuItemEq: findByMenuItemEq,
    findByScreenEq: findByScreenEq,
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
