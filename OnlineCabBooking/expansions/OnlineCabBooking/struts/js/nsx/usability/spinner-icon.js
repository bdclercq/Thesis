define(["require","exports","jquery"],(function(e,n,i){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.hideSpinner=n.showSpinner=void 0;n.showSpinner=function(){if(0===i("#spinner").length){var e=i("<div id='spinner' class='nsx-spinner nsx-spinner-fixed'></div>");i("body").append(e)}},n.hideSpinner=function(){var e=i("#spinner");e.length>0&&e.remove()}}));