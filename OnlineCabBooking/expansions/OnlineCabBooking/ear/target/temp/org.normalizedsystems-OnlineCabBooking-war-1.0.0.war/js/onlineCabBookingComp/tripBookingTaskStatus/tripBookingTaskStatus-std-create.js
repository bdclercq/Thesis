// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var TripBookingTaskStatusEvents = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-events');
  var TripBookingTaskStatusForm = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-instance-form');
  var TripBookingTaskStatusActions = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-actions');
  var TripBookingTaskStatusElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'tripBookingTaskStatus');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: TripBookingTaskStatusElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) TripBookingTaskStatus element!")
    });

    function render() {
      var createIntent = TripBookingTaskStatusEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "onlineCabBookingComp/tripBookingTaskStatus-form";
      var title = "TripBookingTaskStatus";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: TripBookingTaskStatusActions.createAction()
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

      var form = TripBookingTaskStatusForm.defineForm({
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
      startedAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().startedAt)
          })
      },
      finishedAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().finishedAt)
          })
      },
      status: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().status)
          })
      },
      stateTask: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().stateTask)
          })
      },
      tripBooking: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().tripBooking)
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
