"use strict";define(["knockout","jquery","nsx/usability/shortcuts"],(function(e,t,c){e.bindingHandlers.shortcut={init:function(t,n,i,s,r){var u=e.unwrap(n());u&&u.key&&c.registerShortcut({element:t,key:u.key,callback:u.callback,scope:u.scope})}}}));