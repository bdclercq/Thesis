// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;
  var when = require('nsx/triggers').when;

  require('nsx/injectors/tabs');

  var EngineNodeEvents = require('workflow/engineNode/engineNode-events');

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController();
  var nsxApplication = require('nsx/nsx-application');

  var isDataRefDefined = require('nsx/util/widget-utils').isDataRefDefined;

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEngineNodeServiceTab(tabRow, input) {
    var target = input.target;
    var name = "Engine node service";
    var template = "nsx/templates/tab-content";
    var viewModel = {};
    var constants = { engineNode: target };
    var EngineNodeServiceElement = nsxApplication.getElement("workflow", "engineNodeService");
    var engineNodeServiceAccessRight = accessRightsController.requestRight({
      element: EngineNodeServiceElement,
      requestedRight: AccessRights.Right.VIEW
    });
    var tabOptions = {
      visible: engineNodeServiceAccessRight.approved
    };

    // @anchor:engineNodeService-tab-options:start
    // @anchor:engineNodeService-tab-options:end
    // anchor:custom-engineNodeService-tab-options:start
    // anchor:custom-engineNodeService-tab-options:end

    var tab = tabRow.defineTab({
        name: name,
        view: template,
        viewModel: viewModel
    }, tabOptions);

    var lastSelectedTarget = ko.observable();
    var parentUpdate = EngineNodeEvents.defineUpdateListener();
    when(parentUpdate).or(target).thenDo(function() {
       if (isDataRefDefined(target)()) lastSelectedTarget(target);
    });
    when(tab.revealed)
      .thenDo(function renderTab() {
        require([
          'workflow/engineNodeService/engineNodeService-table-view',
          'workflow/engineNodeService/engineNodeService-events'], function(EngineNodeServiceTableView, EngineNodeServiceEvents) {
          var table = EngineNodeServiceTableView.defineTableView({
            injector: tab,
            selector: "main",
            constants: constants,
            resetOn: lastSelectedTarget
          });

          // @anchor:engineNodeService-tab-widgets:start
          // @anchor:engineNodeService-tab-widgets:end
          // anchor:custom-engineNodeService-tab-widgets:start
          // anchor:custom-engineNodeService-tab-widgets:end

          var childRefresh = EngineNodeServiceEvents.defineRefreshUpdateTrigger();
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

    defineEngineNodeServiceTab(tabRow, { target: input.target, constants: input.constants });

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
