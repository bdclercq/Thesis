// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var ThumbnailEvents = require('utils/thumbnail/thumbnail-events');
  var ThumbnailActions = require('utils/thumbnail/thumbnail-actions');
  var ThumbnailElement = require('nsx/nsx-application').getElement('utils', 'thumbnail');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.id",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.name",
      isValueType: false
    },
    {
      fieldName: "fullName",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.fullName",
      isValueType: false
    },
    {
      fieldName: "uri",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.uri",
      isValueType: false
    },
    {
      fieldName: "depth",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.depth",
      isValueType: false
    },
    {
      fieldName: "border",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.border",
      isValueType: false
    },
    {
      fieldName: "thumbType",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.thumbType",
      isValueType: false
    },
    {
      fieldName: "thumbName",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.thumbName",
      isValueType: false
    },
    {
      fieldName: "targetType",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.targetType",
      isValueType: false
    },
    {
      fieldName: "targetName",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.targetName",
      isValueType: false
    },
    {
      fieldName: "targetId",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.targetId",
      isValueType: false
    },
    {
      fieldName: "leftX",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.leftX",
      isValueType: false
    },
    {
      fieldName: "topY",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.topY",
      isValueType: false
    },
    {
      fieldName: "width",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.width",
      isValueType: false
    },
    {
      fieldName: "height",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.height",
      isValueType: false
    },
    {
      fieldName: "clickAction",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.clickAction",
      isValueType: false
    },
    {
      fieldName: "hooverAction",
      type: FieldType.VALUE,
      key: "utils.Thumbnail.hooverAction",
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
   *   action: ThumbnailAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || ThumbnailActions.editAction();

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
