/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var dataAccessFindByForProfileEq_ElementEqElement = nsxApplication.getElement("account", "dataAccessFindByForProfileEq_ElementEq");

  var fieldOptions = {
    // anchor:field-configs:start
    element: {
      fieldName: "element",
      type: FieldType.VALUE,
      key: "account.DataAccessFindByForProfileEq_ElementEq.element",
      isValueType: false
    },
    forProfile: {
      fieldName: "forProfile",
      type: FieldType.DATAREF,
      key: "account.DataAccessFindByForProfileEq_ElementEq.forProfile",
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

  function jsToFormModel(dataAccessFindByForProfileEq_ElementEq, fieldFilter) {
    var detailModel = jsToViewModel(dataAccessFindByForProfileEq_ElementEq);
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

  function jsToViewModel(dataAccessFindByForProfileEq_ElementEq) {
    var dataAccessFindByForProfileEq_ElementEqViewModel = {
      // anchor:value-fields-to-model:start
      element: ko.observable(dataAccessFindByForProfileEq_ElementEq.element),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      forProfile: ko.observable(dataAccessFindByForProfileEq_ElementEq.forProfile),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return dataAccessFindByForProfileEq_ElementEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(dataAccessFindByForProfileEq_ElementEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      element: dataAccessFindByForProfileEq_ElementEqViewModel.element(),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      forProfile: dataAccessFindByForProfileEq_ElementEqViewModel.forProfile(),
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

  var dataAccessFindByForProfileEq_ElementEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return dataAccessFindByForProfileEq_ElementEqMapper;
});
