"use strict";define(["knockout","nsx/triggers"],(function(e,n){var r="none",c={none:"",asc:"icon-chevron-down",desc:"icon-chevron-up"},i={none:"asc",asc:"desc",desc:"none"};return{defineSortableColumn:function(o,s){s=s||{};var t,u=o.fieldName,a=s.resetOn;t=s.ascending?"asc":s.descending?"desc":r;var d=e.observable(t),f=e.computed((function(){var e=d();return e===r?null:{fieldName:u,ascending:"asc"===e}})),l=n.defineTrigger(),g=n.defineSelectionTrigger({source:f});return l.subscribe((function(){var e=d();d(i[e])})),l.subscribe(g.trigger),a.subscribe((function(){d(r)})),{icon:e.pureComputed((function(){return c[d()]})),css:"sortable",click:l,sort:l,reset:a,select:g}},defineNotSortableColumn:function(e,r){return r=r||{},e.fieldName,{icon:c.none,css:"not-sortable",click:n.defineTrigger()}}}}));