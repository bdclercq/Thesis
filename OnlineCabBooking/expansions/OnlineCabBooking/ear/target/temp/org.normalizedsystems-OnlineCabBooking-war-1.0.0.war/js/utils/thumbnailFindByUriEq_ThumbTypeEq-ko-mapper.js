/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var thumbnailFindByUriEq_ThumbTypeEqElement = nsxApplication.getElement("utils", "thumbnailFindByUriEq_ThumbTypeEq");

  var fieldOptions = {
    // anchor:field-configs:start
    thumbType: {
      fieldName: "thumbType",
      type: FieldType.VALUE,
      key: "utils.ThumbnailFindByUriEq_ThumbTypeEq.thumbType",
      isValueType: false
    },
    uri: {
      fieldName: "uri",
      type: FieldType.VALUE,
      key: "utils.ThumbnailFindByUriEq_ThumbTypeEq.uri",
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

  function jsToFormModel(thumbnailFindByUriEq_ThumbTypeEq, fieldFilter) {
    var detailModel = jsToViewModel(thumbnailFindByUriEq_ThumbTypeEq);
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

  function jsToViewModel(thumbnailFindByUriEq_ThumbTypeEq) {
    var thumbnailFindByUriEq_ThumbTypeEqViewModel = {
      // anchor:value-fields-to-model:start
      thumbType: ko.observable(thumbnailFindByUriEq_ThumbTypeEq.thumbType),
      uri: ko.observable(thumbnailFindByUriEq_ThumbTypeEq.uri),
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return thumbnailFindByUriEq_ThumbTypeEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(thumbnailFindByUriEq_ThumbTypeEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      thumbType: thumbnailFindByUriEq_ThumbTypeEqViewModel.thumbType(),
      uri: thumbnailFindByUriEq_ThumbTypeEqViewModel.uri(),
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

  var thumbnailFindByUriEq_ThumbTypeEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return thumbnailFindByUriEq_ThumbTypeEqMapper;
});
