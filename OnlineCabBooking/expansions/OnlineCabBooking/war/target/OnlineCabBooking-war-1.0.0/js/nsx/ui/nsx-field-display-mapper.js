"use strict";define(["jquery"],(function(e){function n(e){return"<span data-bind='text: "+r(e)+"'></span>"}function t(e){return"<span>"+("field '"+e.name+"', with type '"+e.dataType+"' is not mapped")+"</span>"}function a(e){return"<span data-bind='dateString: "+r(e)+"'></span>"}function r(e){return e.name+".value"}return{createFieldDisplayMapper:function(r){r=r||{};var i={},p={String:n,Integer:n,Date:a};p=e.extend({},p,r.valueTypeMappers);var u={};return u=e.extend({},u,r.linkTypeWrappers),i.mapField=function(e){switch(e.fieldType){case"value":return(p[(n=e).dataType]||t)(n);case"link":return function(e){return t(e)}(e);default:throw"fieldType: "+e.fieldType+" not supported"}var n},i}}}));