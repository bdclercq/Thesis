define(["require","exports","nsx/triggers","knockout","nsx/util/text-utils","nsx/nsx-agent"],(function(e,t,n,r,i,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.defineFinders=void 0,t.defineFinders=function(e){var t=e.element,s=e.defaultFinder,a=e.finders,d=e.defaultDetails,c=e.resetOn,l=F(s),u=r.observable(l),f=r.observable(d),g=(0,n.defineTrigger)(),p=(0,o.createAgent)(t);function m(){u(l),f(r.toJS(d)),g.trigger()}function F(e){return{name:e,component:t.getComponentName(),element:t.getElementName(),translation:(0,i.translate)("".concat(t.getQualifiedName(),".").concat(e)),find:function(t){var n={searchMethod:e,details:t.details,rowsPerPage:t.rowsPerPage,page:t.page,projection:t.projection,sortFields:t.sortFields};return p.find(n)}}}var h=a.map(F);return(0,n.when)(c).thenDo(m),{searchMethod:u,searchDetails:f,finderOptions:h,resetFinder:m,setFinder:function(e,t){u(e),f(t?r.toJS(t):r.toJS(d)),g.trigger()},setFinderDetails:function(e){f(r.toJS(e)),g.trigger()},updated:g}}}));