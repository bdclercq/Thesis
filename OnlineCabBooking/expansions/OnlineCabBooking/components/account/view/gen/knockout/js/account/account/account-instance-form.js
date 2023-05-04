// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var AccountEvents = require('account/account/account-events');
  var AccountActions = require('account/account/account-actions');
  var AccountElement = require('nsx/nsx-application').getElement('account', 'account');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "account.Account.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "account.Account.name",
      isValueType: false
    },
    {
      fieldName: "refId",
      type: FieldType.VALUE,
      key: "account.Account.refId",
      isValueType: false
    },
    {
      fieldName: "fullName",
      type: FieldType.VALUE,
      key: "account.Account.fullName",
      isValueType: false
    },
    {
      fieldName: "address",
      type: FieldType.VALUE,
      key: "account.Account.address",
      isValueType: false
    },
    {
      fieldName: "zipCode",
      type: FieldType.VALUE,
      key: "account.Account.zipCode",
      isValueType: false
    },
    {
      fieldName: "city",
      type: FieldType.VALUE,
      key: "account.Account.city",
      isValueType: false
    },
    {
      fieldName: "country",
      type: FieldType.VALUE,
      key: "account.Account.country",
      isValueType: false
    },
    {
      fieldName: "email",
      type: FieldType.VALUE,
      key: "account.Account.email",
      isValueType: false
    },
    {
      fieldName: "phone",
      type: FieldType.VALUE,
      key: "account.Account.phone",
      isValueType: false
    },
    {
      fieldName: "style",
      type: FieldType.VALUE,
      key: "account.Account.style",
      isValueType: false
    },
    {
      fieldName: "status",
      type: FieldType.VALUE,
      key: "account.Account.status",
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
   *   action: AccountAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || AccountActions.editAction();

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
