// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var EngineServiceEvents = require('workflow/engineService/engineService-events');
  var EngineServiceActions = require('workflow/engineService/engineService-actions');
  var EngineServiceElement = require('nsx/nsx-application').getElement('workflow', 'engineService');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "workflow.EngineService.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "workflow.EngineService.name",
      isValueType: false
    },
    {
      fieldName: "status",
      type: FieldType.VALUE,
      key: "workflow.EngineService.status",
      isValueType: false
    },
    {
      fieldName: "changed",
      type: FieldType.VALUE,
      key: "workflow.EngineService.changed",
      isValueType: false
    },
    {
      fieldName: "busy",
      type: FieldType.VALUE,
      key: "workflow.EngineService.busy",
      isValueType: false
    },
    {
      fieldName: "waitTime",
      type: FieldType.VALUE,
      key: "workflow.EngineService.waitTime",
      isValueType: false
    },
    {
      fieldName: "collector",
      type: FieldType.VALUE,
      key: "workflow.EngineService.collector",
      isValueType: false
    },
    {
      fieldName: "workflow",
      type: FieldType.DATAREF,
      key: "workflow.EngineService.workflow",
      isValueType: false
    },
    {
      fieldName: "timeWindowGroup",
      type: FieldType.DATAREF,
      key: "workflow.EngineService.timeWindowGroup",
      isValueType: false
    },
    {
      fieldName: "lastRunAt",
      type: FieldType.DATE,
      key: "workflow.EngineService.lastRunAt",
      isValueType: true
    },
    {
      fieldName: "batchSize",
      type: FieldType.VALUE,
      key: "workflow.EngineService.batchSize",
      isValueType: false
    },
    {
      fieldName: "maximumNumberOfNodes",
      type: FieldType.VALUE,
      key: "workflow.EngineService.maximumNumberOfNodes",
      isValueType: false
    },
    {
      fieldName: "engineNodeServices",
      type: FieldType.LIST,
      key: "workflow.EngineService.engineNodeServices",
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
   *   action: EngineServiceAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || EngineServiceActions.editAction();

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
