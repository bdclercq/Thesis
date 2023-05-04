// custom file
define(function(require) {
  "use strict";

  var $              = require('jquery');
  var ko             = require('knockout');
  var nsxApplication = require('nsx/nsx-application');
  var template       = require('text!html/assets/downloadLink.html');

  function updateLink(domElement, spec) {
    var label = ko.unwrap(spec.label);
    var target = ko.unwrap(spec.target);
    var $element = $(domElement);

    var url = nsxApplication.getApplicationUrl() + target;

    var link = template
      .replace("{label}", label)
      .replace("{url}", url);

    $element.html(link);
  }

  function init(domElement, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
  }

  function update(domElement, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
    var spec = valueAccessor();
    updateLink(domElement, spec);
  }

  ko.bindingHandlers.assetDownload = {
    init: init,
    update: update
  };
});
