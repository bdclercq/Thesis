"use strict";define((function(r){var e=r("knockout"),i=r("nsx/triggers");function n(r,i){var n=e.unwrap(r);if(n)for(var s=Object.keys(n),u=0;u<s.length;u++){var b=n[s[u]];e.isSubscribable(b)&&i.addTrigger(b)}}return{superSubscribe:function(r,s){var u=i.defineBundledTrigger();e.isSubscribable(r)&&(u.addTrigger(r),r.subscribe((function(){n(r,u)}))),n(r,u),u.subscribe(s)}}}));