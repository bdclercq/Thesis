// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var AssetEvents = require('assets/asset/asset-events');
  var AssetForm = require('assets/asset/asset-instance-form');
  var AssetActions = require('assets/asset/asset-actions');
  var AssetElement = require('nsx/nsx-application').getElement('assets', 'asset');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  AssetForm = require("assets/asset/asset-upload-form");
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: AssetElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) Asset element!")
    });

    function render() {
      var createIntent = AssetEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "assets/asset-form";
      var title = "Asset";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: AssetActions.createAction()
      };

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      formView = "assets/asset-upload-form";
      // anchor:custom-options:end

      when(createIntent).thenDo(function(params) {
        constants(params.constants);
        values(params.values);
      });

      var formData = ko.pureComputed(function() {
        return utils.combine(defaultValues, constants(), values());
      });

      var form = AssetForm.defineForm({
        data: formData,
        layoutConfig: layoutConfig
      }, formOptions);

      // @anchor:form:start
      // @anchor:form:end
      // anchor:custom-form:start
      // anchor:custom-form:end

      Popup.definePopup({
        view: formView,
        viewModel: form,
        title: title,
        trigger: createIntent
      }, {
        onConfirm: form.submit,
        closeOn: form.success
      });
      loaded.trigger();
    }

    return {
      loaded: loaded
    }
  }

  function initLayoutConfig(constants) {
    return {
      // anchor:field-layout-config:start
      name: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().name)
          })
      },
      type: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().type)
          })
      },
      fileAsset: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fileAsset)
          })
      },
      internalAsset: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().internalAsset)
          })
      },
      downloadLink: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().downloadLink)
          })
      },
      contentType: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().contentType)
          })
      },
      byteSize: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().byteSize)
          })
      },
      fileSize: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fileSize)
          })
      },
      fileId: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fileId)
          })
      },
      complete: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().complete)
          })
      },
      externalAsset: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().externalAsset)
          })
      }
      // anchor:field-layout-config:end
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineCreateDialog = defineCreateDialog;
  return exports;
});
