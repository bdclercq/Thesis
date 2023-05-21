// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var InvoiceEvents = require('onlineCabBookingComp/invoice/invoice-events');
  var InvoiceForm = require('onlineCabBookingComp/invoice/invoice-instance-form');
  var InvoiceActions = require('onlineCabBookingComp/invoice/invoice-actions');
  var InvoiceElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'invoice');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: InvoiceElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) Invoice element!")
    });

    function render() {
      var createIntent = InvoiceEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "onlineCabBookingComp/invoice-form";
      var title = "Invoice";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: InvoiceActions.createAction()
      };

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      // anchor:custom-options:end

      when(createIntent).thenDo(function(params) {
        constants(params.constants);
        values(params.values);
      });

      var formData = ko.pureComputed(function() {
        return utils.combine(defaultValues, constants(), values());
      });

      var form = InvoiceForm.defineForm({
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
      payment: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().payment)
          })
      },
      customer: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().customer)
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
