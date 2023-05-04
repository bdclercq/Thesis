// @feature:account-info
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var translate = require('nsx/util/text-utils').translate;
  var format = require('nsx/util/text-utils').format;

  var Menu = require('nsx/menu');
  var Triggers = require('nsx/triggers');
  var Page = require('nsx/injectors/page-segment');

  var ErrorView = require('nsx/error/error-view');
  var ErrorLevel = require('nsx/error/error-levels');
  var ErrorPriorities = require('nsx/error/error-handler-priorities');

  var AccountComponentInfo = require('account/account-component-info');
  var AccountEvents = require('account/account/account-events');
  var AccountProjector = require('account/account/account-projector');
  var AccountForm = require('account/account/account-instance-form');

  var UserTable = require('account/user/user-table-view');

  var DataRef = require('nsx/model/dataref');

  var intentHandlerLoader = require('nsx/config/intent-handler').getIntentHandlerLoader();

  require('nsx/injectors/toolbar');
  require('nsx/injectors/tabs');


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

  function getAccountDataRef() {
    var urlMatch = location.href.match(/account\/account\/(\d+)/);
    if (urlMatch.length < 2) {
      throw new Error("Missing account id");
    }
    var id = parseInt(urlMatch[1]);
    return DataRef.createDataRef("", id);
  }

  function defineFormToolbar(page, input) {
    var toolbar = page.defineButtonToolbar({
      selector: "toolbar"
    });

    toolbar.defineButton({
      trigger: Triggers.defineEventTrigger({
        key: "account.account.confirm.edit",
        source: null
      }),
      label: "save changes",
      layout: "btn-primary"
    });

    toolbar.defineButton({
      trigger: AccountEvents.defineRefreshUpdateTrigger(),
      label: "reset",
      icon: "icon-refresh"
    });

  }


  function defineAccountForm(page, input) {
    var editConfirm = Triggers.defineEventListener({
      key: "account.account.confirm.edit"
    });

    var layoutConfig = {};

    var form = AccountForm.defineForm({
      data: input.accountDetails,
      layoutConfig: layoutConfig
    }, {
      resetOn: AccountEvents.defineRefreshUpdateListener()
    });

    Triggers.when(editConfirm).thenTrigger(form.submit);

    page.definePageSegment({
      view: "account/account-form",
      viewModel: form,
      selector: "accountForm"
    }, {
      wrapWith: "nsx/bootstrap/well"
    });
  }

  function defineUserTable(page, input) {
    UserTable.defineTableView({
      injector: page,
      selector: "right",
      constants: {
        account: input.account
      },
      resetOn: AccountEvents.defineRefreshUpdateListener()
    })
  }


  function buildPage() {
    var account = getAccountDataRef();

    var name = ko.observable();

    var template = "account/account-page-template";
    var viewModel = {};
    var title = format("{title} - {name}", {
      title: translate("account.Account"),
      name: name
    });
    var menuName = AccountComponentInfo.applicationName;
    var menuAnchor = "navigation";
    var menuTemplate = "nsx/knockout/navigation-bar";

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

    var projector = AccountProjector.defineProjector(ko.observable(account), {
      projection: "details"
    });

    Triggers.when(projector.projection).thenDo(function (accountDetails) {
      name(accountDetails.name);
      account.name = accountDetails.name;
    })

    defineErrorMessages(page);
    defineFormToolbar(page, {});
    defineAccountForm(page, {
      accountDetails: projector.projection
    });
    defineUserTable(page, {
      account: account
    })


    intentHandlerLoader.startListening();
  }

  return {
    buildPage: buildPage
  }

});