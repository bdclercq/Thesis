"use strict";define(["require","jquery","nsx/nsx-application"],(function(e,n,t){var r={},c=t.getApplicationUrl()+"/account/getHelpInfo-json";return{getHelpInfo:function(e,t){var i=r[e];i?t(i):n.get(c,{target:e},(function(n){var c;n.success&&(c=n.value),r[e]=c,t(c)}))}}}));