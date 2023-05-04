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
        fullName: ko.observable(),
        uri: ko.observable(),
        depth: ko.observable(),
        border: ko.observable(),
        thumbType: ko.observable(),
        thumbName: ko.observable(),
        targetType: ko.observable(),
        targetName: ko.observable(),
        targetId: ko.observable(),
        leftX: ko.observable(),
        topY: ko.observable(),
        width: ko.observable(),
        height: ko.observable(),
        clickAction: ko.observable(),
        hooverAction: ko.observable()
        // anchor:init-viewModel:end
        // @anchor:init-viewModel:start
        // @anchor:init-viewModel:end
        // anchor:custom-init-viewModel:start
        // anchor:custom-init-viewModel:end
      };
    }

    function updateViewmodel(thumbnailDetails) {
      if (!!thumbnailDetails) {
        viewmodel.id(thumbnailDetails.id);
        // anchor:update-viewModel:start
        viewmodel.name(thumbnailDetails.name);
        viewmodel.fullName(thumbnailDetails.fullName);
        viewmodel.uri(thumbnailDetails.uri);
        viewmodel.depth(thumbnailDetails.depth);
        viewmodel.border(thumbnailDetails.border);
        viewmodel.thumbType(thumbnailDetails.thumbType);
        viewmodel.thumbName(thumbnailDetails.thumbName);
        viewmodel.targetType(thumbnailDetails.targetType);
        viewmodel.targetName(thumbnailDetails.targetName);
        viewmodel.targetId(thumbnailDetails.targetId);
        viewmodel.leftX(thumbnailDetails.leftX);
        viewmodel.topY(thumbnailDetails.topY);
        viewmodel.width(thumbnailDetails.width);
        viewmodel.height(thumbnailDetails.height);
        viewmodel.clickAction(thumbnailDetails.clickAction);
        viewmodel.hooverAction(thumbnailDetails.hooverAction);
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
