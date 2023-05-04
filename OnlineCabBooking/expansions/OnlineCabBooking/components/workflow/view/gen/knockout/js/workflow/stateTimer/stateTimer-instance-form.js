// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var StateTimerEvents = require('workflow/stateTimer/stateTimer-events');
  var StateTimerActions = require('workflow/stateTimer/stateTimer-actions');
  var StateTimerElement = require('nsx/nsx-application').getElement('workflow', 'stateTimer');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.name",
      isValueType: false
    },
    {
      fieldName: "processor",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.processor",
      isValueType: false
    },
    {
      fieldName: "implementation",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.implementation",
      isValueType: false
    },
    {
      fieldName: "params",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.params",
      isValueType: false
    },
    {
      fieldName: "beginState",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.beginState",
      isValueType: false
    },
    {
      fieldName: "targetState",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.targetState",
      isValueType: false
    },
    {
      fieldName: "alteredState",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.alteredState",
      isValueType: false
    },
    {
      fieldName: "allowedPeriod",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.allowedPeriod",
      isValueType: false
    },
    {
      fieldName: "requiredAction",
      type: FieldType.VALUE,
      key: "workflow.StateTimer.requiredAction",
      isValueType: false
    },
    {
      fieldName: "workflow",
      type: FieldType.DATAREF,
      key: "workflow.StateTimer.workflow",
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
   *   action: StateTimerAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || StateTimerActions.editAction();

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
