/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var engineNodeServiceFindByEngineNodeEq_EngineServiceEqElement = nsxApplication.getElement("workflow", "engineNodeServiceFindByEngineNodeEq_EngineServiceEq");

  var fieldOptions = {
    // anchor:field-configs:start
    engineNode: {
      fieldName: "engineNode",
      type: FieldType.DATAREF,
      key: "workflow.EngineNodeServiceFindByEngineNodeEq_EngineServiceEq.engineNode",
      isValueType: false
    },
    engineService: {
      fieldName: "engineService",
      type: FieldType.DATAREF,
      key: "workflow.EngineNodeServiceFindByEngineNodeEq_EngineServiceEq.engineService",
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

  function jsToFormModel(engineNodeServiceFindByEngineNodeEq_EngineServiceEq, fieldFilter) {
    var detailModel = jsToViewModel(engineNodeServiceFindByEngineNodeEq_EngineServiceEq);
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

  function jsToViewModel(engineNodeServiceFindByEngineNodeEq_EngineServiceEq) {
    var engineNodeServiceFindByEngineNodeEq_EngineServiceEqViewModel = {
      // anchor:value-fields-to-model:start
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      engineNode: ko.observable(engineNodeServiceFindByEngineNodeEq_EngineServiceEq.engineNode),
      engineService: ko.observable(engineNodeServiceFindByEngineNodeEq_EngineServiceEq.engineService),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return engineNodeServiceFindByEngineNodeEq_EngineServiceEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(engineNodeServiceFindByEngineNodeEq_EngineServiceEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      engineNode: engineNodeServiceFindByEngineNodeEq_EngineServiceEqViewModel.engineNode(),
      engineService: engineNodeServiceFindByEngineNodeEq_EngineServiceEqViewModel.engineService(),
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

  var engineNodeServiceFindByEngineNodeEq_EngineServiceEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return engineNodeServiceFindByEngineNodeEq_EngineServiceEqMapper;
});
