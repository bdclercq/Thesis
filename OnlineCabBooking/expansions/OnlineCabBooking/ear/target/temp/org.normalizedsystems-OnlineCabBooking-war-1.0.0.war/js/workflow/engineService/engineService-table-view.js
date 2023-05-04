// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;

  var Trigger = require('nsx/triggers');
  var Pagination = require('nsx/paging/standard-pagination');
  var FinderSelector = require("nsx/list/finder-selector");
  require('nsx/injectors/toolbar');

  var EngineServiceEvents = require('workflow/engineService/engineService-events');
  var EngineServiceFinders = require('workflow/engineService/engineService-finders');
  var EngineServiceStdTable = require('workflow/engineService/engineService-std-table');

  var EngineServiceElement = require('nsx/nsx-application').getElement('workflow', 'engineService');
  var isDataRefDefined = require('nsx/util/widget-utils').isDataRefDefined;
  var ifBoth = require('nsx/util/widget-utils').ifBoth;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  var WorkflowElement = require('nsx/nsx-application').getElement('workflow', 'workflow');
  var EngineServiceProjector = require('workflow/engineService/engineService-projector');
  var PerformCommandAction = require('nsx/action/perform-command-action');
  var nsxActions = require('nsx/nsx-actions');
  var nsxRequest = require('nsx/nsx-request');
  var nsxNotification = require('nsx/notification/nsx-notification')
  // anchor:custom-variables:end

  //region Finders
  // anchor:finders:start
  function defineFinders(input) {
    var constantFields = utils.getFieldOperators(input.constants);
    var defaultFinder = EngineServiceFinders.finderWith(constantFields);
    var finderOptions = EngineServiceFinders.allFindersWith(constantFields);
    var defaultDetails = input.constants || {};
    var resetTrigger = input.resetOn;

    var searchMethod = ko.observable(defaultFinder);
    var searchDetails = ko.observable(defaultDetails);
    var updateTrigger = Trigger.defineTrigger();

    // @anchor:finder-options:start
    // @anchor:finder-options:end
    // anchor:custom-finder-options:start
    // anchor:custom-finder-options:end

    function resetFinder() {
      searchMethod(defaultFinder);
      searchDetails(ko.toJS(defaultDetails));
      updateTrigger.trigger();
    }

    function setFinder(finder, details) {
      searchMethod(finder);
      if (details) {
        searchDetails(ko.toJS(details));
      } else {
        searchDetails(ko.toJS(defaultDetails));
      }
      updateTrigger.trigger();
    }

    function setFinderDetails(details) {
      searchDetails(ko.toJS(details));
      updateTrigger.trigger();
    }

    var finderUpdate = EngineServiceEvents.defineFinderUpdateTrigger();
    Trigger.when(updateTrigger).thenTrigger(finderUpdate);
    Trigger.when(resetTrigger).thenDo(resetFinder);

    return {
      searchMethod: searchMethod,
      searchDetails: searchDetails,
      finderOptions: finderOptions,
      resetFinder: resetFinder,
      setFinder: setFinder,
      setFinderDetails: setFinderDetails,
      updated: updateTrigger
    }
  }

  function defineFinderSelector(widget, input) {
    var view = "nsx/knockout/finderPanel-prefilled";
    var constantFields = utils.getFields(input.constants);
    var viewModel = FinderSelector.defineFinderSelector({
      element: EngineServiceElement,
      finderModel: input.finders
    }, {
      hiddenFields: constantFields
    });
    // @anchor:finder-selector:start
    // @anchor:finder-selector:end
    // anchor:custom-finder-selector:start
    // anchor:custom-finder-selector:end
    widget.definePageSegment({
      selector: "finders",
      view: view,
      viewModel: viewModel
    }, {
      wrapWith: "nsx/bootstrap/well",
      visible: input.visible
    });
  }
  // anchor:finders:end
  // anchor:show-finder-button:start
  function defineShowFinderButton(toolbar) {
    var buttonOptions = {
      layout: "finder-button",
      icon: "icon-search"
    };
    // @anchor:show-finder-button:start
    // @anchor:show-finder-button:end
    // anchor:custom-show-finder-button:start
    // anchor:custom-show-finder-button:end
    return toolbar.defineToggleButton(buttonOptions);
  }
  // anchor:show-finder-button:end
  //endregion

  //region List
  // anchor:list:start
  function defineEngineServiceList(widget, input) {
    return EngineServiceStdTable.defineTable(widget, input);
  }

  function definePagination(toolbar, listModel) {
    var view = "nsx/knockout/standard-pagination";
    var viewModel = Pagination.definePagination({
      pageNb: listModel.pageNb,
      numberOfPages: listModel.numberOfPages,
      gotoPage: listModel.gotoPage,
      totalNumberOfItems: listModel.totalNumberOfItems
    });
    var segmentOptions = {
      visible: ko.pureComputed(function () {
        return listModel.numberOfPages() > 1;
      })
    };
    // @anchor:pagination:start
    // @anchor:pagination:end
    // anchor:custom-pagination:start
    // anchor:custom-pagination:end
    toolbar.defineToolbarSegment({
      view: view,
      viewModel: viewModel
    }, segmentOptions);
  }
  // anchor:list:end
  //endregion

  //region Buttons
  // anchor:new-instance-button:start
  function defineNewInstanceButton(toolbar, input) {
    var accessRight = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.CREATE
    });
    var buttonOptions = {
      icon: "icon-plus",
      layout: "create-button",
      trigger: EngineServiceEvents.defineCreateIntentTrigger({
          constants: input.constants,
          values: input.values
        }),
      visible: accessRight.approved
    };
    // @anchor:new-instance-button:start
    // @anchor:new-instance-button:end
    // anchor:custom-new-instance-button:start
    // anchor:custom-new-instance-button:end
    return toolbar.defineButton(buttonOptions);
  }
  // anchor:new-instance-button:end
  // anchor:refresh-button:start
  function defineRefreshButton(toolbar) {
    var buttonOptions = {
      trigger: EngineServiceEvents.defineRefreshUpdateTrigger(),
      layout: "reload-button",
      icon: "icon-refresh"
    };
    // @anchor:refresh-button:start
    // @anchor:refresh-button:end
    // anchor:custom-refresh-button:start
    // anchor:custom-refresh-button:end
    return toolbar.defineButton(buttonOptions);
  }
  // anchor:refresh-button:end
  // anchor:view-instance-button:start
  function defineViewInstanceButton(toolbar, input) {
    var accessRight = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.VIEW
    });
    var buttonOptions = {
      icon: "icon-eye-open",
      layout: "view-button",
      trigger: EngineServiceEvents.defineViewIntentTrigger({
          selection: input.selection,
          constants: input.constants
        }),
      visible: ifBoth(
        accessRight.approved,
        isDataRefDefined(input.selection)
      )
    };
    // @anchor:view-instance-button:start
    // @anchor:view-instance-button:end
    // anchor:custom-view-instance-button:start
    // anchor:custom-view-instance-button:end
    return toolbar.defineButton(buttonOptions);
  }
  // anchor:view-instance-button:end
  // anchor:edit-instance-button:start
  function defineEditInstanceButton(toolbar, input) {
    var accessRight = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.EDIT
    });
    var buttonOptions = {
      icon: "icon-pencil",
      layout: "edit-button",
      trigger: EngineServiceEvents.defineEditIntentTrigger({
          selection: input.selection,
          constants: input.constants
        }),
      visible: ifBoth(
        accessRight.approved,
        isDataRefDefined(input.selection)
      )
    };
    // @anchor:edit-instance-button:start
    // @anchor:edit-instance-button:end
    // anchor:custom-edit-instance-button:start
    // anchor:custom-edit-instance-button:end
    return toolbar.defineButton(buttonOptions);
  }
  // anchor:edit-instance-button:end
  // anchor:delete-instance-button:start
  function defineDeleteInstanceButton(toolbar, input) {
    var accessRight = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.DELETE
    });
    var buttonOptions = {
      icon: "icon-trash",
      layout: "delete-button",
      trigger: EngineServiceEvents.defineDeleteIntentTrigger({
          selection: input.selection
        }),
      visible: ifBoth(
        accessRight.approved,
        isDataRefDefined(input.selection)
      )
    };
    // @anchor:delete-instance-button:start
    // @anchor:delete-instance-button:end
    // anchor:custom-delete-instance-button:start
    // anchor:custom-delete-instance-button:end
    return toolbar.defineButton(buttonOptions);
  }
  // anchor:delete-instance-button:end
  // @anchor:buttons:start
  // @anchor:buttons:end
  // anchor:custom-buttons:start
  // anchor:custom-buttons:end
  //endregion

  // @anchor:functions:start
  // @anchor:functions:end
  // anchor:custom-methods:start
  function defineRunNowButton(toolbar, selection) {
    var button = toolbar.defineButton({
      label: "Run now",
      icon: "icon-forward",
      visible: isDataRefDefined(selection)
    });

    Trigger.when(button.trigger)
        .thenDo(_ => {
          var engineService = ko.unwrap(selection)

          let url = nsxActions.getElementAction(WorkflowElement, 'run-json');
          url += `?engineService.id=${engineService.id}`
          nsxRequest.postPerformTask({
            url: url
          }).then((result) => {
            var messageBody = `
tasks processed: ${result.tasksProcessedInTotal}
tasks succeeded: ${result.tasksSucceeded}
tasks failed: ${result.tasksFailed}
tasks not executed: ${result.tasksNotExecuted}
`
            if (result.success) {
              nsxNotification.popupSuccess(engineService.name + ' ran successfully' + messageBody)
            } else {
              nsxNotification.popupWarn(engineService.name + ' encountered some problems' + messageBody)
            }
          })
        })

  }


  function defineEnableDisableRefreshButtons(toolbar, selection) {
    var engineServiceUpdate = EngineServiceEvents.defineEditUpdateTrigger();
    var esDetails = EngineServiceProjector.defineProjector(selection, {
      projection: "details"
    }).projection;

    var disableButton = toolbar.defineButton({
      label: "Disable Engine",
      icon: "icon-pause",
      layout: "disable-engine-btn",
      visible: ko.pureComputed(function () {
        return utils.isDefined(selection()) && esDetails().status === "start";
      })
    });

    var enableButton = toolbar.defineButton({
      label: "Enable Engine",
      icon: "icon-play",
      layout: "enable-engine-btn",
      visible: ko.pureComputed(function () {
        return utils.isDefined(selection()) && esDetails().status === "stop";
      })
    });

    var refreshButton = toolbar.defineButton({
      label: "Refresh Engine",
      icon: "icon-refresh",
      layout: "refresh-engine-btn",
      visible: ko.pureComputed(function () {
        return utils.isDefined(selection()) && esDetails().status === "start";
      })
    });

    var stopEngine = PerformCommandAction.definePerformCommandAction({
      commandName: "stopEngine",
      instance: {},
      trigger: disableButton.trigger,
      element: EngineServiceElement
    }, {
      target: selection
    });

    var startEngine = PerformCommandAction.definePerformCommandAction({
      commandName: "startEngine",
      instance: {},
      trigger: enableButton.trigger,
      element: EngineServiceElement
    }, {
      target: selection
    });

    var refreshEngine = PerformCommandAction.definePerformCommandAction({
      commandName: "refreshEngine",
      instance: {},
      trigger: refreshButton.trigger,
      element: EngineServiceElement
    }, {
      target: selection
    });

    Trigger
      .when(stopEngine.success)
      .or(startEngine.success)
      .or(refreshEngine.success)
      .thenTrigger(engineServiceUpdate);
  }
  // anchor:custom-methods:end

  /**
   * @param {{
   *   injector: NsxInjector,
   *   selector: string,
   *   constants: object,
   *   resetOn: Nsx.TriggerOutput<void>
   * }} input
   */
  function defineTableView(input) {
    var template = "nsx/templates/table-view";
    var viewModel = {};
    var tableContext = {
      constants: input.constants,
      values: {},
      resetOn: input.resetOn
    };

    // @anchor:page-options:start
    // @anchor:page-options:end
    // anchor:custom-page-options:start
    // anchor:custom-page-options:end

    var widget = input.injector.definePageSegment({
      selector: input.selector,
      view: template,
      viewModel: viewModel
    });

    var toolbar = widget.defineButtonToolbar({
      selector: "toolbar"
    });

    var finders = defineFinders({
      constants: tableContext.constants,
      resetOn: tableContext.resetOn
    });

    var newInstanceButton = defineNewInstanceButton(toolbar, tableContext);
    var showFinderButton = defineShowFinderButton(toolbar, tableContext);
    var refreshButton = defineRefreshButton(toolbar, tableContext);

    var finderSelector = defineFinderSelector(widget, {
      finders: finders,
      constants: tableContext.constants,
      visible: showFinderButton.active
    });

    var listOptions = {
      searchMethod: finders.searchMethod,
      searchDetails: finders.searchDetails
    };
    // @anchor:list-options:start
    // @anchor:list-options:end
    // anchor:custom-list-options:start
    // anchor:custom-list-options:end
    var engineServiceList = defineEngineServiceList(widget, listOptions);

    tableContext.selection = engineServiceList.selection;

    // @anchor:page-model:start
    // @anchor:page-model:end
    // anchor:custom-page-model:start
    var refreshUpdate = EngineServiceEvents.defineRefreshUpdateTrigger();
    setInterval(refreshUpdate.trigger, 10000);
    // anchor:custom-page-model:end

    var pagination = definePagination(toolbar, engineServiceList.listModel);
    var viewInstanceButton = defineViewInstanceButton(toolbar, tableContext);
    var editInstanceButton = defineEditInstanceButton(toolbar, tableContext);
    var deleteInstanceButton = defineDeleteInstanceButton(toolbar, tableContext);

    // @anchor:page-after:start
    // @anchor:page-after:end
    // anchor:custom-page-after:start
    defineEnableDisableRefreshButtons(toolbar, engineServiceList.selection);
    defineRunNowButton(toolbar, engineServiceList.selection)
    // anchor:custom-page-after:end

    return {
      injector: widget,
      selection: engineServiceList.selection
    };
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineTableView = defineTableView;
  return exports;
});
