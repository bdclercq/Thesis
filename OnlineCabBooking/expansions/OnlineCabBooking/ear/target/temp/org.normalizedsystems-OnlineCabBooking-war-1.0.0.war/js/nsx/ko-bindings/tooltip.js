"use strict";define(["jquery","knockout"],(function(t,i){i.bindingHandlers.tooltip={init:function(n,e,o,r,l){var u=i.unwrap(e());u&&t(n).tooltip({title:u,trigger:"hover",placement:"bottom",delay:100})}}}));