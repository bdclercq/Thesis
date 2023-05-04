/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var dataAccessFindByForUserEq_FunctionalityEqElement = nsxApplication.getElement("account", "dataAccessFindByForUserEq_FunctionalityEq");

  var fieldOptions = {
    // anchor:field-configs:start
    forUser: {
      fieldName: "forUser",
      type: FieldType.DATAREF,
      key: "account.DataAccessFindByForUserEq_FunctionalityEq.forUser",
      isValueType: false
    },
    functionality: {
      fieldName: "functionality",
      type: FieldType.VALUE,
      key: "account.DataAccessFindByForUserEq_FunctionalityEq.functionality",
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

  function jsToFormModel(dataAccessFindByForUserEq_FunctionalityEq, fieldFilter) {
    var detailModel = jsToViewModel(dataAccessFindByForUserEq_FunctionalityEq);
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

  function jsToViewModel(dataAccessFindByForUserEq_FunctionalityEq) {
    var dataAccessFindByForUserEq_FunctionalityEqViewModel = {
      // anchor:value-fields-to-model:start
      functionality: ko.observable(dataAccessFindByForUserEq_FunctionalityEq.functionality),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      forUser: ko.observable(dataAccessFindByForUserEq_FunctionalityEq.forUser),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return dataAccessFindByForUserEq_FunctionalityEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(dataAccessFindByForUserEq_FunctionalityEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      functionality: dataAccessFindByForUserEq_FunctionalityEqViewModel.functionality(),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      forUser: dataAccessFindByForUserEq_FunctionalityEqViewModel.forUser(),
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

  var dataAccessFindByForUserEq_FunctionalityEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return dataAccessFindByForUserEq_FunctionalityEqMapper;
});
