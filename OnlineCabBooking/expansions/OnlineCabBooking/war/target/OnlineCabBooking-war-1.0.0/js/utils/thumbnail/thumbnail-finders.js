// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Lists all the finders for the 'thumbnail' element
 */
define(function (require) {
  var utils = require('nsx/util/utils');
  var nsxApplication = require('nsx/nsx-application');
  var nsxAgent = require('nsx/nsx-agent');
  var translate = require('nsx/util/text-utils').translate;
  var thumbnailElement = nsxApplication.getElement("utils", "thumbnail");
  var thumbnailAgent = nsxAgent.createAgent(thumbnailElement);

  // anchor:finders:start
  function findAll(options) {
    var name = "findAllThumbnails";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findAllThumbnails");

    function find(options){
      var findOptions = {
        searchMethod: "findAllThumbnails",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
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
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
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

  function findByDepthEq(options) {
    var name = "findByDepthEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByDepthEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByDepthEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByDepthEq.fields = ["depth"];
  findByDepthEq.fieldOperatorPairs = ["depth:eq"];

  function findByTargetNameEq(options) {
    var name = "findByTargetNameEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByTargetNameEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTargetNameEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTargetNameEq.fields = ["targetName"];
  findByTargetNameEq.fieldOperatorPairs = ["targetName:eq"];

  function findByTargetTypeEq(options) {
    var name = "findByTargetTypeEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByTargetTypeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByTargetTypeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByTargetTypeEq.fields = ["targetType"];
  findByTargetTypeEq.fieldOperatorPairs = ["targetType:eq"];

  function findByThumbTypeEq(options) {
    var name = "findByThumbTypeEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByThumbTypeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByThumbTypeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByThumbTypeEq.fields = ["thumbType"];
  findByThumbTypeEq.fieldOperatorPairs = ["thumbType:eq"];

  function findByUriEq(options) {
    var name = "findByUriEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByUriEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByUriEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByUriEq.fields = ["uri"];
  findByUriEq.fieldOperatorPairs = ["uri:eq"];

  function findByUriEq_DepthEq(options) {
    var name = "findByUriEq_DepthEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByUriEq_DepthEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByUriEq_DepthEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByUriEq_DepthEq.fields = ["depth", "uri"];
  findByUriEq_DepthEq.fieldOperatorPairs = ["depth:eq", "uri:eq"];

  function findByUriEq_TargetTypeEq(options) {
    var name = "findByUriEq_TargetTypeEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByUriEq_TargetTypeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByUriEq_TargetTypeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByUriEq_TargetTypeEq.fields = ["targetType", "uri"];
  findByUriEq_TargetTypeEq.fieldOperatorPairs = ["targetType:eq", "uri:eq"];

  function findByUriEq_ThumbTypeEq(options) {
    var name = "findByUriEq_ThumbTypeEq";
    var component = "utils";
    var element = "thumbnail";
    var translation = translate("utils.thumbnail.findByUriEq_ThumbTypeEq");

    function find(options){
      var findOptions = {
        searchMethod: "findByUriEq_ThumbTypeEq",
        details: options.details,
        rowsPerPage: options.rowsPerPage,
        page: options.page,
        projection: options.projection,
        sortFields: options.sortFields
      };
      return thumbnailAgent.find(findOptions);
    }

    return {
      name : name,
      component : component,
      element : element,
      find : find,
      translation : translation
    };
  }
  findByUriEq_ThumbTypeEq.fields = ["thumbType", "uri"];
  findByUriEq_ThumbTypeEq.fieldOperatorPairs = ["thumbType:eq", "uri:eq"];
  // anchor:finders:end

  // @anchor:finders:start
  // @anchor:finders:end
  // anchor:custom-finders:start
  // anchor:custom-finders:end

  function getAllFinders(){
    var allFinders = [
      // anchor:getAllFinders:start
      findByNameEq,
      findByDepthEq,
      findByTargetNameEq,
      findByTargetTypeEq,
      findByThumbTypeEq,
      findByUriEq,
      findByUriEq_DepthEq,
      findByUriEq_TargetTypeEq,
      findByUriEq_ThumbTypeEq
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
    findByDepthEq: findByDepthEq,
    findByTargetNameEq: findByTargetNameEq,
    findByTargetTypeEq: findByTargetTypeEq,
    findByThumbTypeEq: findByThumbTypeEq,
    findByUriEq: findByUriEq,
    findByUriEq_DepthEq: findByUriEq_DepthEq,
    findByUriEq_TargetTypeEq: findByUriEq_TargetTypeEq,
    findByUriEq_ThumbTypeEq: findByUriEq_ThumbTypeEq,
    // anchor:finder-returns:end
    // @anchor:finder-returns:start
    // @anchor:finder-returns:end
    allFinders : getAllFinders,
    allFindersWith: getAllFindersWith,
    finderWith: getFinderWith
  }
});
