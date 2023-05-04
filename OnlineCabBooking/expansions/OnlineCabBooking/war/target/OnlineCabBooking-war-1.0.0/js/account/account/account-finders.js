// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'account' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var accountElement = nsxApplication.getElement("account", "account");
  var accountAgent = nsxAgent.createAgent(accountElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllAccounts";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findAllAccounts");

    function find(options){
      var findOptions = {
        searchMethod: "findAllAccounts",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
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
    var element = "account";
    var translation = translate("account.account.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
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

  function findByAddressEq(options) {
    var name = "findByAddressEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByAddressEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByAddressEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
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

  function findByCityEq(options) {
    var name = "findByCityEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByCityEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCityEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
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

  function findByCountryEq(options) {
    var name = "findByCountryEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByCountryEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByCountryEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByCountryEq.fields = ["country"];
  findByCountryEq.fieldOperatorPairs = ["country:eq"];

  function findByEmailEq(options) {
    var name = "findByEmailEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByEmailEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByEmailEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
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

  function findByFullNameEq(options) {
    var name = "findByFullNameEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByFullNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByFullNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
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

  function findByRefIdEq(options) {
    var name = "findByRefIdEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByRefIdEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByRefIdEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByRefIdEq.fields = ["refId"];
  findByRefIdEq.fieldOperatorPairs = ["refId:eq"];

  function findByStatusEq(options) {
    var name = "findByStatusEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByStatusEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByStatusEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByStatusEq.fields = ["status"];
  findByStatusEq.fieldOperatorPairs = ["status:eq"];

  function findByZipCodeEq(options) {
    var name = "findByZipCodeEq";
    var component = "account";
    var element = "account";
    var translation = translate("account.account.findByZipCodeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByZipCodeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return accountAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByZipCodeEq.fields = ["zipCode"];
  findByZipCodeEq.fieldOperatorPairs = ["zipCode:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByAddressEq,
      findByCityEq,
      findByCountryEq,
      findByEmailEq,
      findByFullNameEq,
      findByRefIdEq,
      findByStatusEq,
      findByZipCodeEq
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
    findByAddressEq: findByAddressEq,
    findByCityEq: findByCityEq,
    findByCountryEq: findByCountryEq,
    findByEmailEq: findByEmailEq,
    findByFullNameEq: findByFullNameEq,
    findByRefIdEq: findByRefIdEq,
    findByStatusEq: findByStatusEq,
    findByZipCodeEq: findByZipCodeEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
