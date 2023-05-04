// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var ThumbnailEvents = require('utils/thumbnail/thumbnail-events');
  var ThumbnailProjector = require('utils/thumbnail/thumbnail-projector');
  var ThumbnailForm = require('utils/thumbnail/thumbnail-instance-form');
  var ThumbnailActions = require('utils/thumbnail/thumbnail-actions');
  var ThumbnailElement = require('nsx/nsx-application').getElement('utils', 'thumbnail');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: ThumbnailElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) Thumbnail element!")
    });

    function render() {
      var editIntent = ThumbnailEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "utils/thumbnail-form";
      var title = "Edit Thumbnail";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: ThumbnailActions.editAction()
      };

      var detailsProjection = ThumbnailProjector.defineProjector(selection, {
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

      var form = ThumbnailForm.defineForm({
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
      fullName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().fullName)
          })
      },
      uri: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().uri)
          })
      },
      depth: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().depth)
          })
      },
      border: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().border)
          })
      },
      thumbType: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().thumbType)
          })
      },
      thumbName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().thumbName)
          })
      },
      targetType: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().targetType)
          })
      },
      targetName: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().targetName)
          })
      },
      targetId: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().targetId)
          })
      },
      leftX: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().leftX)
          })
      },
      topY: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().topY)
          })
      },
      width: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().width)
          })
      },
      height: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().height)
          })
      },
      clickAction: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().clickAction)
          })
      },
      hooverAction: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().hooverAction)
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
