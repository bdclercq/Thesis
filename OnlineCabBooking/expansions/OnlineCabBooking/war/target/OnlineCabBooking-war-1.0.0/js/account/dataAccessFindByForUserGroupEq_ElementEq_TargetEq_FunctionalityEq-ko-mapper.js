/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqElement = nsxApplication.getElement("account", "dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq");

  var fieldOptions = {
    // anchor:field-configs:start
    forUserGroup: {
      fieldName: "forUserGroup",
      type: FieldType.DATAREF,
      key: "account.DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.forUserGroup",
      isValueType: false
    },
    element: {
      fieldName: "element",
      type: FieldType.VALUE,
      key: "account.DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.element",
      isValueType: false
    },
    target: {
      fieldName: "target",
      type: FieldType.VALUE,
      key: "account.DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.target",
      isValueType: false
    },
    functionality: {
      fieldName: "functionality",
      type: FieldType.VALUE,
      key: "account.DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.functionality",
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

  function jsToFormModel(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq, fieldFilter) {
    var detailModel = jsToViewModel(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq);
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

  function jsToViewModel(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq) {
    var dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel = {
      // anchor:value-fields-to-model:start
      element: ko.observable(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.element),
      target: ko.observable(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.target),
      functionality: ko.observable(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.functionality),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      forUserGroup: ko.observable(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq.forUserGroup),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      element: dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel.element(),
      target: dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel.target(),
      functionality: dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel.functionality(),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      forUserGroup: dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqViewModel.forUserGroup(),
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

  var dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return dataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqMapper;
});
