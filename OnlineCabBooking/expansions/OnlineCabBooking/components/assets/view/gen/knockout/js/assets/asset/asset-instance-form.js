// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var AssetEvents = require('assets/asset/asset-events');
  var AssetActions = require('assets/asset/asset-actions');
  var AssetElement = require('nsx/nsx-application').getElement('assets', 'asset');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "assets.Asset.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "assets.Asset.name",
      isValueType: false
    },
    {
      fieldName: "type",
      type: FieldType.VALUE,
      key: "assets.Asset.type",
      isValueType: false
    },
    {
      fieldName: "fileAsset",
      type: FieldType.DATAREF,
      key: "assets.Asset.fileAsset",
      isValueType: false
    },
    {
      fieldName: "internalAsset",
      type: FieldType.DATAREF,
      key: "assets.Asset.internalAsset",
      isValueType: false
    },
    {
      fieldName: "downloadLink",
      type: FieldType.VALUE,
      key: "assets.Asset.downloadLink",
      isValueType: false
    },
    {
      fieldName: "contentType",
      type: FieldType.VALUE,
      key: "assets.Asset.contentType",
      isValueType: false
    },
    {
      fieldName: "byteSize",
      type: FieldType.VALUE,
      key: "assets.Asset.byteSize",
      isValueType: false
    },
    {
      fieldName: "fileSize",
      type: FieldType.VALUE,
      key: "assets.Asset.fileSize",
      isValueType: false
    },
    {
      fieldName: "fileId",
      type: FieldType.VALUE,
      key: "assets.Asset.fileId",
      isValueType: false
    },
    {
      fieldName: "complete",
      type: FieldType.VALUE,
      key: "assets.Asset.complete",
      isValueType: false
    },
    {
      fieldName: "externalAsset",
      type: FieldType.DATAREF,
      key: "assets.Asset.externalAsset",
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
   *   action: AssetAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || AssetActions.editAction();

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
