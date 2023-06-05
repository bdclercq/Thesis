// @feature:user-info
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

  var UserEvents = require('account/user/user-events');
  var UserProjector = require('account/user/user-projector');
  var ChangePasswordForm = require('account/user/user-changePassword-form');

  var AccountDetailsInstance = require('account/account/account-details-instance-model');
  var ProfileDetailsInstance = require('account/profile/profile-details-instance-model');

  var UserForm = require('account/user/user-instance-form');

  var DataRef = require('nsx/model/dataref');

  var intentHandlerLoader = require('nsx/config/intent-handler').getIntentHandlerLoader();

  var Popup = require('nsx/injectors/popup');
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

  function getUserDataRef() {
    var urlMatch = location.href.match(/user\/(\d+)/);
    if (urlMatch.length < 2) {
      throw new Error("Missing user id");
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
        key: "account.user.confirm.edit",
        source: null
      }),
      label: "save changes",
      layout: "btn-primary"
    });

    toolbar.defineButton({
      trigger: UserEvents.defineRefreshUpdateTrigger(),
      label: "reset",
      icon: "icon-refresh"
    });

    toolbar.defineButton({
      trigger: Triggers.defineEventTrigger({
        key: "account.user.intent.changePassword",
        source: null
      }),
      label: "change password",
      icon: "icon-cog"
    });
  }


  function defineUserForm(page, input) {
    var editConfirm = Triggers.defineEventListener({
      key: "account.user.confirm.edit"
    });

    var layoutConfig = {
      lastModifiedAt: {hidden: true},
      enteredAt: {hidden: true},
      password: {hidden: true},
      encryptedPassword: {hidden: true},
      userGroups: {hidden: true},
      profile: {disabled: true},
      account: {disabled: true}
    };

    var form = UserForm.defineForm({
      data: input.userDetails,
      layoutConfig: layoutConfig
    }, {
      resetOn: UserEvents.defineRefreshUpdateListener()
    });

    Triggers.when(editConfirm).thenTrigger(form.submit);

    page.definePageSegment({
      view: "account/user-form",
      viewModel: form,
      selector: "userForm"
    }, {
      wrapWith: "nsx/bootstrap/well"
    });
  }

  function defineAccountTab(tabs, input) {
    var accountModel = AccountDetailsInstance.defineInstanceView({
      details: input.account
    });

    tabs.defineTab({
      name: translate("account.user.account"),
      view: "account/account-details-instance",
      viewModel: accountModel
    })
  }


  function defineProfileTab(tabs, input) {
    var profileModel = ProfileDetailsInstance.defineInstanceView({
      details: input.profile
    });

    tabs.defineTab({
      name: translate("account.user.profile"),
      view: "account/profile-details-instance",
      viewModel: profileModel
    })
  }

  function defineUserGroupsTab(tabs, input) {
    var tab = tabs.defineTab({
      name: translate("account.user.userGroups"),
      view: "account/userGroups-list",
      viewModel: {}
    });
    tab.definePageSegment({
      view: "account/userGroup-link-list",
      viewModel: {
        list: {
          items: input.userGroups
        }
      },
      selector: "list"
    }, {
      wrapWith: "nsx/bootstrap/table"
    })
  }

  function defineChangePasswordDialog(input) {
    var changePwIntent = Triggers.defineEventListener({
      key: "account.user.intent.changePassword"
    });
    var userUpdate = UserEvents.defineEditUpdateTrigger();
    var commandForm = ChangePasswordForm.defineCommandForm({
      data: {},
      target: input.user,
      layoutConfig: {}
    }, {
      resetOn: changePwIntent
    });

    Popup.definePopup({
      title: "Change Password",
      view: "account/user-changePassword-form",
      viewModel: commandForm,
      trigger: changePwIntent
    }, {
      onConfirm: commandForm.submit,
      closeOn: commandForm.success
    });

    Triggers.when(commandForm.success)
      .thenTrigger(userUpdate);
  }

  function buildPage() {
    var user = getUserDataRef();

    var name = ko.observable();
    var account = ko.observable();
    var profile = ko.observable();
    var userGroups = ko.observableArray();

    var template = "account/user-page-template";
    var viewModel = {};
    var title = format("{title} - {name}", {
      title: translate("account.User"),
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

    var projector = UserProjector.defineProjector(ko.observable(user), {
      projection: "details"
    });

    Triggers.when(projector.projection).thenDo(function (userDetails) {
      name(userDetails.name);
      account(userDetails.accountDetails);
      profile(userDetails.profileDetails);
      userGroups(userDetails.userGroups);
    })

    defineErrorMessages(page);

    defineFormToolbar(page, {});

    defineUserForm(page, {
      userDetails: projector.projection
    });

    var tabs = page.defineTabs({
      selector: "right"
    });

    defineAccountTab(tabs, {
      account: account
    });
    defineProfileTab(tabs, {
      profile: profile
    });

    defineUserGroupsTab(tabs, {
      userGroups: userGroups
    })

    defineChangePasswordDialog({
      user: user
    })

    intentHandlerLoader.startListening();
  }

  return {
    buildPage: buildPage
  }

});