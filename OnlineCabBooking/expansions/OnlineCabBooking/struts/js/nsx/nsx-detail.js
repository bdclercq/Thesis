define(["require","exports"],(function(e,t){"use strict";function n(e){return e.getComponentName()+"/"+e.getElementName()+"-ko-mapper"}function m(e,t){return t||(t=function(e){var t=e.getComponentName()+"/";return t+(e.getElementName()+"-ko-form.html")}(e)),"text!html/"+t}function o(e){var t="text!html/";return t+=e.getComponentName()+"/",t+=e.getElementName()+"-ko-detail.html"}Object.defineProperty(t,"__esModule",{value:!0}),t.getMapper=t.getView=t.getForm=t.modelScriptName=t.formTemplateName=void 0,t.modelScriptName=n,t.formTemplateName=m,t.getForm=function(){for(var t,o,r,a=[],i=0;i<arguments.length;i++)a[i]=arguments[i];t=a[0],3===a.length?(o=a[1],r=a[2]):r=a[1],e([n(t),m(t,o)],(function(e,n){r({mapper:e,template:n,element:t,defaults:{}})}))},t.getView=function(t,n){e([o(t)],(function(e){n({template:e})}))},t.getMapper=function(t,m){e([n(t)],m)}}));