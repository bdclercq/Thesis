define(["require","exports","nsx/usability/shortcuts"],(function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.defineShortcuts=void 0,t.defineShortcuts=function(e){var t=null;function n(){for(var i=e.listView.items(),n=0;n<i.length;n++)if(i[n].isSelected())return void(t=n);t=null}function r(){var i=e.listView.items(),n=i.length;0!==n&&i[t=(t+n)%n].select.trigger()}i.registerShortcut({key:"up",callback:function(){n(),null===t?t=-1:t-=1,r()},scope:"page"}),i.registerShortcut({key:"down",callback:function(){n(),null===t?t=0:t+=1,r()},scope:"page"})}}));