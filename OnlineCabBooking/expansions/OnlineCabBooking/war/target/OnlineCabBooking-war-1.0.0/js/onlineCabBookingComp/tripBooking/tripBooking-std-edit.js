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
  var TripBookingProjector = require('onlineCabBookingComp/tripBooking/tripBooking-projector');
  var TripBookingForm = require('onlineCabBookingComp/tripBooking/tripBooking-instance-form');
  var TripBookingActions = require('onlineCabBookingComp/tripBooking/tripBooking-actions');
  var TripBookingElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'tripBooking');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: TripBookingElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) TripBooking element!")
    });

    function render() {
      var editIntent = TripBookingEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "onlineCabBookingComp/tripBooking-form";
      var title = "Edit TripBooking";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: TripBookingActions.editAction()
      };

      var detailsProjection = TripBookingProjector.defineProjector(selection, {
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

      var form = TripBookingForm.defineForm({
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
      payment: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().payment)
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
