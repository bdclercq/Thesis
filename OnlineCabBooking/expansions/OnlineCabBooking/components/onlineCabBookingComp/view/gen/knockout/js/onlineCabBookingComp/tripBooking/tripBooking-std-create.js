// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var TripBookingEvents = require('onlineCabBookingComp/tripBooking/tripBooking-events');
  var TripBookingForm = require('onlineCabBookingComp/tripBooking/tripBooking-instance-form');
  var TripBookingActions = require('onlineCabBookingComp/tripBooking/tripBooking-actions');
  var TripBookingElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'tripBooking');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: TripBookingElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) TripBooking element!")
    });

    function render() {
      var createIntent = TripBookingEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "onlineCabBookingComp/tripBooking-form";
      var title = "TripBooking";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: TripBookingActions.createAction()
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

      var form = TripBookingForm.defineForm({
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
      customer: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().customer)
          })
      },
      driver: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().driver)
          })
      },
      fromLocation: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fromLocation)
          })
      },
      toLocation: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().toLocation)
          })
      },
      fromDateTime: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fromDateTime)
          })
      },
      toDateTime: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().toDateTime)
          })
      },
      km: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().km)
          })
      },
      totalAmount: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().totalAmount)
          })
      },
      payment: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().payment)
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
