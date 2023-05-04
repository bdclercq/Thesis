define(["require","exports","jquery","knockout","nsx/triggers"],(function(n,e,o,i,t){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.submitDialog=e.closeDialog=e.closeAllDialogs=e.hasDialogs=e.numberOfDialogs=e.showDialog=e.popupDialog=e.unclosableDialog=void 0;var r,c=[];function l(){return c.length>0}function a(){return c.length}function s(){return 1040+2*a()}function u(n){n.css("z-index",s()+1)}function g(){r.css("z-index",s())}function f(n){c.length>0||(r=o("<div class='modal-backdrop'/>"),n.append(r))}function d(){var n=a()-1;return n<0?null:c[n]}function h(){var n=d();n&&n.show()}function m(){d().trigger("cancelDialog"),p()}function p(){var n=c.pop();n&&(n.hide(),n.trigger("hideDialog"),i.cleanNode(n[0]),n.remove()),0===c.length?(r.remove(),r=null):(h(),g())}function v(e){var r,l,a=o.extend({},{title:"no title",showParent:!1,content:{},contentModel:{},interactive:!0},e),s=(0,t.defineTrigger)(),v=(0,t.defineTrigger)();return r=a.content,l=function(n){var e;a.showParent||(e=d())&&e.hide(),function(n){var e=o("#dynamicContent");f(e),e.append(n),c.push(n)}(n),function(n,e){var t=e.showReference||function(){window.alert("can not show reference")},r=e.errorMessages||i.observableArray();function c(){e.confirm&&e.confirm(e.contentModel,(function(){p()}))}var l={dialogId:i.pureComputed((function(){var n=i.unwrap(e.title);return n?n.replace(new RegExp("\\s+","g"),"-").toLowerCase():null})),title:e.title,contentModel:e.contentModel,isLarge:"large"===e.size,close:m,confirm:c,hasConfirm:!!e.confirm,showReference:t,errorMessages:r,interactive:e.interactive,hasErrorMessages:i.computed((function(){return r().length>0}))};i.applyBindings(l,n),o(n).on("submitDialog",c)}(n[0],a),h(),function(n){var e=o(window).height(),i=n.height(),t=n.find(".modal-body"),r=e-2*n.position().top-(i-t.height());t.css({"max-height":r})}(n),g(),u(n),n.on("hideDialog",s.trigger),n.on("cancelDialog",v.trigger)},n(["text!html/nsx/knockout/modalDialog.html"],(function(n){var e=o(n);e.find(".nsx-dialog-body").html(r),l(e)})),{close:p,onHide:s.subscribe,onCancel:v.subscribe}}e.hasDialogs=l,e.numberOfDialogs=a,e.submitDialog=function(){d().trigger("submitDialog")},e.closeDialog=p,e.closeAllDialogs=function(){for(;l();)p()},e.showDialog=v,e.popupDialog=function(n,e){var o=void 0;return e.confirm&&(o=function(n,e){n.confirm&&n.confirm(n,e)&&e()}),v({title:e.title,size:e.size,content:n,contentModel:e,confirm:o,showParent:!0})},e.unclosableDialog=function(n,e){var o=void 0;return e.confirm&&(o=function(n,e){n.confirm&&n.confirm(n,e)&&e()}),v({title:e.title,size:e.size,content:n,contentModel:e,confirm:o,showParent:!0,interactive:!1})}}));