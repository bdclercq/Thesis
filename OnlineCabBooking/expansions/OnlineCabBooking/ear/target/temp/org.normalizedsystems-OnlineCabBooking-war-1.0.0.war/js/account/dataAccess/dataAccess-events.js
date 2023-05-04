// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var Triggers = require("nsx/triggers");
  /** @type {Object<string,any>} */
  var exports = {};

  /* ==UPDATES========================================== */
  //<editor-fold desc="Update events">
  exports.defineUpdateListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.update"})
  };

  exports.defineFinderUpdateTrigger = function () {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.update.finder",
      source: null
    })
  };
  exports.defineFinderUpdateListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.update.finder"})
  };

  exports.defineCreateUpdateTrigger = function () {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.update.create",
      source: null
    })
  };
  exports.defineCreateUpdateListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.update.create"})
  };

  exports.defineEditUpdateTrigger = function () {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.update.edit",
      source: null
    })
  };
  exports.defineEditUpdateListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.update.edit"})
  };

  exports.defineDeleteUpdateTrigger = function () {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.update.delete",
      source: null
    })
  };
  exports.defineDeleteUpdateListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.update.delete"})
  };

  exports.defineRefreshUpdateTrigger = function () {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.update.refresh",
      source: null
    })
  };
  exports.defineRefreshUpdateListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.update.refresh"})
  };
  // @anchor:update-events:start
  // @anchor:update-events:end
  //</editor-fold>
  // anchor:custom-update-events:start
  // anchor:custom-update-events:end

  /* ==INTENTS========================================== */
  //<editor-fold desc="Intent events">
  exports.defineCreateIntentListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.intent.create"})
  };
  exports.defineCreateIntentTrigger = function (source) {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.intent.create",
      source: source
    })
  };

  exports.defineEditIntentListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.intent.edit"})
  };
  exports.defineEditIntentTrigger = function (source) {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.intent.edit",
      source: source
    })
  };

  exports.defineDeleteIntentListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.intent.delete"})
  };
  exports.defineDeleteIntentTrigger = function (source) {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.intent.delete",
      source: source
    })
  };

  exports.defineViewIntentListener = function () {
    return Triggers.defineEventListener({key: "account.dataAccess.intent.view"})
  };
  exports.defineViewIntentTrigger = function (source) {
    return Triggers.defineEventTrigger({
      key: "account.dataAccess.intent.view",
      source: source
    })
  };
  // @anchor:intent-events:start
  // @anchor:intent-events:end
  //</editor-fold>
  // anchor:custom-intent-events:start
  // anchor:custom-intent-events:end

  return exports;
});
