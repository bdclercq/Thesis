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
  var InvoiceProjector = require('onlineCabBookingComp/invoice/invoice-projector');
  var InvoiceForm = require('onlineCabBookingComp/invoice/invoice-instance-form');
  var InvoiceActions = require('onlineCabBookingComp/invoice/invoice-actions');
  var InvoiceElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'invoice');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: InvoiceElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) Invoice element!")
    });

    function render() {
      var editIntent = InvoiceEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "onlineCabBookingComp/invoice-form";
      var title = "Edit Invoice";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: InvoiceActions.editAction()
      };

      var detailsProjection = InvoiceProjector.defineProjector(selection, {
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

      var form = InvoiceForm.defineForm({
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
  exports.defineEditDialog = defineEditDialog;
  return exports;
});
