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

  var ScreenTableView = require('account/screen/screen-table-view');

  var ScreenElement = require('nsx/nsx-application').getElement('account', 'screen');
  var isDataRefDefined = require('nsx/util/widget-utils').isDataRefDefined;

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
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
  // anchor:custom-methods:end

  function buildPage() {
    var template = "nsx/templates/page-default";
    var headViewModel = {
      title: translate("account.Screen.page.title")
    };
    var viewModel = {};
    var title = translate("account.Screen.menu.title");
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

    var table = ScreenTableView.defineTableView({
      injector: page,
      selector: "main",
      constants: constants,
      resetOn: Trigger.INIT_EVENT
    });

    // anchor:custom-page-after:start
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
