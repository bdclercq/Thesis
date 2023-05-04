/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var workflowFindByResponsibleEqElement = nsxApplication.getElement("workflow", "workflowFindByResponsibleEq");

  var fieldOptions = {
    // anchor:field-configs:start
    responsible: {
      fieldName: "responsible",
      type: FieldType.DATAREF,
      key: "workflow.WorkflowFindByResponsibleEq.responsible",
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

  function jsToFormModel(workflowFindByResponsibleEq, fieldFilter) {
    var detailModel = jsToViewModel(workflowFindByResponsibleEq);
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

  function jsToViewModel(workflowFindByResponsibleEq) {
    var workflowFindByResponsibleEqViewModel = {
      // anchor:value-fields-to-model:start
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      responsible: ko.observable(workflowFindByResponsibleEq.responsible),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return workflowFindByResponsibleEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(workflowFindByResponsibleEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      responsible: workflowFindByResponsibleEqViewModel.responsible(),
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

  var workflowFindByResponsibleEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return workflowFindByResponsibleEqMapper;
});
