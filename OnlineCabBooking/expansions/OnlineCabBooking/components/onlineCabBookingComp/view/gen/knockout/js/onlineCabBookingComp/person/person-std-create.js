// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var utils = require('nsx/util/utils');
  var Popup = require('nsx/injectors/popup');
  var Trigger = require("nsx/triggers");
  var when = Trigger.when;

  var AccessRights = require('nsx/config/access-rights');
  var accessRightsController = AccessRights.getAccessRightsController()

  var PersonEvents = require('onlineCabBookingComp/person/person-events');
  var PersonForm = require('onlineCabBookingComp/person/person-instance-form');
  var PersonActions = require('onlineCabBookingComp/person/person-actions');
  var PersonElement = require('nsx/nsx-application').getElement('onlineCabBookingComp', 'person');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineCreateDialog(options) {
    options = options || {}
    var loaded = Trigger.defineContinuingTrigger();

    var createRightsRequest = accessRightsController.requestRight({
      element: PersonElement,
      requestedRight: AccessRights.Right.CREATE
    })

    when(createRightsRequest.granted).thenDo(render);
    when(createRightsRequest.refused).thenDo(function () {
      throw new Error("You do not have the rights to create a(n) Person element!")
    });

    function render() {
      var createIntent = PersonEvents.defineCreateIntentListener();

      var constants = ko.observable();
      var values = ko.observable();

      var formView = "onlineCabBookingComp/person-form";
      var title = "Person";
      var layoutConfig = initLayoutConfig(constants);
      var defaultValues = { id: 0 };
      var formOptions = {
        resetOn: createIntent,
        action: PersonActions.createAction()
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

      var form = PersonForm.defineForm({
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
      username: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().username)
          })
      },
      password: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().password)
          })
      },
      email: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().email)
          })
      },
      mobile: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().mobile)
          })
      },
      address: {
          disabled: ko.pureComputed(function () {
            return utils.isDefined(constants().address)
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
