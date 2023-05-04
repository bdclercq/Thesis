// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var StateTimerEvents = require('workflow/stateTimer/stateTimer-events');
  var StateTimerForm = require('workflow/stateTimer/stateTimer-instance-form');
  var StateTimerActions = require('workflow/stateTimer/stateTimer-actions');
  var StateTimerElement = require('nsx/nsx-application').getElement('workflow', 'stateTimer');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: StateTimerElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) StateTimer element!")
    });

    function render() {
      var createIntent = StateTimerEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "workflow/stateTimer-form";
      var title = "StateTimer";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: StateTimerActions.createAction()
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

      var form = StateTimerForm.defineForm({
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
      targetState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().targetState)
          })
      },
      alteredState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().alteredState)
          })
      },
      allowedPeriod: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().allowedPeriod)
          })
      },
      requiredAction: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().requiredAction)
          })
      },
      workflow: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().workflow)
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
