// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Form = require('nsx/form/form-model');
  var FormError = require('nsx/form/form-error-extender');
  var FormLayout = require('nsx/form/form-layout-extender');

  var Trigger = require('nsx/triggers');
  var FieldType = require('nsx/field/field-type');

  var AddressEvents = require('onlineCabBookingComp/address/address-events');
  var AddressActions = require('onlineCabBookingComp/address/address-actions');
  var AddressElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'address');

  var formFields = [
    // anchor:field-configs:start
    {
      fieldName: "id",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.id",
      isValueType: false
    },
    {
      fieldName: "state",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.state",
      isValueType: false
    },
    {
      fieldName: "city",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.city",
      isValueType: false
    },
    {
      fieldName: "pincode",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.pincode",
      isValueType: false
    },
    {
      fieldName: "street",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.street",
      isValueType: false
    },
    {
      fieldName: "houseNumber",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.houseNumber",
      isValueType: false
    },
    {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "onlineCabBookingComp.Address.name",
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
   *   action: AddressAction<any>
   * }} options
   * @return {{
   *    instance: NsxForm,
   *    submit: Trigger<void>
   * }}
   */
  function defineForm(input, options) {
    options = options || {};

    var submit = Trigger.defineTrigger();
    var editAction = options.action || AddressActions.editAction();

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
