// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Trigger = require('nsx/triggers');

  // anchor:require:start
  var CabEvents = require('onlineCabBookingComp/cab/cab-events');
  var TripBookingEvents = require('onlineCabBookingComp/tripBooking/tripBooking-events');
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
        licenseNo: ko.observable(),
        rating: ko.observable(),
        isAvailable: ko.observable(),
        cab: defineLink(CabEvents),
        tripBooking: defineLink(TripBookingEvents)
        // anchor:init-viewModel:end
        // @anchor:init-viewModel:start
        // @anchor:init-viewModel:end
        // anchor:custom-init-viewModel:start
        // anchor:custom-init-viewModel:end
      };
    }

    function updateViewmodel(driverDetails) {
      if (!!driverDetails) {
        viewmodel.id(driverDetails.id);
        // anchor:update-viewModel:start
        viewmodel.name(driverDetails.name);
        viewmodel.licenseNo(driverDetails.licenseNo);
        viewmodel.rating(driverDetails.rating);
        viewmodel.isAvailable(driverDetails.isAvailable);
        viewmodel.cab.value(driverDetails.cab);
        viewmodel.tripBooking.value(driverDetails.tripBooking);
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
