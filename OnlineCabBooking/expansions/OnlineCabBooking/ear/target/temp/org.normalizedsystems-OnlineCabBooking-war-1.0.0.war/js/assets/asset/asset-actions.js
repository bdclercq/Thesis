define(function (require) {
  // expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
  "use strict";

  const Trigger = require('nsx/triggers');
  const ErrorTrigger = require('nsx/error/error-trigger');
  const nsxActions = require("nsx/nsx-actions");
  const nsxRequest = require("nsx/nsx-request");

  const AssetEvents = require('assets/asset/asset-events');
  const AssetElement = require('nsx/nsx-application').getElement('assets', 'asset');

  // @anchor:imports:start
  // @anchor:imports:end
  // anchor:custom-imports:start
  // anchor:custom-imports:end

  //region Support functions
  // anchor:support-functions:start
  /**
   * @param {{
   *   errorNotifications: ErrorSender,
   *   updateEvent: { trigger },
   *   submit: function(input: I, callback: (function(any))
   * }} params
   * @template I
   * @return {AssetAction<I>}
   */
  function newAction(params) {
    const errorNotifications = params.errorNotifications;
    const updateEvent = params.updateEvent;
    const submit = params.submit;

    const successEvent = Trigger.defineTrigger();
    const errorEvent = Trigger.defineTrigger();
    const completeEvent = Trigger.defineTrigger();

    let lastResult = {};

    function perform(input) {
      submit(input, handleResult)
    }

    function handleResult(result) {
      lastResult = result;

      if (result.success) {
        successEvent.trigger();
        updateEvent.trigger();
      } else {
        errorEvent.trigger()
        errorNotifications.errors(result.errorMessages);
      }
    }

    return {
      completeEvent: completeEvent,
      successEvent: successEvent,
      errorEvent: errorEvent,
      perform: perform,
      getResult: function () {
        return lastResult;
      }
    }
  }
  // anchor:support-functions:end
  // @anchor:support-functions:start
  // @anchor:support-functions:end
  // anchor:custom-support-functions:start
  // anchor:custom-support-functions:end
  //endregion

  // anchor:actions:start
  function createAction() {
    return newAction({
      errorNotifications: ErrorTrigger.defineTrigger({subject: "assets.asset.create"}),
      updateEvent: AssetEvents.defineCreateUpdateTrigger(),
      submit: (formData, callback) => {
        formData.id = 0;
        var requestOptions = {
          url: nsxActions.getElementAction(AssetElement, "saveDetails-json"),
          data: JSON.stringify(formData),
          dataType: 'json',
          contentType: 'application/json; charset=UTF-8'
        };
        // @anchor:create-action:start
        // @anchor:create-action:end
        // anchor:custom-create-action:start
        // anchor:custom-create-action:end
        nsxRequest.postSave(requestOptions).then(callback);
      }
    });
  }

  function editAction() {
    return newAction({
      errorNotifications: ErrorTrigger.defineTrigger({subject: "assets.asset.edit"}),
      updateEvent: AssetEvents.defineEditUpdateTrigger(),
      submit: (formData, callback) => {
        var requestOptions = {
          url: nsxActions.getElementAction(AssetElement, "saveDetails-json"),
          data: JSON.stringify(formData),
          dataType: 'json',
          contentType: 'application/json; charset=UTF-8'
        };
        // @anchor:edit-action:start
        // @anchor:edit-action:end
        // anchor:custom-edit-action:start
        // anchor:custom-edit-action:end
        nsxRequest.postSave(requestOptions).then(callback);
      }
    });
  }

  function deleteAction() {
    return newAction({
      errorNotifications: ErrorTrigger.defineTrigger({subject: "assets.asset.delete"}),
      updateEvent: AssetEvents.defineDeleteUpdateTrigger(),
      submit: (target, callback) => {
        var requestOptions = {
          url: nsxActions.getElementAction(AssetElement, "delete-json"),
          data: JSON.stringify({
            "assetOid": target.id
          }),
          dataType: 'json',
          contentType: 'application/json; charset=UTF-8'
        };
        // @anchor:delete-action:start
        // @anchor:delete-action:end
        // anchor:custom-delete-action:start
        // anchor:custom-delete-action:end
        nsxRequest.postDelete(requestOptions).then(callback);
      }
    });
  }
  // anchor:actions:end
  // @anchor:actions:start
  // @anchor:actions:end
  // anchor:custom-actions:start
  // anchor:custom-actions:end

  const exports = {};
  // anchor:exports:start
  exports.createAction = createAction;
  exports.editAction = editAction;
  exports.deleteAction = deleteAction;
  // anchor:exports:end
  // @anchor:exports:start
  // @anchor:exports:end
  // anchor:custom-exports:start
  // anchor:custom-exports:end
  return exports;
});
