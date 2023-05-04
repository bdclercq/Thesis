// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;
  var when = require('nsx/triggers').when;

  require('nsx/injectors/tabs');

  var WorkflowEvents = require('workflow/workflow/workflow-events');

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController();
  var nsxApplication = require('nsx/nsx-application');

  var isDataRefDefined = require('nsx/util/widget-utils').isDataRefDefined;

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
/* REMOVED */
  // anchor:custom-variables:end

  function defineEngineServiceTab(tabRow, input) {
    var target = input.target;
    var name = "Engine service";
    var template = "nsx/templates/tab-content";
    var viewModel = {};
    var constants = { workflow: target };
    var EngineServiceElement = nsxApplication.getElement("workflow", "engineService");
    var engineServiceAccessRight = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.VIEW
    });
    var tabOptions = {
      visible: engineServiceAccessRight.approved
    };

    // @anchor:engineService-tab-options:start
    // @anchor:engineService-tab-options:end
    // anchor:custom-engineService-tab-options:start
    // anchor:custom-engineService-tab-options:end

    var tab = tabRow.defineTab({
        name: name,
        view: template,
        viewModel: viewModel
    }, tabOptions);

    var lastSelectedTarget = ko.observable();
    var parentUpdate = WorkflowEvents.defineUpdateListener();
    when(parentUpdate).or(target).thenDo(function() {
       if (isDataRefDefined(target)()) lastSelectedTarget(target);
    });
    when(tab.revealed)
      .thenDo(function renderTab() {
        require([
          'workflow/engineService/engineService-table-view',
          'workflow/engineService/engineService-events',
          'workflow/workflow/engineService-waterfall'
        ], function(EngineServiceTableView, EngineServiceEvents, EngineServiceWaterfall) {
          var table = EngineServiceTableView.defineTableView({
            injector: tab,
            selector: "main",
            constants: constants,
            resetOn: lastSelectedTarget
          });

          EngineServiceWaterfall.defineWaterfall({
            target: table.selection,
            constants: constants,
            injector: tab,
            selector: "widgets"
          });
          var childRefresh = EngineServiceEvents.defineRefreshUpdateTrigger();
          when(lastSelectedTarget).thenTrigger(childRefresh);
        })
    });
  }

  function defineStateTaskTab(tabRow, input) {
    var target = input.target;
    var name = "State task";
    var template = "nsx/templates/tab-content";
    var viewModel = {};
    var constants = { workflow: target };
    var StateTaskElement = nsxApplication.getElement("workflow", "stateTask");
    var stateTaskAccessRight = accessRightsController.requestRight({
      element: StateTaskElement,
      requestedRight: AccessRights.Right.VIEW
    });
    var tabOptions = {
      visible: stateTaskAccessRight.approved
    };

    // @anchor:stateTask-tab-options:start
    // @anchor:stateTask-tab-options:end
    // anchor:custom-stateTask-tab-options:start
    // anchor:custom-stateTask-tab-options:end

    var tab = tabRow.defineTab({
        name: name,
        view: template,
        viewModel: viewModel
    }, tabOptions);

    var lastSelectedTarget = ko.observable();
    var parentUpdate = WorkflowEvents.defineUpdateListener();
    when(parentUpdate).or(target).thenDo(function() {
       if (isDataRefDefined(target)()) lastSelectedTarget(target);
    });
    when(tab.revealed)
      .thenDo(function renderTab() {
        require([
          'workflow/stateTask/stateTask-table-view',
          'workflow/stateTask/stateTask-events'], function(StateTaskTableView, StateTaskEvents) {
          var table = StateTaskTableView.defineTableView({
            injector: tab,
            selector: "main",
            constants: constants,
            resetOn: lastSelectedTarget
          });

          // @anchor:stateTask-tab-widgets:start
          // @anchor:stateTask-tab-widgets:end
          // anchor:custom-stateTask-tab-widgets:start
          // anchor:custom-stateTask-tab-widgets:end

          var childRefresh = StateTaskEvents.defineRefreshUpdateTrigger();
          when(lastSelectedTarget).thenTrigger(childRefresh);
        })
    });
  }

  function defineStateTimerTab(tabRow, input) {
    var target = input.target;
    var name = "State timer";
    var template = "nsx/templates/tab-content";
    var viewModel = {};
    var constants = { workflow: target };
    var StateTimerElement = nsxApplication.getElement("workflow", "stateTimer");
    var stateTimerAccessRight = accessRightsController.requestRight({
      element: StateTimerElement,
      requestedRight: AccessRights.Right.VIEW
    });
    var tabOptions = {
      visible: stateTimerAccessRight.approved
    };

    // @anchor:stateTimer-tab-options:start
    // @anchor:stateTimer-tab-options:end
    // anchor:custom-stateTimer-tab-options:start
    // anchor:custom-stateTimer-tab-options:end

    var tab = tabRow.defineTab({
        name: name,
        view: template,
        viewModel: viewModel
    }, tabOptions);

    var lastSelectedTarget = ko.observable();
    var parentUpdate = WorkflowEvents.defineUpdateListener();
    when(parentUpdate).or(target).thenDo(function() {
       if (isDataRefDefined(target)()) lastSelectedTarget(target);
    });
    when(tab.revealed)
      .thenDo(function renderTab() {
        require([
          'workflow/stateTimer/stateTimer-table-view',
          'workflow/stateTimer/stateTimer-events'], function(StateTimerTableView, StateTimerEvents) {
          var table = StateTimerTableView.defineTableView({
            injector: tab,
            selector: "main",
            constants: constants,
            resetOn: lastSelectedTarget
          });

          // @anchor:stateTimer-tab-widgets:start
          // @anchor:stateTimer-tab-widgets:end
          // anchor:custom-stateTimer-tab-widgets:start
          // anchor:custom-stateTimer-tab-widgets:end

          var childRefresh = StateTimerEvents.defineRefreshUpdateTrigger();
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

    defineEngineServiceTab(tabRow, { target: input.target, constants: input.constants });
    defineStateTaskTab(tabRow, { target: input.target, constants: input.constants });
    defineStateTimerTab(tabRow, { target: input.target, constants: input.constants });

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
