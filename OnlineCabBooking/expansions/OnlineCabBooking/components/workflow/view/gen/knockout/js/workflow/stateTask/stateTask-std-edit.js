// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var StateTaskEvents = require('workflow/stateTask/stateTask-events');
  var StateTaskProjector = require('workflow/stateTask/stateTask-projector');
  var StateTaskForm = require('workflow/stateTask/stateTask-instance-form');
  var StateTaskActions = require('workflow/stateTask/stateTask-actions');
  var StateTaskElement = require('nsx/nsx-application').getElement('workflow', 'stateTask');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: StateTaskElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) StateTask element!")
    });

    function render() {
      var editIntent = StateTaskEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "workflow/stateTask-form";
      var title = "Edit StateTask";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: StateTaskActions.editAction()
      };

      var detailsProjection = StateTaskProjector.defineProjector(selection, {
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

      var form = StateTaskForm.defineForm({
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
      processor: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().processor)
          })
      },
      implementation: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().implementation)
          })
      },
      params: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().params)
          })
      },
      beginState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().beginState)
          })
      },
      interimState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().interimState)
          })
      },
      failedState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().failedState)
          })
      },
      endState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().endState)
          })
      },
      workflow: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().workflow)
          })
      },
      maxConcurrentTasks: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().maxConcurrentTasks)
          })
      },
      timeout: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().timeout)
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
