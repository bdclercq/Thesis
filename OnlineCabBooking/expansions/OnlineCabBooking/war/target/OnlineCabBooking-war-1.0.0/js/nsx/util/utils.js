define(["require","exports","knockout"],(function(n,r,e){"use strict";function t(n){return void 0!==n}function i(n,r){if(!t(n))throw new Error("Missing argument: "+r)}function u(n,r){for(var e=-1/0,t=null,i=0,u=n;i<u.length;i++){var o=u[i];r(o)>e&&(e=r(o),t=o)}return t}Object.defineProperty(r,"__esModule",{value:!0}),r.combine=r.findMin=r.findMax=r.arrayContainsAll=r.getFieldOperators=r.getFields=r.filterNull=r.checkInput=r.checkArgument=r.isFalse=r.isTrue=r.isNotDefined=r.isDefined=void 0,r.isDefined=t,r.isNotDefined=function(n){return void 0===n},r.isTrue=function(n){return e.unwrap(n)},r.isFalse=function(n){return!e.unwrap(n)},r.checkArgument=i,r.checkInput=function(n,r){r.forEach((function(r){i(n[r],r)}))},r.filterNull=function(n){return n.filter((function(n){return null!==n}))},r.getFields=function(n){var r=[];for(var e in n)n.hasOwnProperty(e)&&r.push(e);return r},r.getFieldOperators=function(n){var r=[];for(var e in n)n.hasOwnProperty(e)&&r.push(e+":eq");return r},r.arrayContainsAll=function(n,r){for(var e=1,t=0,i=r;t<i.length;t++){var u=i[t];e*=1+n.indexOf(u)}return 0!==e},r.findMax=u,r.findMin=function(n,r){return u(n,(function(n){return-1*r(n)}))},r.combine=function n(){for(var r=[],e=0;e<arguments.length;e++)r[e]=arguments[e];for(var t={},i=0,u=r;i<u.length;i++){var o=u[i];for(var f in o)"object"==typeof o[f]&&"object"==typeof t[f]?t[f]=n(t[f],o[f]):t[f]=o[f]}return t}}));