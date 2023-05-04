// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var TimeTaskEvents = require('workflow/timeTask/timeTask-events');
  var TimeTaskProjector = require('workflow/timeTask/timeTask-projector');
  var TimeTaskForm = require('workflow/timeTask/timeTask-instance-form');
  var TimeTaskActions = require('workflow/timeTask/timeTask-actions');
  var TimeTaskElement = require('nsx/nsx-application').getElement('workflow', 'timeTask');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: TimeTaskElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) TimeTask element!")
    });

    function render() {
      var editIntent = TimeTaskEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "workflow/timeTask-form";
      var title = "Edit TimeTask";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: TimeTaskActions.editAction()
      };

      var detailsProjection = TimeTaskProjector.defineProjector(selection, {
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

      var form = TimeTaskForm.defineForm({
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
      triggerState: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().triggerState)
          })
      },
      intervalPeriod: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().intervalPeriod)
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
  exports.defineEditDialog = defineEditDialog;
  return exports;
});
