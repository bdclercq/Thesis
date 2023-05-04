// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(['jquery', 'nsx/nsx-actions', 'nsx/error/error-trigger'], function ($, nsxActions, errorTrigger) {
  var logger = errorTrigger.defineTrigger();
  var errorMessages = {
    401: "Action not authorized, perhaps relogin?",
    403: "Forbidden action, perhaps relogin?",
    404: "Not Found",
    500: "Something went wrong in the server"
  };

  // @anchor:errorMessages:start
  // @anchor:errorMessages:end
  // anchor:custom-errorMessages:start
  // anchor:custom-errorMessages:end

  $(document).ajaxError(function (event, jqXHR) {
    var statusCode = jqXHR.statusCode().status;
    // @anchor:on-error:start
    // @anchor:on-error:end
    // anchor:custom-on-error:start
    // anchor:custom-on-error:end
    if (errorMessages[statusCode]) {
      // @anchor:log-error:start
      // @anchor:log-error:end
      // anchor:custom-log-error:start
      // anchor:custom-log-error:end
      logger.error(errorMessages[statusCode]);
    }
  });
});
