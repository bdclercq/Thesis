"use strict";define(["knockout"],(function(e){e.components.register("stylesheet",{viewModel:function(e){this.href="/"+window.location.pathname.split("/")[1]+"/"+e.href},template:'<link rel="stylesheet" data-bind="attr: { href: href }">'})}));