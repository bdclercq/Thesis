// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var TripBookingTaskStatusEvents = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-events');
  var TripBookingTaskStatusActions = require('onlineCabBookingComp/tripBookingTaskStatus/tripBookingTaskStatus-actions');
  var TripBookingTaskStatusElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'tripBookingTaskStatus');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.TripBookingTaskStatus.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.TripBookingTaskStatus.name",
      isValueType: false
    },
    {
      fieldName: "startedAt",
      type: FieldType.DATE,
      key: "onlineCabBookingComp.TripBookingTaskStatus.startedAt",
      isValueType: false
    },
    {
      fieldName: "finishedAt",
      type: FieldType.DATE,
      key: "onlineCabBookingComp.TripBookingTaskStatus.finishedAt",
      isValueType: false
    },
    {
      fieldName: "status",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.TripBookingTaskStatus.status",
      isValueType: false
    },
    {
      fieldName: "stateTask",
      type: FieldType.DATAREF,
      key: "onlineCabBookingComp.TripBookingTaskStatus.stateTask",
      isValueType: false
    },
    {
      fieldName: "tripBooking",
      type: FieldType.DATAREF,
      key: "onlineCabBookingComp.TripBookingTaskStatus.tripBooking",
      isValueType: false
    }
    // anchor:field-configs:end
    // @anchor:field-configs:start
    // @anchor:field-configs:end
    // anchor:custom-field-configs:start
    // anchor:custom-field-configs:end
  ];

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /**
   * @param {{
   *   data: Object,
   *   layoutConfig: NsxFieldLayoutConfig
   * }} input
   * @param {{
   *   resetOn: TriggerOutput<any>,
   *   action: TripBookingTaskStatusAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || TripBookingTaskStatusActions.editAction();

    var fieldErrorMap = ko.observable({});
    var form = Form.defineFormModel({
      data: input.data,
      fields: formFields
    }, {
      resetOn: options.resetOn
    });

    // @anchor:form:start
    // @anchor:form:end
    // anchor:custom-form:start
    // anchor:custom-form:end

    FormError.extendForm({
      model: form,
      fieldErrors: fieldErrorMap
    });
    FormLayout.extendForm({
      model: form,
      config: input.layoutConfig
    });

    Trigger
      .when(submit)
      .thenDo(() => {
          editAction.perform(ko.unwrap(form.dataModel))
        });
    Trigger
      .when(editAction.errorEvent)
      .thenDo(() => {
          fieldErrorMap(editAction.getResult().fieldErrorMap);
        });

    // anchor:field-options:start
    // anchor:field-options:end

    // @anchor:after:start
    // @anchor:after:end
    // anchor:custom-after:start
    // anchor:custom-after:end

    return {
      instance: form,
      submit: submit,
      success: editAction.successEvent,
      error: editAction.errorEvent,
      complete: editAction.completeEvent
    }
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineForm = defineForm;
  return exports;
});
