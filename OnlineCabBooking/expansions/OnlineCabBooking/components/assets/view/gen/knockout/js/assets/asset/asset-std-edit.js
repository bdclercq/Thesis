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
  var AssetProjector = require('assets/asset/asset-projector');
  var AssetForm = require('assets/asset/asset-instance-form');
  var AssetActions = require('assets/asset/asset-actions');
  var AssetElement = require('nsx/nsx-application').getElement('assets', 'asset');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: AssetElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) Asset element!")
    });

    function render() {
      var editIntent = AssetEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "assets/asset-form";
      var title = "Edit Asset";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: AssetActions.editAction()
      };

      var detailsProjection = AssetProjector.defineProjector(selection, {
        projection: "details"
      });

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      // anchor:custom-options:end

      when(editIntent).thenDo(function(params) {
        selection(params.selection);
        constants(params.constants);
      });
      when(editIntent)
        .and(detailsProjection.projection)
        .thenTrigger(showDialog);

      var form = AssetForm.defineForm({
        data: detailsProjection.projection,
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
        trigger: showDialog
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

  /**
   * @param {KnockoutObservable<any>} constants
   * @returns {Object<string, NsxFieldLayoutConfig>}
   */
  function initLayoutConfig(constants) {
    return {
      // anchor:field-layout-config:start
      id: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().id)
          })
      },
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
  exports.defineEditDialog = defineEditDialog;
  return exports;
});
