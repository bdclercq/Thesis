// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  const ko = require('knockout');
  const Popup = require('nsx/injectors/popup');
  const Trigger = require('nsx/triggers');
  const translate = require('nsx/util/text-utils').translate;
  const format = require('nsx/util/text-utils').format;
  const when = Trigger.when;

  const AccessRights = require('nsx/config/access-rights');
  const accessRightsController = AccessRights.getAccessRightsController()

  const StateTimerEvents = require('workflow/stateTimer/stateTimer-events');
  const StateTimerActions = require('workflow/stateTimer/stateTimer-actions');
  const StateTimerElement = require('nsx/nsx-application').getElement('workflow', 'stateTimer');

  let title = translate("ns.toDelete");

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineDeleteDialog() {
    const loaded = Trigger.defineContinuingTrigger();

    const deleteRightsRequest = accessRightsController.requestRight({
      element: StateTimerElement,
      requestedRight: AccessRights.Right.DELETE
    });

    when(deleteRightsRequest.granted).thenDo(render);

    function render() {
      const selection = ko.observable([]);
      const deleteIntent = StateTimerEvents.defineDeleteMultipleIntentListener();
      const closeDialog = Trigger.defineTrigger();

      when(deleteIntent).thenDo(function(params) {
        selection(params.selectedItems);
      });

      let confirmDelete = defineConfirmDialog(deleteIntent, closeDialog, selection);
      let deleteAction = StateTimerActions.deleteMultipleAction();

      // @anchor:delete-action:start
      // @anchor:delete-action:end
      // anchor:custom-delete-action:start
      // anchor:custom-delete-action:end

      when(confirmDelete).thenDo(() => deleteAction.perform(selection()));
      when(deleteAction.successEvent).thenTrigger(closeDialog);
      loaded.trigger();
    }

    return {
      loaded: loaded
    }
  }

  function defineConfirmDialog(deleteIntent, closeDialog, selection) {
    const confirmDelete = Trigger.defineTrigger();
    Popup.definePopup({
      title: title,
      view: "nsx/knockout/simpleText",
      viewModel: {
        text: format(translate("ns.areYouSureToDeleteMultipleItems"), {
          count: ko.pureComputed(function() { return selection().length; })
        })
      },
      trigger: deleteIntent
    }, {
      onConfirm: confirmDelete,
      closeOn: closeDialog
    });
    return confirmDelete;
  }

  /** @type {Object<string,any>} */
  let exports = {};
  exports.defineDeleteDialog = defineDeleteDialog;
  return exports;
});