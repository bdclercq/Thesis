"use strict";define(["knockout"],(function(r){return{defineProgressBar:function(n,e){var t=n.progress;return{width:r.pureComputed((function(){return 100*r.unwrap(t)+"%"}))}}}}));