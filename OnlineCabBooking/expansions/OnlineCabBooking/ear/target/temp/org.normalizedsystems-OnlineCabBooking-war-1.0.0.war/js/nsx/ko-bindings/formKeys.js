"use strict";define(["jquery","knockout"],(function(n,e){e.bindingHandlers.enterKey={init:function(e,t,r,i,u){var a=t();n(e).on("keypress",(function(n){if(13!==(n.which||n.keyCode))return!0;return n.target.blur(),a.call(u.$data),!1}))}},e.bindingHandlers.escapeKey={init:function(e,t,r,i,u){var a=t();n(e).on("keyup",(function(n){if(27!==(n.which||n.keyCode))return!0;return n.target.blur(),a.call(u.$data),!1}))}}}));