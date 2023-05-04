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
  var UserProjector = require('account/user/user-projector');
  var UserForm = require('account/user/user-instance-form');
  var UserActions = require('account/user/user-actions');
  var UserElement = require('nsx/nsx-application').getElement('account', 'user');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: UserElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) User element!")
    });

    function render() {
      var editIntent = UserEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "account/user-form";
      var title = "Edit User";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: UserActions.editAction()
      };

      var detailsProjection = UserProjector.defineProjector(selection, {
        projection: "details"
      });

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      layoutConfig.lastModifiedAt = { hidden: true }
      layoutConfig.enteredAt = { hidden: true }
      layoutConfig.encryptedPassword = { hidden: true }
      // anchor:custom-options:end

      when(editIntent).thenDo(function(params) {
        selection(params.selection);
        constants(params.constants);
      });
      when(editIntent)
        .and(detailsProjection.projection)
        .thenTrigger(showDialog);

      var form = UserForm.defineForm({
        data: detailsProjection.projection,
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
        trigger: showDialog
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

  /**
   * @param {KnockoutObservable<any>} constants
   * @returns {Object<string, NsxFieldLayoutConfig>}
   */
  function initLayoutConfig(constants) {
    return {
      // anchor:field-layout-config:start
      id: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().id)
          })
      },
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
  exports.defineEditDialog = defineEditDialog;
  return exports;
});
