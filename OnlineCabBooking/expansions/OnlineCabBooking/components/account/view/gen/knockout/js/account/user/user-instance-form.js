// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var UserEvents = require('account/user/user-events');
  var UserActions = require('account/user/user-actions');
  var UserElement = require('nsx/nsx-application').getElement('account', 'user');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "account.User.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "account.User.name",
      isValueType: false
    },
    {
      fieldName: "password",
      type: FieldType.VALUE,
      key: "account.User.password",
      isValueType: false
    },
    {
      fieldName: "fullName",
      type: FieldType.VALUE,
      key: "account.User.fullName",
      isValueType: false
    },
    {
      fieldName: "email",
      type: FieldType.VALUE,
      key: "account.User.email",
      isValueType: false
    },
    {
      fieldName: "mobile",
      type: FieldType.VALUE,
      key: "account.User.mobile",
      isValueType: false
    },
    {
      fieldName: "language",
      type: FieldType.VALUE,
      key: "account.User.language",
      isValueType: false
    },
    {
      fieldName: "firstName",
      type: FieldType.VALUE,
      key: "account.User.firstName",
      isValueType: false
    },
    {
      fieldName: "lastName",
      type: FieldType.VALUE,
      key: "account.User.lastName",
      isValueType: false
    },
    {
      fieldName: "persNr",
      type: FieldType.VALUE,
      key: "account.User.persNr",
      isValueType: false
    },
    {
      fieldName: "lastModifiedAt",
      type: FieldType.DATE,
      key: "account.User.lastModifiedAt",
      isValueType: false
    },
    {
      fieldName: "enteredAt",
      type: FieldType.DATE,
      key: "account.User.enteredAt",
      isValueType: false
    },
    {
      fieldName: "disabled",
      type: FieldType.VALUE,
      key: "account.User.disabled",
      isValueType: false
    },
    {
      fieldName: "timeout",
      type: FieldType.VALUE,
      key: "account.User.timeout",
      isValueType: false
    },
    {
      fieldName: "account",
      type: FieldType.DATAREF,
      key: "account.User.account",
      isValueType: false
    },
    {
      fieldName: "profile",
      type: FieldType.DATAREF,
      key: "account.User.profile",
      isValueType: false
    },
    {
      fieldName: "userGroups",
      type: FieldType.LIST,
      key: "account.User.userGroups",
      isValueType: false
    },
    {
      fieldName: "encryptedPassword",
      type: FieldType.VALUE,
      key: "account.User.encryptedPassword",
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
   *   action: UserAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || UserActions.editAction();

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
