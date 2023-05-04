// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Trigger = require('nsx/triggers');

  // anchor:require:start
  // anchor:require:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineInstanceView(input, options) {
    var details = input.details;

    var viewmodel = initViewmodel();

    function defineLink(events) {
      var value = ko.observable();
      var id = ko.pureComputed(function () { return !!value() ? value().id : 0 });
      var name = ko.pureComputed(function () { return !!value() ? value().name : "" });
      var link = events.defineViewIntentTrigger({
        selection: value
      });
      return {
        value: value,
        id: id,
        name: name,
        link: link
      }
    }

    function defineValueTypeField() {
      var value = ko.observable();
      return {
        value: ko.observable()
      }
    }

    function initViewmodel() {
      return {
        id: ko.observable(),
        // anchor:init-viewModel:start
        name: ko.observable(),
        password: ko.observable(),
        fullName: ko.observable(),
        email: ko.observable(),
        mobile: ko.observable(),
        language: ko.observable(),
        firstName: ko.observable(),
        lastName: ko.observable(),
        persNr: ko.observable(),
        lastModifiedAt: ko.observable(),
        enteredAt: ko.observable(),
        disabled: ko.observable(),
        timeout: ko.observable(),
        encryptedPassword: ko.observable()
        // anchor:init-viewModel:end
        // @anchor:init-viewModel:start
        // @anchor:init-viewModel:end
        // anchor:custom-init-viewModel:start
        // anchor:custom-init-viewModel:end
      };
    }

    function updateViewmodel(userDetails) {
      if (!!userDetails) {
        viewmodel.id(userDetails.id);
        // anchor:update-viewModel:start
        viewmodel.name(userDetails.name);
        viewmodel.password(userDetails.password);
        viewmodel.fullName(userDetails.fullName);
        viewmodel.email(userDetails.email);
        viewmodel.mobile(userDetails.mobile);
        viewmodel.language(userDetails.language);
        viewmodel.firstName(userDetails.firstName);
        viewmodel.lastName(userDetails.lastName);
        viewmodel.persNr(userDetails.persNr);
        viewmodel.lastModifiedAt(userDetails.lastModifiedAt);
        viewmodel.enteredAt(userDetails.enteredAt);
        viewmodel.disabled(userDetails.disabled);
        viewmodel.timeout(userDetails.timeout);
        viewmodel.encryptedPassword(userDetails.encryptedPassword);
        // anchor:update-viewModel:end
        // @anchor:update-viewModel:start
        // @anchor:update-viewModel:end
        // anchor:custom-update-viewModel:start
        // anchor:custom-update-viewModel:end
      }
    }

    if (!!details.subscribe) {
      details.subscribe(updateViewmodel);
    } else {
      updateViewmodel(details);
    }

    // @anchor:define-after:start
    // @anchor:define-after:end
    // anchor:custom-define-after:start
    // anchor:custom-define-after:end

    return viewmodel;
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineInstanceView = defineInstanceView;
  return exports;
});
