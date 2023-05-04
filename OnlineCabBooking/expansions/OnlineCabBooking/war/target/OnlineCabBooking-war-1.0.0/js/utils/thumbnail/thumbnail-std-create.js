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
  var ThumbnailForm = require('utils/thumbnail/thumbnail-instance-form');
  var ThumbnailActions = require('utils/thumbnail/thumbnail-actions');
  var ThumbnailElement = require('nsx/nsx-application').getElement('utils', 'thumbnail');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: ThumbnailElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) Thumbnail element!")
    });

    function render() {
      var createIntent = ThumbnailEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "utils/thumbnail-form";
      var title = "Thumbnail";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: ThumbnailActions.createAction()
      };

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      // anchor:custom-options:end

      when(createIntent).thenDo(function(params) {
        constants(params.constants);
        values(params.values);
      });

      var formData = ko.pureComputed(function() {
        return utils.combine(defaultValues, constants(), values());
      });

      var form = ThumbnailForm.defineForm({
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
  exports.defineCreateDialog = defineCreateDialog;
  return exports;
});
