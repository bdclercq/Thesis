"use strict";define(["jquery"],(function(e){return{createDataElement:function(n){var t={component:n.component,elementName:n.elementName,getProjection:function(e){var n=u[e];if(n)return n;throw"Projection '"+e+"' not found"},getFinder:function(e){return a[e]}},r=function(n){var t={};return e.each(n,(function(e,n){t[n.name]=n})),t}(n.fields),o=e.map(n.projections,(function(n){var t={},o=n.calculatedFields||[];e.each(o,(function(e,n){n[n.name]=n}));var u=e.map(n.fieldNames,(function(e){var n=r[e];return n||t[e]}));return{getName:function(){return n.name},getFields:function(){return u}}})),u={},a={};return e.each(o,(function(e,n){u[n.getName()]=n})),t}}}));