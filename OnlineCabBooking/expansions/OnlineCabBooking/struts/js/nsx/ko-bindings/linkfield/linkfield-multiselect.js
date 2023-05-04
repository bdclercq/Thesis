define(["require","exports","knockout","nsx/nsx-agent","./dropdowns"],(function(e,t,n,i,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.createLinkFieldMultiSelectionList=void 0,t.createLinkFieldMultiSelectionList=function(e,t){var c=(0,i.createAgent)(t.dataElement),o=function(e){var t=document.createElement("div");return e.append(t),{selection:t}}(e);function s(e){(function(e,t){return new Promise((function(n,i){t.getProjection(e,"info").then((function(e){e.success?n(e.value):i("no value found")}))}))})(e,c).then((function(n){var i=document.createElement("span");i.classList.add("nsx-multiselect-item"),i.setAttribute("name","selection-"+e.id);var c=document.createElement("span");c.textContent=(0,a.getLabel)(n,t);var s=document.createElement("span");s.classList.add("icon-remove","nsx-multiselect-item-close"),i.append(c,s),i.addEventListener("click",(function(){t.value.remove(e)})),o.selection.append(i)}))}t.value().forEach(s);var l=t.value.subscribe((function(e){e.forEach((function(e){switch(e.status){case"added":s(e.value);break;case"deleted":t=e.value,n=o.selection.children.namedItem("selection-"+t.id),o.selection.removeChild(n)}var t,n}))}),this,"arrayChange");n.utils.domNodeDisposal.addDisposeCallback(e,(function(){l.dispose()}))}}));