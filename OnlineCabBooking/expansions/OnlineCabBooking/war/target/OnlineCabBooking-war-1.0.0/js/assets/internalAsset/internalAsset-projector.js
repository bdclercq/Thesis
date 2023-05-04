// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Creates a projector that maps a dataref to its projection.
 * Changes in the dataref-observable will cause the projection to be updated.
 */
define(function(require) {
  var ko = require('knockout');
  var internalAssetMetaData = require("dataElement!assets.internalAsset");
  var internalAssetAgent = internalAssetMetaData.getAgent();
  var Trigger = require('nsx/triggers');
  var when = Trigger.when;
  var InternalAssetEvents = require('assets/internalAsset/internalAsset-events');

  var translate = require('nsx/util/text-utils').translate;
  var errorTrigger = require("nsx/error/error-trigger").defineTrigger({
    subject: "assets.internalAsset.projection"
  });

  function defineProjector(dataRefObservable, options) {
    var projection = ko.observable({});
    var dataCreate = InternalAssetEvents.defineCreateUpdateListener();
    var dataEdit = InternalAssetEvents.defineEditUpdateListener();
    var dataRefresh = InternalAssetEvents.defineRefreshUpdateListener();
    var dataDelete = InternalAssetEvents.defineDeleteUpdateListener();
    var dataFinder = InternalAssetEvents.defineFinderUpdateListener();

    function updateProjection() {
      var dataRef = dataRefObservable();
      if (!dataRef || dataRef.id === 0) {
        projection({});
      } else {
        internalAssetAgent
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
