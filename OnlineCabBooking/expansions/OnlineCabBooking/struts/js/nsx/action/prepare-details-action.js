define(["require","exports","jquery","knockout","nsx/triggers","nsx/nsx-actions","nsx/nsx-struts-helper"],(function(e,r,s,t,n,i,o){"use strict";Object.defineProperty(r,"__esModule",{value:!0}),r.definePrepareDetailsAction=void 0,r.definePrepareDetailsAction=function(e,r){r=r||{};var a=function(e){return(0,i.getElementAction)(e.element,"prepare-json")}(e),u=t.observable(!1),c=t.observable({}),g=t.observable(!1),l=t.observableArray([]),f=t.observable(),b=n.defineTrigger(),v=n.defineTrigger(),d=n.defineTrigger();function p(){c({}),g(!1),l([]),f()}function x(e){g(e.success),e.success?function(e){p(),c(e.value),l(e.messages),v.trigger()}(e):function(e){p(),f(e.errorMessage),d.trigger()}(e),u(!1),b.trigger()}return e.trigger.subscribe((function(){if(!u()){u(!0);var r=t.toJS(e.data);s.ajax({url:a,data:(0,o.toStrutsObject)({details:r})}).then(x)}})),{busy:u,result:c,success:g,messages:l,errorMessage:f,completeEvent:b,successEvent:v,errorEvent:d}}}));