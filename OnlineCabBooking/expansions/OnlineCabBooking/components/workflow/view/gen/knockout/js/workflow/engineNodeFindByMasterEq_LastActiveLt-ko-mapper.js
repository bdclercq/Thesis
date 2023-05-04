/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var engineNodeFindByMasterEq_LastActiveLtElement = nsxApplication.getElement("workflow", "engineNodeFindByMasterEq_LastActiveLt");

  var fieldOptions = {
    // anchor:field-configs:start
    master: {
      fieldName: "master",
      type: FieldType.VALUE,
      key: "workflow.EngineNodeFindByMasterEq_LastActiveLt.master",
      isValueType: false
    },
    lastActive: {
      fieldName: "lastActive",
      type: FieldType.DATE,
      key: "workflow.EngineNodeFindByMasterEq_LastActiveLt.lastActive",
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

  function jsToFormModel(engineNodeFindByMasterEq_LastActiveLt, fieldFilter) {
    var detailModel = jsToViewModel(engineNodeFindByMasterEq_LastActiveLt);
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

  function jsToViewModel(engineNodeFindByMasterEq_LastActiveLt) {
    var engineNodeFindByMasterEq_LastActiveLtViewModel = {
      // anchor:value-fields-to-model:start
      master: ko.observable(!!engineNodeFindByMasterEq_LastActiveLt.master),
      lastActive: nsxKnockoutUtils.asObservableDate(engineNodeFindByMasterEq_LastActiveLt.lastActive),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return engineNodeFindByMasterEq_LastActiveLtViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(engineNodeFindByMasterEq_LastActiveLtViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      master: !!engineNodeFindByMasterEq_LastActiveLtViewModel.master(),
      lastActive: nsxKnockoutUtils.fromObservableDate(engineNodeFindByMasterEq_LastActiveLtViewModel.lastActive),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
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

  var engineNodeFindByMasterEq_LastActiveLtMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return engineNodeFindByMasterEq_LastActiveLtMapper;
});
