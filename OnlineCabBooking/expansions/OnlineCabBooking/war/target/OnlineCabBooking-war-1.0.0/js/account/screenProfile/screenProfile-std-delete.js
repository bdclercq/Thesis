// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require('nsx/triggers');
  var translate = require('nsx/util/text-utils').translate;
  var format = require('nsx/util/text-utils').format;
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var ScreenProfileEvents = require('account/screenProfile/screenProfile-events');
  var ScreenProfileActions = require('account/screenProfile/screenProfile-actions');
  var ScreenProfileElement = require('nsx/nsx-application').getElement('account', 'screenProfile');

  var title = translate("ns.toDelete");

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineDeleteDialog() {
    var loaded = Trigger.defineContinuingTrigger();

    var deleteRightsRequest = accessRightsController.requestRight({
      element: ScreenProfileElement,
      requestedRight: AccessRights.Right.DELETE
    });

    when(deleteRightsRequest.granted).thenDo(render);
    when(deleteRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to delete a(n) ScreenProfile element!")
    });

    function render() {
      var selection = { id: 0 };
      var deleteIntent = ScreenProfileEvents.defineDeleteIntentListener();
      var closeDialog = Trigger.defineTrigger();

      when(deleteIntent).thenDo(function(params) {
        selection = params.selection;
      });

      var confirmDelete = defineConfirmDialog(deleteIntent, closeDialog);
      var deleteAction = ScreenProfileActions.deleteAction();

      // @anchor:delete-action:start
      // @anchor:delete-action:end
      // anchor:custom-delete-action:start
      // anchor:custom-delete-action:end

      when(confirmDelete).thenDo(() => deleteAction.perform(selection));
      when(deleteAction.successEvent).thenTrigger(closeDialog);
      loaded.trigger();
    }

    return {
      loaded: loaded
    }
  }

  function defineConfirmDialog(deleteIntent, closeDialog) {
    var confirmDelete = Trigger.defineTrigger();
    Popup.definePopup({
      title: title,
      view: "nsx/knockout/simpleText",
      viewModel: {
        text: format("{question}?", { question: translate("ns.areYouSureToDeleteFollowingObject")})
      },
      trigger: deleteIntent
    }, {
      onConfirm: confirmDelete,
      closeOn: closeDialog
    });
    return confirmDelete;
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineDeleteDialog = defineDeleteDialog;
  return exports;
});