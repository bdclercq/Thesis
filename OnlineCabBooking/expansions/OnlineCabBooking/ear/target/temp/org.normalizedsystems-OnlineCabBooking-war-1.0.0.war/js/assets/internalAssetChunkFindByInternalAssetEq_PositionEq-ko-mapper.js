/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var internalAssetChunkFindByInternalAssetEq_PositionEqElement = nsxApplication.getElement("assets", "internalAssetChunkFindByInternalAssetEq_PositionEq");

  var fieldOptions = {
    // anchor:field-configs:start
    internalAsset: {
      fieldName: "internalAsset",
      type: FieldType.DATAREF,
      key: "assets.InternalAssetChunkFindByInternalAssetEq_PositionEq.internalAsset",
      isValueType: false
    },
    position: {
      fieldName: "position",
      type: FieldType.VALUE,
      key: "assets.InternalAssetChunkFindByInternalAssetEq_PositionEq.position",
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

  function jsToFormModel(internalAssetChunkFindByInternalAssetEq_PositionEq, fieldFilter) {
    var detailModel = jsToViewModel(internalAssetChunkFindByInternalAssetEq_PositionEq);
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

  function jsToViewModel(internalAssetChunkFindByInternalAssetEq_PositionEq) {
    var internalAssetChunkFindByInternalAssetEq_PositionEqViewModel = {
      // anchor:value-fields-to-model:start
      position: ko.observable(internalAssetChunkFindByInternalAssetEq_PositionEq.position),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      internalAsset: ko.observable(internalAssetChunkFindByInternalAssetEq_PositionEq.internalAsset),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return internalAssetChunkFindByInternalAssetEq_PositionEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(internalAssetChunkFindByInternalAssetEq_PositionEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      position: internalAssetChunkFindByInternalAssetEq_PositionEqViewModel.position(),
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      internalAsset: internalAssetChunkFindByInternalAssetEq_PositionEqViewModel.internalAsset(),
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

  var internalAssetChunkFindByInternalAssetEq_PositionEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return internalAssetChunkFindByInternalAssetEq_PositionEqMapper;
});
