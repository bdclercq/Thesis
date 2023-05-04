// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var TripBookingEvents = require('onlineCabBookingComp/tripBooking/tripBooking-events');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()
  var TripBookingElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'tripBooking');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineViewDialog() {
    var loaded = Trigger.defineContinuingTrigger();

    var viewRightsRequest = accessRightsController.requestRight({
      element: TripBookingElement,
      requestedRight: AccessRights.Right.VIEW
    })

    when(viewRightsRequest.granted).thenDo(render);
    when(viewRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to view a(n) TripBooking!")
    });

    function render() {
      var selection = ko.observable();
      var viewIntent = TripBookingEvents.defineViewIntentListener();

      var config = {
        title: 'TripBooking',
        projection: 'details',
        projector: 'onlineCabBookingComp/tripBooking/tripBooking-projector',
        modeller: 'onlineCabBookingComp/tripBooking/tripBooking-details-instance-model',
        view: 'onlineCabBookingComp/tripBooking-details-instance'
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
