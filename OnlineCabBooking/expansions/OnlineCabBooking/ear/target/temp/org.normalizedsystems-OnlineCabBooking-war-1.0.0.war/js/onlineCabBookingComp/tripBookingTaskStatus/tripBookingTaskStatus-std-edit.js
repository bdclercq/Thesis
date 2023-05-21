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
  var TripBookingTaskStatusProjector = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-projector');
  var TripBookingTaskStatusForm = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-instance-form');
  var TripBookingTaskStatusActions = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-actions');
  var TripBookingTaskStatusElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'tripBookingTaskStatus');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: TripBookingTaskStatusElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) TripBookingTaskStatus element!")
    });

    function render() {
      var editIntent = TripBookingTaskStatusEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "onlineCabBookingComp/tripBookingTaskStatus-form";
      var title = "Edit TripBookingTaskStatus";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: TripBookingTaskStatusActions.editAction()
      };

      var detailsProjection = TripBookingTaskStatusProjector.defineProjector(selection, {
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

      var form = TripBookingTaskStatusForm.defineForm({
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
  exports.defineEditDialog = defineEditDialog;
  return exports;
});
