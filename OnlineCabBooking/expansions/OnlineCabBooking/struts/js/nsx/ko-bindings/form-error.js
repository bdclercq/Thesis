"use strict";define(["jquery","knockout","text!html/nsx/knockout/form-error.html"],(function(n,t,r){t.bindingHandlers.formError={init:function(e,i){var o={field:i()};return n(e).html(r),t.applyBindingsToDescendants(o,e),{controlsDescendantBindings:!0}}}}));