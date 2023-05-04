// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var DataAccessEvents = require('account/dataAccess/dataAccess-events');
  var DataAccessProjector = require('account/dataAccess/dataAccess-projector');
  var DataAccessForm = require('account/dataAccess/dataAccess-instance-form');
  var DataAccessActions = require('account/dataAccess/dataAccess-actions');
  var DataAccessElement = require('nsx/nsx-application').getElement('account', 'dataAccess');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineEditDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var editRightsRequest = accessRightsController.requestRight({
      element: DataAccessElement,
      requestedRight: AccessRights.Right.EDIT
    })

    when(editRightsRequest.granted).thenDo(render);
    when(editRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to edit a(n) DataAccess element!")
    });

    function render() {
      var editIntent = DataAccessEvents.defineEditIntentListener();
      var showDialog = Trigger.defineTrigger();

      var constants = ko.observable();
      var selection = ko.observable();
      var formView = "account/dataAccess-form";
      var title = "Edit DataAccess";
      var layoutConfig = initLayoutConfig(constants)
      var formOptions = {
        resetOn: editIntent,
        action: DataAccessActions.editAction()
      };

      var detailsProjection = DataAccessProjector.defineProjector(selection, {
        projection: "details"
      });

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      // anchor:custom-options:end

      when(editIntent).thenDo(function(params) {
        selection(params.selection);
        constants(params.constants);
      });
      when(editIntent)
        .and(detailsProjection.projection)
        .thenTrigger(showDialog);

      var form = DataAccessForm.defineForm({
        data: detailsProjection.projection,
        layoutConfig: layoutConfig
      }, formOptions);

      // @anchor:form:start
      // @anchor:form:end
      // anchor:custom-form:start
      // anchor:custom-form:end

      Popup.definePopup({
        view: formView,
        viewModel: form,
        title: title,
        trigger: showDialog
      }, {
        onConfirm: form.submit,
        closeOn: form.success
      });
      loaded.trigger();
    }

    return {
      loaded: loaded
    }
  }

  /**
   * @param {KnockoutObservable<any>} constants
   * @returns {Object<string, NsxFieldLayoutConfig>}
   */
  function initLayoutConfig(constants) {
    return {
      // anchor:field-layout-config:start
      id: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().id)
          })
      },
      name: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().name)
          })
      },
      forProfile: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().forProfile)
          })
      },
      forUser: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().forUser)
          })
      },
      userGroups: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().userGroups)
          })
      },
      element: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().element)
          })
      },
      target: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().target)
          })
      },
      functionality: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().functionality)
          })
      },
      authorized: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().authorized)
          })
      },
      lastModifiedAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().lastModifiedAt)
          })
      },
      enteredAt: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().enteredAt)
          })
      },
      disabled: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().disabled)
          })
      },
      forUserGroup: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().forUserGroup)
          })
      }
      // anchor:field-layout-config:end
    }
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineEditDialog = defineEditDialog;
  return exports;
});
