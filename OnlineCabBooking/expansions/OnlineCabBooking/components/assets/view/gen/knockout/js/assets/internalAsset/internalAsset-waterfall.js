// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;
  var when = require('nsx/triggers').when;

  require('nsx/injectors/tabs');

  var InternalAssetEvents = require('assets/internalAsset/internalAsset-events');

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController();
  var nsxApplication = require('nsx/nsx-application');

  var isDataRefDefined = require('nsx/util/widget-utils').isDataRefDefined;

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineInternalAssetChunkTab(tabRow, input) {
    var target = input.target;
    var name = "Internal asset chunk";
    var template = "nsx/templates/tab-content";
    var viewModel = {};
    var constants = { internalAsset: target };
    var InternalAssetChunkElement = nsxApplication.getElement("assets", "internalAssetChunk");
    var internalAssetChunkAccessRight = accessRightsController.requestRight({
      element: InternalAssetChunkElement,
      requestedRight: AccessRights.Right.VIEW
    });
    var tabOptions = {
      visible: internalAssetChunkAccessRight.approved
    };

    // @anchor:internalAssetChunk-tab-options:start
    // @anchor:internalAssetChunk-tab-options:end
    // anchor:custom-internalAssetChunk-tab-options:start
    // anchor:custom-internalAssetChunk-tab-options:end

    var tab = tabRow.defineTab({
        name: name,
        view: template,
        viewModel: viewModel
    }, tabOptions);

    var lastSelectedTarget = ko.observable();
    var parentUpdate = InternalAssetEvents.defineUpdateListener();
    when(parentUpdate).or(target).thenDo(function() {
       if (isDataRefDefined(target)()) lastSelectedTarget(target);
    });
    when(tab.revealed)
      .thenDo(function renderTab() {
        require([
          'assets/internalAssetChunk/internalAssetChunk-table-view',
          'assets/internalAssetChunk/internalAssetChunk-events'], function(InternalAssetChunkTableView, InternalAssetChunkEvents) {
          var table = InternalAssetChunkTableView.defineTableView({
            injector: tab,
            selector: "main",
            constants: constants,
            resetOn: lastSelectedTarget
          });

          // @anchor:internalAssetChunk-tab-widgets:start
          // @anchor:internalAssetChunk-tab-widgets:end
          // anchor:custom-internalAssetChunk-tab-widgets:start
          // anchor:custom-internalAssetChunk-tab-widgets:end

          var childRefresh = InternalAssetChunkEvents.defineRefreshUpdateTrigger();
          when(lastSelectedTarget).thenTrigger(childRefresh);
        })
    });
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  /**
   * @param {{
   *   target: DataRef | KnockoutObservable<DataRef>
   *   constants: any
   *   injector: NsxInjector,
   *   selector: string
   * }} input
   * @param {*} options
   */
  function defineWaterfall(input, options) {
    var visible = isDataRefDefined(input.target);

    // @anchor:page-options:start
    // @anchor:page-options:end
    // anchor:custom-page-options:start
    // anchor:custom-page-options:end

    var tabRow = input.injector.defineTabs({
      selector: input.selector
    }, {
      visible: visible
    });

    // @anchor:page-model:start
    // @anchor:page-model:end
    // anchor:custom-page-model:start
    // anchor:custom-page-model:end

    defineInternalAssetChunkTab(tabRow, { target: input.target, constants: input.constants });

    // @anchor:page-after:start
    // @anchor:page-after:end
    // anchor:custom-page-after:start
    // anchor:custom-page-after:end

    return {
      tabRow: tabRow
    };
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineWaterfall = defineWaterfall;
  return exports;
});
