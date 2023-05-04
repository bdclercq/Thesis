// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'dataAccess' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var dataAccessElement = nsxApplication.getElement("account", "dataAccess");
  var dataAccessAgent = nsxAgent.createAgent(dataAccessElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllDataAccesss";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findAllDataAccesss");

    function find(options){
      var findOptions = {
        searchMethod: "findAllDataAccesss",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
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
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
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

  function findByElementEq(options) {
    var name = "findByElementEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByElementEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByElementEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByElementEq.fields = ["element"];
  findByElementEq.fieldOperatorPairs = ["element:eq"];

  function findByElementEq_FunctionalityEq(options) {
    var name = "findByElementEq_FunctionalityEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByElementEq_FunctionalityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByElementEq_FunctionalityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByElementEq_FunctionalityEq.fields = ["element", "functionality"];
  findByElementEq_FunctionalityEq.fieldOperatorPairs = ["element:eq", "functionality:eq"];

  function findByElementEq_TargetEq(options) {
    var name = "findByElementEq_TargetEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByElementEq_TargetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByElementEq_TargetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByElementEq_TargetEq.fields = ["element", "target"];
  findByElementEq_TargetEq.fieldOperatorPairs = ["element:eq", "target:eq"];

  function findByForProfileEq(options) {
    var name = "findByForProfileEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForProfileEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForProfileEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForProfileEq.fields = ["forProfile"];
  findByForProfileEq.fieldOperatorPairs = ["forProfile:eq"];

  function findByForProfileEq_ElementEq(options) {
    var name = "findByForProfileEq_ElementEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForProfileEq_ElementEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForProfileEq_ElementEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForProfileEq_ElementEq.fields = ["element", "forProfile"];
  findByForProfileEq_ElementEq.fieldOperatorPairs = ["element:eq", "forProfile:eq"];

  function findByForProfileEq_FunctionalityEq(options) {
    var name = "findByForProfileEq_FunctionalityEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForProfileEq_FunctionalityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForProfileEq_FunctionalityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForProfileEq_FunctionalityEq.fields = ["forProfile", "functionality"];
  findByForProfileEq_FunctionalityEq.fieldOperatorPairs = ["forProfile:eq", "functionality:eq"];

  function findByForUserEq(options) {
    var name = "findByForUserEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserEq.fields = ["forUser"];
  findByForUserEq.fieldOperatorPairs = ["forUser:eq"];

  function findByForUserEq_ElementEq(options) {
    var name = "findByForUserEq_ElementEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserEq_ElementEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserEq_ElementEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserEq_ElementEq.fields = ["element", "forUser"];
  findByForUserEq_ElementEq.fieldOperatorPairs = ["element:eq", "forUser:eq"];

  function findByForUserEq_FunctionalityEq(options) {
    var name = "findByForUserEq_FunctionalityEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserEq_FunctionalityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserEq_FunctionalityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserEq_FunctionalityEq.fields = ["forUser", "functionality"];
  findByForUserEq_FunctionalityEq.fieldOperatorPairs = ["forUser:eq", "functionality:eq"];

  function findByTargetEq(options) {
    var name = "findByTargetEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByTargetEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTargetEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTargetEq.fields = ["target"];
  findByTargetEq.fieldOperatorPairs = ["target:eq"];

  function findByForUserGroupEq_ElementEq(options) {
    var name = "findByForUserGroupEq_ElementEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserGroupEq_ElementEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserGroupEq_ElementEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserGroupEq_ElementEq.fields = ["element", "forUserGroup"];
  findByForUserGroupEq_ElementEq.fieldOperatorPairs = ["element:eq", "forUserGroup:eq"];

  function findByForUserEq_ElementEq_TargetEq_FunctionalityEq(options) {
    var name = "findByForUserEq_ElementEq_TargetEq_FunctionalityEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserEq_ElementEq_TargetEq_FunctionalityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserEq_ElementEq_TargetEq_FunctionalityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserEq_ElementEq_TargetEq_FunctionalityEq.fields = ["forUser", "element", "target", "functionality"];
  findByForUserEq_ElementEq_TargetEq_FunctionalityEq.fieldOperatorPairs = ["forUser:eq", "element:eq", "target:eq", "functionality:eq"];

  function findByForProfileEq_ElementEq_TargetEq_FunctionalityEq(options) {
    var name = "findByForProfileEq_ElementEq_TargetEq_FunctionalityEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForProfileEq_ElementEq_TargetEq_FunctionalityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForProfileEq_ElementEq_TargetEq_FunctionalityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForProfileEq_ElementEq_TargetEq_FunctionalityEq.fields = ["forProfile", "element", "target", "functionality"];
  findByForProfileEq_ElementEq_TargetEq_FunctionalityEq.fieldOperatorPairs = ["forProfile:eq", "element:eq", "target:eq", "functionality:eq"];

  function findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq(options) {
    var name = "findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.fields = ["forUserGroup", "element", "target", "functionality"];
  findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.fieldOperatorPairs = ["forUserGroup:eq", "element:eq", "target:eq", "functionality:eq"];

  function findByForUserGroupEq(options) {
    var name = "findByForUserGroupEq";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findByForUserGroupEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByForUserGroupEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByForUserGroupEq.fields = ["forUserGroup"];
  findByForUserGroupEq.fieldOperatorPairs = ["forUserGroup:eq"];

  function findBySpecificationOrWildcard(options) {
    var name = "findBySpecificationOrWildcard";
    var component = "account";
    var element = "dataAccess";
    var translation = translate("account.dataAccess.findBySpecificationOrWildcard");

    function find(options){
      var findOptions = {
        searchMethod: "findBySpecificationOrWildcard",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return dataAccessAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findBySpecificationOrWildcard.fields = ["forProfile", "forUserGroup", "forUser", "element", "target", "functionality"];
  findBySpecificationOrWildcard.fieldOperatorPairs = ["forProfile:eq", "forUserGroup:eq", "forUser:eq", "element:eq", "target:eq", "functionality:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByElementEq,
      findByElementEq_FunctionalityEq,
      findByElementEq_TargetEq,
      findByForProfileEq,
      findByForProfileEq_ElementEq,
      findByForProfileEq_FunctionalityEq,
      findByForUserEq,
      findByForUserEq_ElementEq,
      findByForUserEq_FunctionalityEq,
      findByTargetEq,
      findByForUserGroupEq_ElementEq,
      findByForUserEq_ElementEq_TargetEq_FunctionalityEq,
      findByForProfileEq_ElementEq_TargetEq_FunctionalityEq,
      findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq,
      findByForUserGroupEq,
      findBySpecificationOrWildcard
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
    findByElementEq: findByElementEq,
    findByElementEq_FunctionalityEq: findByElementEq_FunctionalityEq,
    findByElementEq_TargetEq: findByElementEq_TargetEq,
    findByForProfileEq: findByForProfileEq,
    findByForProfileEq_ElementEq: findByForProfileEq_ElementEq,
    findByForProfileEq_FunctionalityEq: findByForProfileEq_FunctionalityEq,
    findByForUserEq: findByForUserEq,
    findByForUserEq_ElementEq: findByForUserEq_ElementEq,
    findByForUserEq_FunctionalityEq: findByForUserEq_FunctionalityEq,
    findByTargetEq: findByTargetEq,
    findByForUserGroupEq_ElementEq: findByForUserGroupEq_ElementEq,
    findByForUserEq_ElementEq_TargetEq_FunctionalityEq: findByForUserEq_ElementEq_TargetEq_FunctionalityEq,
    findByForProfileEq_ElementEq_TargetEq_FunctionalityEq: findByForProfileEq_ElementEq_TargetEq_FunctionalityEq,
    findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq: findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq,
    findByForUserGroupEq: findByForUserGroupEq,
    findBySpecificationOrWildcard: findBySpecificationOrWildcard,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
