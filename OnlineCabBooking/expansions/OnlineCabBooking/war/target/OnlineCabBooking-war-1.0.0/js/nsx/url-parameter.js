"use strict";define(["jquery","knockout"],(function(e,n){return{defineParameter:function(r,i){i=i||{};var a=r.name,o=void 0===i.failIfUndefined||i.failIfUndefined,t=a+"=([\\w\\d%]+)&?",f=location.search.match(new RegExp(t));if(!f){if(o)throw"Missing url parameter: "+a;return n.observable(void 0)}var d=decodeURIComponent(f[1]);return n.observable(e.parseJSON(d))}}}));