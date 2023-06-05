// custom file
define(function(require) {
  "use strict";

  var $              = require('jquery');
  var ko             = require('knockout');
  var nsxApplication = require('nsx/nsx-application');
  var element = nsxApplication.getElement("assets", "asset");

  var Trigger = require('nsx/triggers');
  var AssetProjector = require('assets/asset/asset-projector');
  var PerformCommandAction = require('assets/action/perform-uploadAsset-action');

  var template = require('text!html/assets/asset-upload-input.html');
  var when = Trigger.when;

  function initView(domElement, spec) {
    var assetDataRef = spec.field;
    var type = ko.unwrap(spec.type);

    if (!ko.isWriteableObservable(assetDataRef)) {
      throw Error("Field: expected writable observable");
    }

    var fileSelected = Trigger.defineTrigger();
    var selectedFile = ko.observable();

    var $domRef = $(domElement);
    $domRef.html(template);
    var viewModel = defineViewModel({
      domRef: $domRef,
      assetDataRef: assetDataRef,
      fileSelected: fileSelected,
      selectedFile: selectedFile
    });

    defineUploadAction({
      type: type,
      assetDataRef: assetDataRef,
      fileSelected: fileSelected,
      selectedFile: selectedFile
    });

    ko.applyBindingsToDescendants(viewModel, domElement);
  }

  function defineViewModel(input) {
    var fileInput = input.domRef.find("input");

    function openFilePicker() {
      fileInput.click();
    }

    function uploadFile() {
      var files = fileInput[0].files;
      if (files) {
        input.selectedFile(files[0]);
        input.fileSelected.trigger();
      }
    }

    var filename = defineFilename({
      assetDataRef: input.assetDataRef
    });

    return {
      uploadFile: uploadFile,
      openFilePicker: openFilePicker,
      filename: filename
    };
  }

  function defineFilename(input) {
    var filename = ko.observable();

    var assetInfo = AssetProjector.defineProjector(
      input.assetDataRef, {
        projection: "info"
      }
    ).projection;

    when(assetInfo).thenDo(function (asset) {
      filename(asset.name);
    });

    return filename;
  }

  function defineUploadAction(input) {
    function getDataModel() {
      return {
        uploadUri: ko.unwrap(input.selectedFile),
        type: ko.unwrap(input.type)
      };
    }

    var performAction = PerformCommandAction.definePerformCommandAction({
      commandName: "uploadAsset",
      instance: ko.pureComputed(getDataModel),
      trigger: input.fileSelected,
      element: element
    });

    performAction.errorEvent.subscribe(function () {
      alert(ko.unwrap(performAction.errorMessage));
    });

    performAction.result.subscribe(function(dataRef) {
      if (dataRef) {
        input.assetDataRef(dataRef);
      }
    })
  }

  function init(domElement, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
    var spec = valueAccessor();
    initView(domElement, spec);
    return { controlsDescendantBindings: true }
  }

  function update(domElement, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {

  }

  ko.bindingHandlers.assetDownload = {
    init: init,
    update: update
  };
});
