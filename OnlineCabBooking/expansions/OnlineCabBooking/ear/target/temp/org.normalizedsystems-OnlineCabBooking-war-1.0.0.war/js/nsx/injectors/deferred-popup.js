"use strict";define(["knockout","nsx/injectors/popup","nsx/triggers"],(function(i,e,n){return{definePopup:function(i,r){r=r||{};var t=i.trigger,g=i.definition,o=n.defineTrigger(),u=!1;function f(i,n){i.trigger=o,e.definePopup(i,n),u=!0,o.trigger()}t.subscribe((function(){u?o.trigger():g(f)}))}}}));