// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var UserEvents = require('account/user/user-events');
  var UserForm = require('account/user/user-instance-form');
  var UserActions = require('account/user/user-actions');
  var UserElement = require('nsx/nsx-application').getElement('account', 'user');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: UserElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) User element!")
    });

    function render() {
      var createIntent = UserEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "account/user-form";
      var title = "User";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: UserActions.createAction()
      };

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      layoutConfig.lastModifiedAt = { hidden: true }
      layoutConfig.enteredAt = { hidden: true }
      layoutConfig.encryptedPassword = { hidden: true }
      // anchor:custom-options:end

      when(createIntent).thenDo(function(params) {
        constants(params.constants);
        values(params.values);
      });

      var formData = ko.pureComputed(function() {
        return utils.combine(defaultValues, constants(), values());
      });

      var form = UserForm.defineForm({
        data: formData,
        layoutConfig: layoutConfig
      }, formOptions);

      // @anchor:form:start
      // @anchor:form:end
      // anchor:custom-form:start
      // anchor:custom-form:end

      Popup.definePopup({
        view: formView,
        viewModel: form,
        title: title,
        trigger: createIntent
      }, {
        onConfirm: form.submit,
        closeOn: form.success
      });
      loaded.trigger();
    }

    return {
      loaded: loaded
    }
  }

  function initLayoutConfig(constants) {
    return {
      // anchor:field-layout-config:start
      name: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().name)
          })
      },
      password: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().password)
          })
      },
      fullName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fullName)
          })
      },
      email: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().email)
          })
      },
      mobile: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().mobile)
          })
      },
      language: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().language)
          })
      },
      firstName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().firstName)
          })
      },
      lastName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().lastName)
          })
      },
      persNr: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().persNr)
          })
      },
      lastModifiedAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().lastModifiedAt)
          })
      },
      enteredAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().enteredAt)
          })
      },
      disabled: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().disabled)
          })
      },
      timeout: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().timeout)
          })
      },
      account: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().account)
          })
      },
      profile: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().profile)
          })
      },
      userGroups: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().userGroups)
          })
      },
      encryptedPassword: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().encryptedPassword)
          })
      }
      // anchor:field-layout-config:end
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineCreateDialog = defineCreateDialog;
  return exports;
});
