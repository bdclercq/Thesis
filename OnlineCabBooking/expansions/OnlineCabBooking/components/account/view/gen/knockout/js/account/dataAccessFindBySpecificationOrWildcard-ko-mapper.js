/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var dataAccessFindBySpecificationOrWildcardElement = nsxApplication.getElement("account", "dataAccessFindBySpecificationOrWildcard");

  var fieldOptions = {
    // anchor:field-configs:start
    forProfile: {
      fieldName: "forProfile",
      type: FieldType.DATAREF,
      key: "account.DataAccessFindBySpecificationOrWildcard.forProfile",
      isValueType: false
    },
    forUserGroup: {
      fieldName: "forUserGroup",
      type: FieldType.DATAREF,
      key: "account.DataAccessFindBySpecificationOrWildcard.forUserGroup",
      isValueType: false
    },
    forUser: {
      fieldName: "forUser",
      type: FieldType.DATAREF,
      key: "account.DataAccessFindBySpecificationOrWildcard.forUser",
      isValueType: false
    },
    element: {
      fieldName: "element",
      type: FieldType.VALUE,
      key: "account.DataAccessFindBySpecificationOrWildcard.element",
      isValueType: false
    },
    target: {
      fieldName: "target",
      type: FieldType.VALUE,
      key: "account.DataAccessFindBySpecificationOrWildcard.target",
      isValueType: false
    },
    functionality: {
      fieldName: "functionality",
      type: FieldType.VALUE,
      key: "account.DataAccessFindBySpecificationOrWildcard.functionality",
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

  function jsToFormModel(dataAccessFindBySpecificationOrWildcard, fieldFilter) {
    var detailModel = jsToViewModel(dataAccessFindBySpecificationOrWildcard);
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

  function jsToViewModel(dataAccessFindBySpecificationOrWildcard) {
    var dataAccessFindBySpecificationOrWildcardViewModel = {
      // anchor:value-fields-to-model:start
      element: ko.observable(dataAccessFindBySpecificationOrWildcard.element),
      target: ko.observable(dataAccessFindBySpecificationOrWildcard.target),
      functionality: ko.observable(dataAccessFindBySpecificationOrWildcard.functionality),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      forProfile: ko.observable(dataAccessFindBySpecificationOrWildcard.forProfile),
      forUserGroup: ko.observable(dataAccessFindBySpecificationOrWildcard.forUserGroup),
      forUser: ko.observable(dataAccessFindBySpecificationOrWildcard.forUser),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return dataAccessFindBySpecificationOrWildcardViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(dataAccessFindBySpecificationOrWildcardViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      element: dataAccessFindBySpecificationOrWildcardViewModel.element(),
      target: dataAccessFindBySpecificationOrWildcardViewModel.target(),
      functionality: dataAccessFindBySpecificationOrWildcardViewModel.functionality(),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      forProfile: dataAccessFindBySpecificationOrWildcardViewModel.forProfile(),
      forUserGroup: dataAccessFindBySpecificationOrWildcardViewModel.forUserGroup(),
      forUser: dataAccessFindBySpecificationOrWildcardViewModel.forUser(),
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

  var dataAccessFindBySpecificationOrWildcardMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return dataAccessFindBySpecificationOrWildcardMapper;
});
