define(["require","exports","knockout","nsx/nsx-detail","nsx/triggers"],(function(e,n,r,t,i){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.defineFinderSelector=void 0,n.defineFinderSelector=function(e,n){var d,o;n=n||{};var s=e.element,l=e.finderModel,u=l.finderOptions,a=r.observable(),c=l.searchDetails,f=r.pureComputed((function(){var e=a();return e&&e.name})),F=function(){l.resetFinder()};(0,i.when)(l.updated).thenDo((function(){a(l.searchMethod())}));var v=r.observable(!0),h=n.hiddenFields||[];return F(),{element:s,selectedFinder:a,finderDetails:c,hasFinder:f,setFormModel:function(e){d=e},setFinderElement:function(e){o=e},submit:function(){o&&t.getMapper(o,(function(e){var n=e.viewModelToJS(d.detailModel);l.setFinder(a(),n)}))},resetFinder:F,enableSelector:v,hiddenFields:h,finders:u}}}));