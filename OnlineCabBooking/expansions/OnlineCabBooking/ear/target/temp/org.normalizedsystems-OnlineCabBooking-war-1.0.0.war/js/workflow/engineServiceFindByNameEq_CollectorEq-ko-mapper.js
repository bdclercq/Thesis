/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var engineServiceFindByNameEq_CollectorEqElement = nsxApplication.getElement("workflow", "engineServiceFindByNameEq_CollectorEq");

  var fieldOptions = {
    // anchor:field-configs:start
    collector: {
      fieldName: "collector",
      type: FieldType.VALUE,
      key: "workflow.EngineServiceFindByNameEq_CollectorEq.collector",
      isValueType: false
    },
    name: {
      fieldName: "name",
      type: FieldType.VALUE,
      key: "workflow.EngineServiceFindByNameEq_CollectorEq.name",
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

  function jsToFormModel(engineServiceFindByNameEq_CollectorEq, fieldFilter) {
    var detailModel = jsToViewModel(engineServiceFindByNameEq_CollectorEq);
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

  function jsToViewModel(engineServiceFindByNameEq_CollectorEq) {
    var engineServiceFindByNameEq_CollectorEqViewModel = {
      // anchor:value-fields-to-model:start
      collector: ko.observable(engineServiceFindByNameEq_CollectorEq.collector),
      name: ko.observable(engineServiceFindByNameEq_CollectorEq.name),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return engineServiceFindByNameEq_CollectorEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(engineServiceFindByNameEq_CollectorEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      collector: engineServiceFindByNameEq_CollectorEqViewModel.collector(),
      name: engineServiceFindByNameEq_CollectorEqViewModel.name(),
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

  var engineServiceFindByNameEq_CollectorEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return engineServiceFindByNameEq_CollectorEqMapper;
});
