// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'screenProfile' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var screenProfileElement = nsxApplication.getElement("account", "screenProfile");
  var screenProfileAgent = nsxAgent.createAgent(screenProfileElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllScreenProfiles";
    var component = "account";
    var element = "screenProfile";
    var translation = translate("account.screenProfile.findAllScreenProfiles");

    function find(options){
      var findOptions = {
        searchMethod: "findAllScreenProfiles",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenProfileAgent.find(findOptions);
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

  function findByProfileEq(options) {
    var name = "findByProfileEq";
    var component = "account";
    var element = "screenProfile";
    var translation = translate("account.screenProfile.findByProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenProfileAgent.find(findOptions);
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

  function findByScreenEq(options) {
    var name = "findByScreenEq";
    var component = "account";
    var element = "screenProfile";
    var translation = translate("account.screenProfile.findByScreenEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByScreenEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenProfileAgent.find(findOptions);
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

  function findByScreenEq_ProfileEq(options) {
    var name = "findByScreenEq_ProfileEq";
    var component = "account";
    var element = "screenProfile";
    var translation = translate("account.screenProfile.findByScreenEq_ProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByScreenEq_ProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return screenProfileAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByScreenEq_ProfileEq.fields = ["profile", "screen"];
  findByScreenEq_ProfileEq.fieldOperatorPairs = ["profile:eq", "screen:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByProfileEq,
      findByScreenEq,
      findByScreenEq_ProfileEq
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
    findByProfileEq: findByProfileEq,
    findByScreenEq: findByScreenEq,
    findByScreenEq_ProfileEq: findByScreenEq_ProfileEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
