define(function (require) {
  var ko = require('knockout');
  var nsxActions = require("nsx/nsx-actions");
  var nsxRequest = require('nsx/nsx-request');
  var Triggers = require("nsx/triggers");
  var ErrorTrigger = require("nsx/error/error-trigger");
  var nsxStrutsHelper = require('nsx/nsx-struts-helper');

  /**
   * @param {{
   *   instance: any,
   *   commandName: string
   *   trigger: TriggerInput,
   *   element: NsxElement
   * }} input
   * @param {{
   * }} options
   * @return {{
   *   result: KnockoutObservable<any>,
   *   success: KnockoutObservable<boolean>,
   *   messages: KnockoutObservable<Array<string>>,
   *   errorMessage: KnockoutObservable<string>,
   *   fieldErrorMap: KnockoutObservable<Object>,
   *   completeEvent: TriggerOutput<void>,
   *   successEvent: TriggerOutput<void>,
   *   errorEvent: TriggerOutput<void>
   * }}
   */
  function definePerformCommandAction(input, options){
    options = options || {};

    var result = ko.observable({});
    var success = ko.observable(false);
    var messages = ko.observableArray([]);
    var errorMessage = ko.observable();
    var fieldErrorMap = ko.observable({});

    var completeEvent = Triggers.defineTrigger();
    var successEvent = Triggers.defineTrigger();
    var errorEvent = Triggers.defineTrigger();

    var errorTrigger = ErrorTrigger.defineTrigger({
      subject: input.element.getQualifiedName() + "." + input.commandName + ".perform"
    });

    var action = nsxActions.getElementAction(input.element, "upload-json");


    function clearResponse() {
      result({});
      success(false);
      messages([]);
      errorMessage();
      fieldErrorMap({});
    }

    function handleResponse(response) {
      success(response.success);
      if (response.success) {
        handleSuccess(response);
      } else {
        handleError(response);
      }
      completeEvent.trigger();
    }

    function handleSuccess(response) {
      clearResponse();
      result(response.value);
      messages(response.messages);
      successEvent.trigger();
    }

    function handleError(response) {
      clearResponse();
      errorMessage(response.errorMessage);
      fieldErrorMap(mapFieldErrors(response.fieldErrors));
      errorEvent.trigger();
      errorTrigger.error(response.errorMessage);
    }

    function mapFieldErrors(fieldErrors) {
      var map = {};
      if (fieldErrors) {
        fieldErrors.forEach(function (fieldError) {
          map[fieldError.qualifiedFieldName] = fieldError.message;
        });
      }
      return map;
    }

    function submit() {
      var data = ko.unwrap(input.instance);

      var fileData = data.uploadUri;
      delete data.uploadUri;

      var formData = nsxStrutsHelper.mapToFormData(data);
      formData.append("uploadData", fileData);

      nsxRequest.postPerformCommand({
        url: action,
        data: formData,
        processData: false,
        contentType: false
      }).then(handleResponse);
    }

    input.trigger.subscribe(submit);

    return {
      result: result,
      success: success,
      messages: messages,
      errorMessage: errorMessage,
      fieldErrorMap: fieldErrorMap,
      completeEvent: completeEvent,
      successEvent: successEvent,
      errorEvent: errorEvent
    }
  }

  return {
    definePerformCommandAction: definePerformCommandAction
  }
});
