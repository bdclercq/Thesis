"use strict";define(["jquery","nsx/injectors/page-segment","text!json/application-info.json"],(function(e,n,i){return{defineAppInfo:function(o,t){var a=e.parseJSON(n);return i.definePageSegment({view:"nsx/knockout/application-info",viewModel:a,selector:"#appInfo",failSilent:!0}),{pageInfo:a}}}}));