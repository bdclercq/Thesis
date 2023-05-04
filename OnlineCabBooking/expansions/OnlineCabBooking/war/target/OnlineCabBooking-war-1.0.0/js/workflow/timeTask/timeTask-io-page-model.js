// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;
  require('nsx/injectors/accordion');

  var ErrorView = require('nsx/error/error-view');
  var ErrorLevel = require('nsx/error/error-levels');
  var ErrorPriorities = require('nsx/error/error-handler-priorities');

  var Menu = require('nsx/menu');
  var Page = require('nsx/injectors/page-segment');
  require('nsx/injectors/toolbar');

  var Trigger = require('nsx/triggers');
  var TimeTaskElement = require('nsx/nsx-application').getElement('workflow', 'timeTask');

  var UploadForm = require('nsx/io/upload-form');
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

  // @anchor:methods:start
  function defineImportCsvPane(accordion) {
    var importCsvPane = accordion.definePane({
      name: "Import CSV",
      view: "nsx/templates/tab-content",
      viewModel: {}
    });
    UploadForm.defineUploadForm({
      element: TimeTaskElement,
      parent: importCsvPane,
      selector: "main",
      title: "Upload timeTask instances as CSV",
      type: "csv"
    })
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

  function buildPage() {
    var template = "nsx/templates/page-default";
    var viewModel = {};
    var title = "TimeTask Data";
    var menuName = "OnlineCabBooking";
    var menuAnchor = "navigation";
    var menuTemplate = "nsx/knockout/navigation-bar";
    var constants = {};

    // @anchor:page-options:start
    // @anchor:page-options:end
    // anchor:custom-page-options:start
    // anchor:custom-page-options:end

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

    var accordion = page.defineAccordion({
      selector: "main"
    });

    // @anchor:page-after:start
    defineImportCsvPane(accordion);
    // @anchor:page-after:end
    // anchor:custom-page-after:start
    // anchor:custom-page-after:end
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.buildPage = buildPage;
  return exports;
});
