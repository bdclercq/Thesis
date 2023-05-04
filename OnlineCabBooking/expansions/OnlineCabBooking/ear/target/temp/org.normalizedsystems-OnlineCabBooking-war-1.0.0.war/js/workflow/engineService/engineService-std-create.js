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
  var EngineServiceForm = require('workflow/engineService/engineService-instance-form');
  var EngineServiceActions = require('workflow/engineService/engineService-actions');
  var EngineServiceElement = require('nsx/nsx-application').getElement('workflow', 'engineService');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: EngineServiceElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) EngineService element!")
    });

    function render() {
      var createIntent = EngineServiceEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "workflow/engineService-form";
      var title = "EngineService";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: EngineServiceActions.createAction()
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

      var form = EngineServiceForm.defineForm({
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
  exports.defineCreateDialog = defineCreateDialog;
  return exports;
});
