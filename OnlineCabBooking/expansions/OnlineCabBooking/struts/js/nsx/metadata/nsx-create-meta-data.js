"use strict";define(["jquery"],(function(e){return{createDataElement:function(n){var t=function(n){var t={};return e.each(n,(function(e,n){t[n.name]=n})),t}(n.fields),r=e.map(n.projections,(function(n){var r={};e.each(n.calculatedFields,(function(e,n){n[n.name]=n}));var o=e.map(n.fieldNames,(function(e){var n=t[e];return n||r[e]}));return{getName:function(){return n.name},getFields:function(){return o}}})),o={},u={};return e.each(r,(function(e,n){o[n.getName()]=n})),{component:n.component,elementName:n.elementName,getProjection:function(e){var n=o[e];if(n)return n;throw"Projection '"+e+"' not found"},getFinder:function(e){return u[e]}}}}}));