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
  var DataAccessForm = require('account/dataAccess/dataAccess-instance-form');
  var DataAccessActions = require('account/dataAccess/dataAccess-actions');
  var DataAccessElement = require('nsx/nsx-application').getElement('account', 'dataAccess');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: DataAccessElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) DataAccess element!")
    });

    function render() {
      var createIntent = DataAccessEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "account/dataAccess-form";
      var title = "DataAccess";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: DataAccessActions.createAction()
      };

      // @anchor:options:start
      // @anchor:options:end
      // anchor:custom-options:start
      // anchor:custom-options:end

      when(createIntent).thenDo(function(params) {
        constants(params.constants);
        values(params.values);
      });

      var formData = ko.pureComputed(function() {
        return utils.combine(defaultValues, constants(), values());
      });

      var form = DataAccessForm.defineForm({
        data: formData,
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
        trigger: createIntent
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

  function initLayoutConfig(constants) {
    return {
      // anchor:field-layout-config:start
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
  exports.defineCreateDialog = defineCreateDialog;
  return exports;
});
