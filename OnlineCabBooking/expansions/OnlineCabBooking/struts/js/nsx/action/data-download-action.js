define(["require","exports","knockout","jquery","nsx/nsx-actions","nsx/nsx-struts-helper"],(function(e,t,n,o,i,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.defineDownloadAction=void 0,t.defineDownloadAction=function(e,t){var r=i.getElementAction(e.element,"download-json");e.trigger.subscribe((function(){var i={details:(null==t?void 0:t.details)?t.details():{},searchMethod:(null==t?void 0:t.searchMethod)?t.searchMethod().name:null,type:e.type};window.location.href=r+"?"+o.param(s.toStrutsObject(n.toJS(i)))}))}}));