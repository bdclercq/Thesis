define(["require","exports"],(function(t,e){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.createPicklistButton=void 0,e.createPicklistButton=function(t){(function(t){var e=document.createElement("button");e.type="button",e.classList.add("button","button-small","searchButton"),e.style.margin="0",e.tabIndex=-1;var n=document.createElement("span");return n.classList.add("icon-search"),e.append(n),t.append(e),{button:e}})(t).button.addEventListener("click",(function(){var e=new CustomEvent("show-picklist");t.dispatchEvent(e)}))}}));