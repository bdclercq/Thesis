// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'user' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var userElement = nsxApplication.getElement("account", "user");
  var userAgent = nsxAgent.createAgent(userElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllUsers";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findAllUsers");

    function find(options){
      var findOptions = {
        searchMethod: "findAllUsers",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
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
    var element = "user";
    var translation = translate("account.user.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
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

  function findByAccountEq(options) {
    var name = "findByAccountEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByAccountEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByAccountEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByAccountEq.fields = ["account"];
  findByAccountEq.fieldOperatorPairs = ["account:eq"];

  function findByAccountEq_ProfileEq(options) {
    var name = "findByAccountEq_ProfileEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByAccountEq_ProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByAccountEq_ProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByAccountEq_ProfileEq.fields = ["account", "profile"];
  findByAccountEq_ProfileEq.fieldOperatorPairs = ["account:eq", "profile:eq"];

  function findByFullNameEq(options) {
    var name = "findByFullNameEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByFullNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByFullNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByFullNameEq.fields = ["fullName"];
  findByFullNameEq.fieldOperatorPairs = ["fullName:eq"];

  function findByPersNrEq(options) {
    var name = "findByPersNrEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByPersNrEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByPersNrEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByPersNrEq.fields = ["persNr"];
  findByPersNrEq.fieldOperatorPairs = ["persNr:eq"];

  function findByProfileEq(options) {
    var name = "findByProfileEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
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

  function findByEmailEq(options) {
    var name = "findByEmailEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByEmailEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEmailEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
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

  function findByNameEq_ProfileEq(options) {
    var name = "findByNameEq_ProfileEq";
    var component = "account";
    var element = "user";
    var translation = translate("account.user.findByNameEq_ProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq_ProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return userAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByNameEq_ProfileEq.fields = ["name", "profile"];
  findByNameEq_ProfileEq.fieldOperatorPairs = ["name:eq", "profile:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByAccountEq,
      findByAccountEq_ProfileEq,
      findByFullNameEq,
      findByPersNrEq,
      findByProfileEq,
      findByEmailEq,
      findByNameEq_ProfileEq
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
    findByAccountEq: findByAccountEq,
    findByAccountEq_ProfileEq: findByAccountEq_ProfileEq,
    findByFullNameEq: findByFullNameEq,
    findByPersNrEq: findByPersNrEq,
    findByProfileEq: findByProfileEq,
    findByEmailEq: findByEmailEq,
    findByNameEq_ProfileEq: findByNameEq_ProfileEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
