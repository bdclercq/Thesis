define(["require","exports","knockout","nsx/util/utils"],(function(e,u,i,n){"use strict";Object.defineProperty(u,"__esModule",{value:!0}),u.ifBoth=u.isDataRefDefined=void 0,u.isDataRefDefined=function(e){return i.pureComputed((function(){var u=i.unwrap(e);return n.isDefined(u)&&null!=u&&"number"==typeof u.id&&0!==u.id}))},u.ifBoth=function(e,u){return i.pureComputed((function(){return n.isTrue(e)&&n.isTrue(u)}))}}));