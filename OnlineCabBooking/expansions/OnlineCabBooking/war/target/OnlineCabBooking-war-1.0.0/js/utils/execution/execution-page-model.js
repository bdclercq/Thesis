// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;

  var Menu = require('nsx/menu');
  var Trigger = require('nsx/triggers');
  var Page = require('nsx/injectors/page-segment');
  var intentHandlerLoader = require('nsx/config/intent-handler').getIntentHandlerLoader();

  var ErrorView = require('nsx/error/error-view');
  var ErrorLevel = require('nsx/error/error-levels');
  var ErrorPriorities = require('nsx/error/error-handler-priorities');

  var ApplicationProperties = require('nsx/metadata/nsx-application-properties');

  var ExecutionTableView = require('utils/execution/execution-table-view');

  var ExecutionElement = require('nsx/nsx-application').getElement('utils', 'execution');
  var isDataRefDefined = require('nsx/util/widget-utils').isDataRefDefined;

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  require('nsx/injectors/tabs');
  var PerformTask = require("nsx/action/perform-task-action");
  // anchor:custom-variables:end

  function defineErrorMessages(widget) {
    var errorView = ErrorView.defineErrorView({
      level: ErrorLevel.all,
      priority: ErrorPriorities.pageHeader
    });

    widget.definePageSegment({
      selector: "errors",
      view: "nsx/knockout/errorMessages",
      viewModel: errorView
    }, {
      visible: true
    })
  }

  // @anchor:functions:start
  // @anchor:functions:end
  // anchor:custom-methods:start
  function defineExecuteTab(tabs, input) {
    var tab = tabs.defineTab({
      name: "Perform executions",
      view: "nsx/templates/tab-content",
      viewModel: {}
    });

    var toolbar = tab.defineButtonToolbar({
      selector: "main"
    });

    var button = toolbar.defineButton({
      label: "Execute",
      icon: "icon-play"
    });

    PerformTask.definePerformTaskAction({
      trigger: button.trigger,
      selection: input.selection,
      taskName: "Executor",
      element: ExecutionElement
    })
  }
  // anchor:custom-methods:end

  function buildPage() {
    var template = "nsx/templates/page-default";
    var headViewModel = {
      title: translate("utils.Execution.page.title")
    };
    var viewModel = {};
    var title = translate("utils.Execution.menu.title");
    var menuName = "OnlineCabBooking";
    var menuAnchor = "navigation";
    var menuTemplate = "nsx/knockout/navigation-bar";
    var constants = {};

    // @anchor:page-options:start
    // @anchor:page-options:end
    // anchor:custom-page-options:start
    // anchor:custom-page-options:end

    ko.applyBindings(headViewModel, document.head);

    Menu.defineMenu({
      title: title,
      selector: menuAnchor,
      menuName: menuName,
      view: menuTemplate
    });

    var page = Page.definePageSegment({
      selector: "page",
      view: template,
      viewModel: viewModel
    });

    defineErrorMessages(page);

    var table = ExecutionTableView.defineTableView({
      injector: page,
      selector: "main",
      constants: constants,
      resetOn: Trigger.INIT_EVENT
    });

    // anchor:custom-page-after:start
    /*
    var tabs = page.defineTabs({
      selector: "widgets"
    }, {
      visible: isDataRefDefined(table.selection)
    });

    defineExecuteTab(tabs, {
      selection: table.selection
    });
    */
    // anchor:custom-page-after:end
    // @anchor:page-after:start
    // @anchor:page-after:end

    intentHandlerLoader.startListening();
    ApplicationProperties.defineHelperButton();
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.buildPage = buildPage;
  return exports;
});
