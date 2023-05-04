// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Trigger = require('nsx/triggers');

  // anchor:require:start
  var CustomerEvents = require('onlineCabBookingComp/customer/customer-events');
  var DriverEvents = require('onlineCabBookingComp/driver/driver-events');
  var AddressEvents = require('onlineCabBookingComp/address/address-events');
  var PaymentEvents = require('onlineCabBookingComp/payment/payment-events');
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
        customer: defineLink(CustomerEvents),
        driver: defineLink(DriverEvents),
        fromLocation: defineLink(AddressEvents),
        toLocation: defineLink(AddressEvents),
        fromDateTime: ko.observable(),
        toDateTime: ko.observable(),
        km: ko.observable(),
        totalAmount: ko.observable(),
        payment: defineLink(PaymentEvents)
        // anchor:init-viewModel:end
        // @anchor:init-viewModel:start
        // @anchor:init-viewModel:end
        // anchor:custom-init-viewModel:start
        // anchor:custom-init-viewModel:end
      };
    }

    function updateViewmodel(tripBookingDetails) {
      if (!!tripBookingDetails) {
        viewmodel.id(tripBookingDetails.id);
        // anchor:update-viewModel:start
        viewmodel.customer.value(tripBookingDetails.customer);
        viewmodel.driver.value(tripBookingDetails.driver);
        viewmodel.fromLocation.value(tripBookingDetails.fromLocation);
        viewmodel.toLocation.value(tripBookingDetails.toLocation);
        viewmodel.fromDateTime(tripBookingDetails.fromDateTime);
        viewmodel.toDateTime(tripBookingDetails.toDateTime);
        viewmodel.km(tripBookingDetails.km);
        viewmodel.totalAmount(tripBookingDetails.totalAmount);
        viewmodel.payment.value(tripBookingDetails.payment);
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
