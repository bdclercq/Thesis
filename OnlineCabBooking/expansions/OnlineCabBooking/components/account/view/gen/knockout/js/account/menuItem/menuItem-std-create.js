// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var MenuItemEvents = require('account/menuItem/menuItem-events');
  var MenuItemForm = require('account/menuItem/menuItem-instance-form');
  var MenuItemActions = require('account/menuItem/menuItem-actions');
  var MenuItemElement = require('nsx/nsx-application').getElement('account', 'menuItem');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: MenuItemElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) MenuItem element!")
    });

    function render() {
      var createIntent = MenuItemEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "account/menuItem-form";
      var title = "MenuItem";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: MenuItemActions.createAction()
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

      var form = MenuItemForm.defineForm({
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
      menu: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().menu)
          })
      },
      screen: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().screen)
          })
      },
      menuItem: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().menuItem)
          })
      },
      submenuItems: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().submenuItems)
          })
      },
      sortOrder: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().sortOrder)
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
