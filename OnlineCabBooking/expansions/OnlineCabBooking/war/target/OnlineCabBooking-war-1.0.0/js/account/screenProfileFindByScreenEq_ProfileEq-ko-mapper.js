/* expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1 */
define(function(require) {
  var ko               = require('knockoutjs');
  var nsxApplication   = require('nsx/nsx-application');
  var nsxKnockoutUtils = require('nsx/nsx-knockout-utils');
  var FieldType = require('nsx/field/field-type');
  var FieldModel = require('nsx/field/field-model');

  var screenProfileFindByScreenEq_ProfileEqElement = nsxApplication.getElement("account", "screenProfileFindByScreenEq_ProfileEq");

  var fieldOptions = {
    // anchor:field-configs:start
    profile: {
      fieldName: "profile",
      type: FieldType.DATAREF,
      key: "account.ScreenProfileFindByScreenEq_ProfileEq.profile",
      isValueType: false
    },
    screen: {
      fieldName: "screen",
      type: FieldType.DATAREF,
      key: "account.ScreenProfileFindByScreenEq_ProfileEq.screen",
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

  function jsToFormModel(screenProfileFindByScreenEq_ProfileEq, fieldFilter) {
    var detailModel = jsToViewModel(screenProfileFindByScreenEq_ProfileEq);
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

  function jsToViewModel(screenProfileFindByScreenEq_ProfileEq) {
    var screenProfileFindByScreenEq_ProfileEqViewModel = {
      // anchor:value-fields-to-model:start
      // anchor:value-fields-to-model:end

      // anchor:dataRef-fields-to-model:start
      profile: ko.observable(screenProfileFindByScreenEq_ProfileEq.profile),
      screen: ko.observable(screenProfileFindByScreenEq_ProfileEq.screen),
      // anchor:dataRef-fields-to-model:end

      // anchor:manyToMany-fields-to-model:start
      // anchor:manyToMany-fields-to-model:end
    };

    // anchor:custom-json-to-model:start
    // anchor:custom-json-to-model:end

    return screenProfileFindByScreenEq_ProfileEqViewModel;
  }

  // this should be an instance of the model defined above
  function viewModelToJS(screenProfileFindByScreenEq_ProfileEqViewModel) {
    var jsData = {
      // anchor:value-fields-from-model:start
      // anchor:value-fields-from-model:end

      // anchor:dataRef-fields-from-model:start
      profile: screenProfileFindByScreenEq_ProfileEqViewModel.profile(),
      screen: screenProfileFindByScreenEq_ProfileEqViewModel.screen(),
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

  var screenProfileFindByScreenEq_ProfileEqMapper = {
    jsToFormModel: jsToFormModel,
    jsToViewModel: jsToViewModel,
    viewModelToJS: viewModelToJS
  };

  // anchor:custom-javascript-after:start
  // anchor:custom-javascript-after:end

  return screenProfileFindByScreenEq_ProfileEqMapper;
});
