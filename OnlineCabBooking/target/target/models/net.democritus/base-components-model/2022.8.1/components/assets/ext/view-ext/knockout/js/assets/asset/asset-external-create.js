// custom file
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var AssetForm = require('assets/asset/asset-registerExternalAsset-form');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController();
  var AssetElement = require('nsx/nsx-application').getElement('assets', 'asset');


  function defineCreateDialog(options) {
    options = options || {};
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: AssetElement,
      requestedRight: AccessRights.Right.CREATE
    });

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) Asset element!")
    });

    function render() {
      var constants = ko.observable({});
      var values = ko.observable({});

      var formView = "assets/asset-registerExternalAsset-form";
      var title = "Register external asset";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };

      var createIntent = Trigger.defineEventListener({key: "assets.asset.intent.createExternalAsset"});
      when(createIntent).thenDo(function(params) {
        constants(params.constants || {});
        values(params.values || {});
      });

      var formData = ko.pureComputed(function() {
        return utils.combine(defaultValues, constants(), values());
      });

      var form = AssetForm.defineCommandForm({
        data: formData,
        layoutConfig: layoutConfig
      }, {
        resetOn: createIntent
      });

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
      uri: {
        disabled: ko.pureComputed(function () {
          return utils.isDefined(constants().uri)
        })
      }
    }
  }

  return {
    defineCreateDialog: defineCreateDialog
  }
});
