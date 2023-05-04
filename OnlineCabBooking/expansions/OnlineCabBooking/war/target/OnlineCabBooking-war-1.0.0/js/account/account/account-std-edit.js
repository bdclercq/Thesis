// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var AccountEvents = require('account/account/account-events');
  var AccountProjector = require('account/account/account-projector');
  var AccountForm = require('account/account/account-instance-form');
  var AccountActions = require('account/account/account-actions');
  var AccountElement = require('nsx/nsx-application').getElement('account', 'account');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: AccountElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) Account element!")
    });

    function render() {
      var editIntent = AccountEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "account/account-form";
      var title = "Edit Account";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: AccountActions.editAction()
      };

      var detailsProjection = AccountProjector.defineProjector(selection, {
        projection: "details"
      });

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      // anchor:custom-options:end

      when(editIntent).thenDo(function(params) {
        selection(params.selection);
        constants(params.constants);
      });
      when(editIntent)
        .and(detailsProjection.projection)
        .thenTrigger(showDialog);

      var form = AccountForm.defineForm({
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
      refId: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().refId)
          })
      },
      fullName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fullName)
          })
      },
      address: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().address)
          })
      },
      zipCode: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().zipCode)
          })
      },
      city: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().city)
          })
      },
      country: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().country)
          })
      },
      email: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().email)
          })
      },
      phone: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().phone)
          })
      },
      style: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().style)
          })
      },
      status: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().status)
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
