// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var EngineNodeServiceEvents = require('workflow/engineNodeService/engineNodeService-events');
  var EngineNodeServiceProjector = require('workflow/engineNodeService/engineNodeService-projector');
  var EngineNodeServiceForm = require('workflow/engineNodeService/engineNodeService-instance-form');
  var EngineNodeServiceActions = require('workflow/engineNodeService/engineNodeService-actions');
  var EngineNodeServiceElement = require('nsx/nsx-application').getElement('workflow', 'engineNodeService');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: EngineNodeServiceElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) EngineNodeService element!")
    });

    function render() {
      var editIntent = EngineNodeServiceEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "workflow/engineNodeService-form";
      var title = "Edit EngineNodeService";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: EngineNodeServiceActions.editAction()
      };

      var detailsProjection = EngineNodeServiceProjector.defineProjector(selection, {
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

      var form = EngineNodeServiceForm.defineForm({
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
      status: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().status)
          })
      },
      engineNode: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().engineNode)
          })
      },
      engineService: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().engineService)
          })
      },
      lastRunAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().lastRunAt)
          })
      },
      nextRun: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().nextRun)
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
