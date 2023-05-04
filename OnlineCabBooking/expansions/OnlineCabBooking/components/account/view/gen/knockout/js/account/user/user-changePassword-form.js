// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var PerformCommandAction = require('nsx/action/perform-command-action');
  var Trigger = require('nsx/triggers');
  var ErrorTrigger = require('nsx/error/error-trigger');

  var FieldType = require('nsx/field/field-type');

  var nsxApplication = require('nsx/nsx-application');
  var element = nsxApplication.getElement("account", "user");
  var errorTrigger = ErrorTrigger.defineTrigger();

  var formFields = [
    {
      fieldName: "commandId",
      type: FieldType.VALUE,
      key: "account.changePassword.commandId",
      isValueType: false
    },
    // anchor:form-fields:start
    {
      fieldName: "newPassword",
      type: FieldType.VALUE,
      key: "account.User.changePassword.newPassword",
      isValueType: false
    },
    {
      fieldName: "repeatNewPassword",
      type: FieldType.VALUE,
      key: "account.User.changePassword.repeatNewPassword",
      isValueType: false
    }
    // anchor:form-fields:end
    // @anchor:form-fields:start
    // @anchor:form-fields:end
    // anchor:custom-form-fields:start
    // anchor:custom-form-fields:end
  ];

  /**
   * anchor:signature:start
   * @param {{
   *   data: Object,
   *   target: KnockoutObservable<DataRef>,
   *   layoutConfig: Object
   * }} input
   * @param {{
   *   resetOn: TriggerOutput<any>
   * }} options
   * @return {{
   *   instance: NsxForm,
   *   submit: Trigger<void>
   * }}
   * anchor:signature:end
   */
  function defineCommandForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var actionOptions = {};

    // anchor:action:start
    actionOptions.target = input.target;
    // anchor:action:end
    // @anchor:define-before:start
    // @anchor:define-before:end
    // anchor:custom-define-before:start
    // anchor:custom-define-before:end

    var form = Form.defineFormModel({
      data: input.data,
      fields: formFields
    }, {
      resetOn: options.resetOn
    });

    var performAction = PerformCommandAction.definePerformCommandAction({
      commandName: "changePassword",
      instance: form.dataModel,
      trigger: submit,
      element: element
    }, actionOptions);

    FormError.extendForm({
      model: form,
      fieldErrors: performAction.fieldErrorMap
    });
    FormLayout.extendForm({
      model: form,
      config: input.layoutConfig
    });

    performAction.errorEvent.subscribe(function () {
      errorTrigger.error(performAction.errorMessage());
    });

    // anchor:field-options:start
    // anchor:field-options:end

    // @anchor:define-after:start
    // @anchor:define-after:end
    // anchor:custom-define-after:start
    // anchor:custom-define-after:end

    return {
      instance: form,
      submit: submit,
      success: performAction.successEvent,
      error: performAction.errorEvent,
      complete: performAction.completeEvent,
      messages: performAction.messages
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineCommandForm = defineCommandForm;
  return exports;
});
