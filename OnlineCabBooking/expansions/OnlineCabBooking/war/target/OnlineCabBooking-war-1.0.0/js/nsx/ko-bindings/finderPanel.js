"use strict";define(["jquery","knockout"],(function(n,i){i.bindingHandlers.finderPanel={init:function(e,t,r,l,u){require(["text!html/nsx/knockout/finderPanel.html"],(function(t){var r=n(e);r.html(t),i.applyBindings(l,r.children()[0])}))}}}));