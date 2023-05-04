// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Creates a projector that maps a dataref to its projection.
 * Changes in the dataref-observable will cause the projection to be updated.
 */
define(function(require) {
  var ko = require('knockout');
  var fileAssetMetaData = require("dataElement!assets.fileAsset");
  var fileAssetAgent = fileAssetMetaData.getAgent();
  var Trigger = require('nsx/triggers');
  var when = Trigger.when;
  var FileAssetEvents = require('assets/fileAsset/fileAsset-events');

  var translate = require('nsx/util/text-utils').translate;
  var errorTrigger = require("nsx/error/error-trigger").defineTrigger({
    subject: "assets.fileAsset.projection"
  });

  function defineProjector(dataRefObservable, options) {
    var projection = ko.observable({});
    var dataCreate = FileAssetEvents.defineCreateUpdateListener();
    var dataEdit = FileAssetEvents.defineEditUpdateListener();
    var dataRefresh = FileAssetEvents.defineRefreshUpdateListener();
    var dataDelete = FileAssetEvents.defineDeleteUpdateListener();
    var dataFinder = FileAssetEvents.defineFinderUpdateListener();

    function updateProjection() {
      var dataRef = dataRefObservable();
      if (!dataRef || dataRef.id === 0) {
        projection({});
      } else {
        fileAssetAgent
          .getProjection(dataRef.id, options.projection)
          .done(function (jsonResult) {
            if (jsonResult.success) {
              projection(jsonResult.value);
            } else {
              errorTrigger.warn(translate("cruds.findFailed"))
            }
          });
      }
    }

    when(dataRefObservable)
      .or(dataCreate)
      .or(dataEdit)
      .or(dataRefresh)
      .or(dataFinder)
      .thenDo(updateProjection);
    when(dataDelete).thenDo(() => projection({}));
    updateProjection();

    return {
      projection: projection
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineProjector = defineProjector;
  return exports;
});
