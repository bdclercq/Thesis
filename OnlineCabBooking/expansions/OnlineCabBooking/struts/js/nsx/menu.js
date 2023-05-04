"use strict";define(["knockout","jquery","nsx/nsx-actions","nsx/injectors/page-segment"],(function(e,t,n,i){function o(i,o){var r,a,u=i.title,s=i.menuName,c=e.observable(),l=e.observable(),m=e.observable(),g=e.observable(),p=e.observable(),v=e.observable();function f(e){var t=[];if(e)for(var n=0;n<e.length;n++)t.push(b(e[n]));return t}function b(e){switch(e.type){case"menu":return{title:(t=e).title,items:f(t.items),isItem:!1,isMenu:!0,isSeparator:!1};case"item":return function(e){return{title:e.title,link:e.url,isSelected:e.url===location.href,isItem:!0,isMenu:!1,isSeparator:!1}}(e);case"separator":return{isItem:!1,isMenu:!1,isSeparator:!0};default:throw new Error("Unknown menu item type "+e.type)}var t}return r=n.getApplicationAction("menu-json"),a={menu:s},t.get(r,a,(function(e){c(e.list)})),function(){var e=n.getApplicationAction("account/user-getLoginInfo-json");t.get(e).done((function(e){if(e.success){var t=e.value;l(t.user.name),m(t.user.account.name),g(n.getApplicationAction(t.userPageUrl)),p(n.getApplicationAction(t.accountPageUrl)),v(t.admin)}}))}(),e.pureComputed((function(){return{title:u,items:f(c()),logout:n.getApplicationAction("do-logout"),username:l,userPage:g,accountName:m,accountPage:p,showAccountLink:v}}))}return e.components.register("navigation",{viewModel:o,template:{require:"text!html/nsx/knockout/navigation-bar.html"}}),{defineMenu:function(e,t){t=t||{};var n=o({title:e.title,menuName:e.menuName});return i.definePageSegment({view:e.view,viewModel:n,selector:e.selector}),{}}}}));