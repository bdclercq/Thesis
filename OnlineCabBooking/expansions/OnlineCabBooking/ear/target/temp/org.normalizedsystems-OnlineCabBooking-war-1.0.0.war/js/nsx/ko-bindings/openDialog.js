"use strict";define(["jquery","knockout","nsx/nsx-dialog"],(function(e,n,t){function i(n,i){if(void 0===i.viewModel)throw"viewModel parameter is not defined";if(void 0===i.template)throw"template parameter is not defined";!function(n,i,o){var u=o.event?o.event:"click";function d(){!function(e,n){require(["text!html/"+e.template+".html"],(function(e){t.popupDialog(e,n)}))}(o,i)}e(n).unbind(u,d),e(n).on(u,d)}(n,function(e){return"function"==typeof e.viewModel?e.viewModel.call(e):e.viewModel}(i),i)}n.bindingHandlers.openDialog={update:function(e,t){var o=t();i(e,n.unwrap(o))}}}));