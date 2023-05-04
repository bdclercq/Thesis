// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var DataAccessEvents = require('account/dataAccess/dataAccess-events');
  var DataAccessActions = require('account/dataAccess/dataAccess-actions');
  var DataAccessElement = require('nsx/nsx-application').getElement('account', 'dataAccess');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "account.DataAccess.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "account.DataAccess.name",
      isValueType: false
    },
    {
      fieldName: "forProfile",
      type: FieldType.DATAREF,
      key: "account.DataAccess.forProfile",
      isValueType: false
    },
    {
      fieldName: "forUser",
      type: FieldType.DATAREF,
      key: "account.DataAccess.forUser",
      isValueType: false
    },
    {
      fieldName: "userGroups",
      type: FieldType.LIST,
      key: "account.DataAccess.userGroups",
      isValueType: false
    },
    {
      fieldName: "element",
      type: FieldType.VALUE,
      key: "account.DataAccess.element",
      isValueType: false
    },
    {
      fieldName: "target",
      type: FieldType.VALUE,
      key: "account.DataAccess.target",
      isValueType: false
    },
    {
      fieldName: "functionality",
      type: FieldType.VALUE,
      key: "account.DataAccess.functionality",
      isValueType: false
    },
    {
      fieldName: "authorized",
      type: FieldType.VALUE,
      key: "account.DataAccess.authorized",
      isValueType: false
    },
    {
      fieldName: "lastModifiedAt",
      type: FieldType.DATE,
      key: "account.DataAccess.lastModifiedAt",
      isValueType: false
    },
    {
      fieldName: "enteredAt",
      type: FieldType.DATE,
      key: "account.DataAccess.enteredAt",
      isValueType: false
    },
    {
      fieldName: "disabled",
      type: FieldType.VALUE,
      key: "account.DataAccess.disabled",
      isValueType: false
    },
    {
      fieldName: "forUserGroup",
      type: FieldType.DATAREF,
      key: "account.DataAccess.forUserGroup",
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
   *   action: DataAccessAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || DataAccessActions.editAction();

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
