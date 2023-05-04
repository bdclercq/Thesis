// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
/**
 * Creates a projector that maps a dataref to its projection.
 * Changes in the dataref-observable will cause the projection to be updated.
 */
define(function(require) {
  var ko = require('knockout');
  var paymentMetaData = require("dataElement!onlineCabBookingComp.payment");
  var paymentAgent = paymentMetaData.getAgent();
  var Trigger = require('nsx/triggers');
  var when = Trigger.when;
  var PaymentEvents = require('onlineCabBookingComp/payment/payment-events');

  var translate = require('nsx/util/text-utils').translate;
  var errorTrigger = require("nsx/error/error-trigger").defineTrigger({
    subject: "onlineCabBookingComp.payment.projection"
  });

  function defineProjector(dataRefObservable, options) {
    var projection = ko.observable({});
    var dataCreate = PaymentEvents.defineCreateUpdateListener();
    var dataEdit = PaymentEvents.defineEditUpdateListener();
    var dataRefresh = PaymentEvents.defineRefreshUpdateListener();
    var dataDelete = PaymentEvents.defineDeleteUpdateListener();
    var dataFinder = PaymentEvents.defineFinderUpdateListener();

    function updateProjection() {
      var dataRef = dataRefObservable();
      if (!dataRef || dataRef.id === 0) {
        projection({});
      } else {
        paymentAgent
          .getProjection(dataRef.id, options.projection)
          .done(function (jsonResult) {
            if (jsonResult.success) {
              projection(jsonResult.value);
            } else {
              errorTrigger.warn(translate("cruds.findFailed"))
            }
          });
      }
    }

    when(dataRefObservable)
      .or(dataCreate)
      .or(dataEdit)
      .or(dataRefresh)
      .or(dataFinder)
      .thenDo(updateProjection);
    when(dataDelete).thenDo(() => projection({}));
    updateProjection();

    return {
      projection: projection
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineProjector = defineProjector;
  return exports;
});
