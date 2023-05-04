define(function (require) {
  // expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
  "use strict";

  const Trigger = require('nsx/triggers');
  const ErrorTrigger = require('nsx/error/error-trigger');
  const nsxActions = require("nsx/nsx-actions");
  const nsxRequest = require("nsx/nsx-request");

  const ProfileEvents = require('account/profile/profile-events');
  const ProfileElement = require('nsx/nsx-application').getElement('account', 'profile');

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
   * @return {ProfileAction<I>}
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
      errorNotifications: ErrorTrigger.defineTrigger({subject: "account.profile.create"}),
      updateEvent: ProfileEvents.defineCreateUpdateTrigger(),
      submit: (formData, callback) => {
        formData.id = 0;
        var requestOptions = {
          url: nsxActions.getElementAction(ProfileElement, "saveDetails-json"),
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
      errorNotifications: ErrorTrigger.defineTrigger({subject: "account.profile.edit"}),
      updateEvent: ProfileEvents.defineEditUpdateTrigger(),
      submit: (formData, callback) => {
        var requestOptions = {
          url: nsxActions.getElementAction(ProfileElement, "saveDetails-json"),
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
      errorNotifications: ErrorTrigger.defineTrigger({subject: "account.profile.delete"}),
      updateEvent: ProfileEvents.defineDeleteUpdateTrigger(),
      submit: (target, callback) => {
        var requestOptions = {
          url: nsxActions.getElementAction(ProfileElement, "delete-json"),
          data: JSON.stringify({
            "profileOid": target.id
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
