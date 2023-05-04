// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var ParamTargetValueEvents = require('utils/paramTargetValue/paramTargetValue-events');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()
  var ParamTargetValueElement = require('nsx/nsx-application').getElement('utils', 'paramTargetValue');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineViewDialog() {
    var loaded = Trigger.defineContinuingTrigger();

    var viewRightsRequest = accessRightsController.requestRight({
      element: ParamTargetValueElement,
      requestedRight: AccessRights.Right.VIEW
    })

    when(viewRightsRequest.granted).thenDo(render);
    when(viewRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to view a(n) ParamTargetValue!")
    });

    function render() {
      var selection = ko.observable();
      var viewIntent = ParamTargetValueEvents.defineViewIntentListener();

      var config = {
        title: 'ParamTargetValue',
        projection: 'details',
        projector: 'utils/paramTargetValue/paramTargetValue-projector',
        modeller: 'utils/paramTargetValue/paramTargetValue-details-instance-model',
        view: 'utils/paramTargetValue-details-instance'
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
