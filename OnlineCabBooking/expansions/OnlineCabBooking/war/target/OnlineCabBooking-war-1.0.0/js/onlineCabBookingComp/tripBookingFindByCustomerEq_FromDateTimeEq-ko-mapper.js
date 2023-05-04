/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var tripBookingFindByCustomerEq_FromDateTimeEqElement = nsxApplication.getElement("onlineCabBookingComp", "tripBookingFindByCustomerEq_FromDateTimeEq");

  var fieldOptions = {
    // anchor:field-configs:start
    customer: {
      fieldName: "customer",
      type: FieldType.DATAREF,
      key: "onlineCabBookingComp.TripBookingFindByCustomerEq_FromDateTimeEq.customer",
      isValueType: false
    },
    fromDateTime: {
      fieldName: "fromDateTime",
      type: FieldType.DATE,
      key: "onlineCabBookingComp.TripBookingFindByCustomerEq_FromDateTimeEq.fromDateTime",
      isValueType: false
    }
    // anchor:field-configs:end
    // @anchor:field-configs:start
    // @anchor:field-configs:end
    // anchor:custom-field-configs:start
    // anchor:custom-field-configs:end
  };

  // anchor:custom-javascript-before:start
  // anchor:custom-javascript-before:end

  function jsToFormModel(tripBookingFindByCustomerEq_FromDateTimeEq, fieldFilter) {
    var detailModel = jsToViewModel(tripBookingFindByCustomerEq_FromDateTimeEq);
    var form = {
      detailModel: detailModel,
      isFieldVisible: fieldFilter ? fieldFilter : _ => true,
      isFieldEnabled: function () {
        return true;
      },
      editMode: undefined
    }
    for (const fieldName in detailModel) {
      if (fieldName in fieldOptions) {
        form[fieldName] = FieldModel.defineField(fieldOptions[fieldName]);
        form[fieldName].value = detailModel[fieldName];
      }
    }
    // anchor:field-options:start
    // anchor:field-options:end
    // @anchor:field-options:start
    // @anchor:field-options:end

    // @anchor:after:start
    // @anchor:after:end
    // anchor:custom-after:start
    // anchor:custom-after:end

    return form;
  }

  function jsToViewModel(tripBookingFindByCustomerEq_FromDateTimeEq) {
    var tripBookingFindByCustomerEq_FromDateTimeEqViewModel = {
      // anchor:value-fields-to-model:start
      fromDateTime: nsxKnockoutUtils.asObservableDate(tripBookingFindByCustomerEq_FromDateTimeEq.fromDateTime),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      customer: ko.observable(tripBookingFindByCustomerEq_FromDateTimeEq.customer),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return tripBookingFindByCustomerEq_FromDateTimeEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(tripBookingFindByCustomerEq_FromDateTimeEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      fromDateTime: nsxKnockoutUtils.fromObservableDate(tripBookingFindByCustomerEq_FromDateTimeEqViewModel.fromDateTime),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      customer: tripBookingFindByCustomerEq_FromDateTimeEqViewModel.customer(),
      // anchor:dataRef-fields-from-model:end

      // anchor:manyToMany-fields-from-model:start
      // anchor:manyToMany-fields-from-model:end
    };

    // anchor:custom-model-to-json:start
    // anchor:custom-model-to-json:end

    return jsData;
  }

  function wrapValue(value) {
    if (value) {
      return {
        value: value
      }
    } else {
      return undefined;
    }
  }

  var tripBookingFindByCustomerEq_FromDateTimeEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return tripBookingFindByCustomerEq_FromDateTimeEqMapper;
});
