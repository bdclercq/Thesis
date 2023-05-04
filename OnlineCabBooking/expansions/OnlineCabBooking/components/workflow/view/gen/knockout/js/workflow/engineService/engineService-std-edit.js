// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var EngineServiceEvents = require('workflow/engineService/engineService-events');
  var EngineServiceProjector = require('workflow/engineService/engineService-projector');
  var EngineServiceForm = require('workflow/engineService/engineService-instance-form');
  var EngineServiceActions = require('workflow/engineService/engineService-actions');
  var EngineServiceElement = require('nsx/nsx-application').getElement('workflow', 'engineService');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) EngineService element!")
    });

    function render() {
      var editIntent = EngineServiceEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "workflow/engineService-form";
      var title = "Edit EngineService";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: EngineServiceActions.editAction()
      };

      var detailsProjection = EngineServiceProjector.defineProjector(selection, {
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

      var form = EngineServiceForm.defineForm({
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
      changed: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().changed)
          })
      },
      busy: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().busy)
          })
      },
      waitTime: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().waitTime)
          })
      },
      collector: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().collector)
          })
      },
      workflow: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().workflow)
          })
      },
      timeWindowGroup: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().timeWindowGroup)
          })
      },
      lastRunAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().lastRunAt)
          })
      },
      batchSize: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().batchSize)
          })
      },
      maximumNumberOfNodes: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().maximumNumberOfNodes)
          })
      },
      engineNodeServices: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().engineNodeServices)
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
