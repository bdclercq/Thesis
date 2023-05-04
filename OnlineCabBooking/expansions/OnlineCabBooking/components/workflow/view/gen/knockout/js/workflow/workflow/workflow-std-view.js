// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var WorkflowEvents = require('workflow/workflow/workflow-events');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()
  var WorkflowElement = require('nsx/nsx-application').getElement('workflow', 'workflow');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineViewDialog() {
    var loaded = Trigger.defineContinuingTrigger();

    var viewRightsRequest = accessRightsController.requestRight({
      element: WorkflowElement,
      requestedRight: AccessRights.Right.VIEW
    })

    when(viewRightsRequest.granted).thenDo(render);
    when(viewRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to view a(n) Workflow!")
    });

    function render() {
      var selection = ko.observable();
      var viewIntent = WorkflowEvents.defineViewIntentListener();

      var config = {
        title: 'Workflow',
        projection: 'details',
        projector: 'workflow/workflow/workflow-projector',
        modeller: 'workflow/workflow/workflow-details-instance-model',
        view: 'workflow/workflow-details-instance'
      };

      // @anchor:options:start
      // @anchor:options:end
      //anchor:custom-options:start
      //anchor:custom-options:end

      when(viewIntent).thenDo(function(params) {
        selection(params.selection);
      });

      require([config.projector, config.modeller], function (Projection, Model) {
        var proj = Projection.defineProjector(selection, {
          projection: config.projection
        });
        var model = Model.defineInstanceView({
          details: proj.projection
        });

        Popup.definePopup({
          title: config.title,
          view: config.view,
          viewModel: model,
          trigger: viewIntent
        });
        loaded.trigger();
      });
    }

    return {
      loaded: loaded
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineViewDialog = defineViewDialog;
  return exports;
});
