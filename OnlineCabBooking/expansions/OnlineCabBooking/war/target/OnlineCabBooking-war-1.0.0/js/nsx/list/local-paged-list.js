"use strict";define(["knockout","nsx/simple-switch"],(function(e,t){return{defineListModel:function(n,i){var r=n.list,u=i.pageSize||e.observable(7),o=e.observable(1),s=e.pureComputed((function(){var e=(o()-1)*u();return r().slice(e,e+u())})),a=e.pureComputed((function(){var e=r().length,t=Math.ceil(e/u());return t>=1?t:1})),c=t.defineSimpleSwitch();return c.value.subscribe(o),{page:s,pageNb:o,gotoPage:c,numberOfPages:a}}}}));