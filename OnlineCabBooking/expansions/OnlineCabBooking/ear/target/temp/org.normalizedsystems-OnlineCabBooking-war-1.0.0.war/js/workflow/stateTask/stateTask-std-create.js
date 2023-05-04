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
  var StateTaskForm = require('workflow/stateTask/stateTask-instance-form');
  var StateTaskActions = require('workflow/stateTask/stateTask-actions');
  var StateTaskElement = require('nsx/nsx-application').getElement('workflow', 'stateTask');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: StateTaskElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) StateTask element!")
    });

    function render() {
      var createIntent = StateTaskEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "workflow/stateTask-form";
      var title = "StateTask";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: StateTaskActions.createAction()
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

      var form = StateTaskForm.defineForm({
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
  exports.defineCreateDialog = defineCreateDialog;
  return exports;
});
