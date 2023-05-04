// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var ScreenProfileEvents = require('account/screenProfile/screenProfile-events');
  var ScreenProfileProjector = require('account/screenProfile/screenProfile-projector');
  var ScreenProfileForm = require('account/screenProfile/screenProfile-instance-form');
  var ScreenProfileActions = require('account/screenProfile/screenProfile-actions');
  var ScreenProfileElement = require('nsx/nsx-application').getElement('account', 'screenProfile');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: ScreenProfileElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) ScreenProfile element!")
    });

    function render() {
      var editIntent = ScreenProfileEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "account/screenProfile-form";
      var title = "Edit ScreenProfile";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: ScreenProfileActions.editAction()
      };

      var detailsProjection = ScreenProfileProjector.defineProjector(selection, {
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

      var form = ScreenProfileForm.defineForm({
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
      screen: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().screen)
          })
      },
      profile: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().profile)
          })
      },
      screens: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().screens)
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
