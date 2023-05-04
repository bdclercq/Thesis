// custom file
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var PerformCommandAction = require('assets/action/perform-uploadAsset-action');
  var AssetEvents = require('assets/asset/asset-events');
  var Trigger = require('nsx/triggers');
  var ErrorTrigger = require('nsx/error/error-trigger');

  var FieldType = require('nsx/field/field-type');

  var nsxApplication = require('nsx/nsx-application');
  var element = nsxApplication.getElement("assets", "asset");
  var errorTrigger = ErrorTrigger.defineTrigger();

  var formFields = [
    {
      fieldName: "commandId",
      type: FieldType.VALUE,
      key: "assets.uploadAsset.commandId",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "assets.Asset.uploadAsset.name",
      isValueType: false
    },
    {
      fieldName: "type",
      type: FieldType.VALUE,
      key: "assets.Asset.uploadAsset.type",
      isValueType: false
    },
    {
      fieldName: "uploadUri",
      type: FieldType.FILE,
      key: "assets.Asset.uploadAsset.uploadUri",
      isValueType: false
    }
  ];

  /**
   * @param {{
   *   data: Object,
   *   layoutConfig: Object
   * }} input
   * @param {{
   *   resetOn: TriggerOutput<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var updateEvent = AssetEvents.defineCreateUpdateTrigger();

    var form = Form.defineFormModel({
      data: input.data,
      fields: formFields
    }, {
      resetOn: options.resetOn
    });

    var performAction = PerformCommandAction.definePerformCommandAction({
      commandName: "uploadAsset",
      instance: form.dataModel,
      trigger: submit,
      element: element
    });

    FormError.extendForm({
      model: form,
      fieldErrors: performAction.fieldErrorMap
    });

    FormLayout.extendForm({
      model: form,
      config: input.layoutConfig
    });

    performAction.errorMessage.subscribe(errorTrigger.error);
    performAction.successEvent.subscribe(updateEvent.trigger);

    return {
      instance: form,
      submit: submit,
      success: performAction.successEvent,
      error: performAction.errorEvent,
      complete: performAction.completeEvent
    }
  }

  return {
    defineForm: defineForm
  }
});