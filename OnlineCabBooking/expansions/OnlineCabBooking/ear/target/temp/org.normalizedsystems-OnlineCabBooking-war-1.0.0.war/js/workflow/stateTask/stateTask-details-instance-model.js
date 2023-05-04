// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Trigger = require('nsx/triggers');

  // anchor:require:start
  var WorkflowEvents = require('workflow/workflow/workflow-events');
  // anchor:require:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineInstanceView(input, options) {
    var details = input.details;

    var viewmodel = initViewmodel();

    function defineLink(events) {
      var value = ko.observable();
      var id = ko.pureComputed(function () { return !!value() ? value().id : 0 });
      var name = ko.pureComputed(function () { return !!value() ? value().name : "" });
      var link = events.defineViewIntentTrigger({
        selection: value
      });
      return {
        value: value,
        id: id,
        name: name,
        link: link
      }
    }

    function defineValueTypeField() {
      var value = ko.observable();
      return {
        value: ko.observable()
      }
    }

    function initViewmodel() {
      return {
        id: ko.observable(),
        // anchor:init-viewModel:start
        name: ko.observable(),
        processor: ko.observable(),
        implementation: ko.observable(),
        params: ko.observable(),
        beginState: ko.observable(),
        interimState: ko.observable(),
        failedState: ko.observable(),
        endState: ko.observable(),
        workflow: defineLink(WorkflowEvents),
        maxConcurrentTasks: ko.observable(),
        timeout: ko.observable()
        // anchor:init-viewModel:end
        // @anchor:init-viewModel:start
        // @anchor:init-viewModel:end
        // anchor:custom-init-viewModel:start
        // anchor:custom-init-viewModel:end
      };
    }

    function updateViewmodel(stateTaskDetails) {
      if (!!stateTaskDetails) {
        viewmodel.id(stateTaskDetails.id);
        // anchor:update-viewModel:start
        viewmodel.name(stateTaskDetails.name);
        viewmodel.processor(stateTaskDetails.processor);
        viewmodel.implementation(stateTaskDetails.implementation);
        viewmodel.params(stateTaskDetails.params);
        viewmodel.beginState(stateTaskDetails.beginState);
        viewmodel.interimState(stateTaskDetails.interimState);
        viewmodel.failedState(stateTaskDetails.failedState);
        viewmodel.endState(stateTaskDetails.endState);
        viewmodel.workflow.value(stateTaskDetails.workflow);
        viewmodel.maxConcurrentTasks(stateTaskDetails.maxConcurrentTasks);
        viewmodel.timeout(stateTaskDetails.timeout);
        // anchor:update-viewModel:end
        // @anchor:update-viewModel:start
        // @anchor:update-viewModel:end
        // anchor:custom-update-viewModel:start
        // anchor:custom-update-viewModel:end
      }
    }

    if (!!details.subscribe) {
      details.subscribe(updateViewmodel);
    } else {
      updateViewmodel(details);
    }

    // @anchor:define-after:start
    // @anchor:define-after:end
    // anchor:custom-define-after:start
    // anchor:custom-define-after:end

    return viewmodel;
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineInstanceView = defineInstanceView;
  return exports;
});
