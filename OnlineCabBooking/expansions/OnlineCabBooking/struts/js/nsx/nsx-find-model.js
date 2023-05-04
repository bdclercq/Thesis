"use strict";define(["require","jquery","nsx/nsx-actions","nsx/nsx-utils","nsx/nsx-agent"],(function(e,t,n,r,a){function i(e){var n={},r=a.createAgent(e),i={searchMethod:e.findAllMethod,projection:"info",details:{},rowMapper:void 0};arguments.length>1&&("string"==typeof arguments[1]?i.searchMethod=arguments[1]:"object"==typeof arguments[1]&&(i=t.extend(i,arguments[1]))),arguments.length>2&&(i.details=arguments[2]);var s=[];function o(e){i.searchMethod=e}function d(e){return i.rowMapper?t.map(e,i.rowMapper):e}return n.resetFinder=function(){o(e.findAllMethod),n.setDetails({})},n.setFinder=function(e,t){o(e),n.setDetails(t)},n.setDetails=function(e){i.details=e},n.setSortFields=function(e){s=e},n.getPage=function(e,t,n){var a={page:e,rowsPerPage:t,details:i.details,sortFields:s,projection:i.projection,searchMethod:i.searchMethod};r.find(a).done((function(e){if(e.success&&n){var t=d(e.list);n(e.numberOfPages,t,e.totalNumberOfItems)}}))},n}return{createDataModel:i,createLinkedDataModel:function(e,t,n){e.getElementName();var a=t.getElementName(),s=n||a,o=i(e,"findBy"+r.firstToUpperCase(s)+"Eq");return o.setParent=function(e){var t={};t[s]={id:e.id},o.setDetails(t)},o},createDependentDataModel:function(e){var n=e.element,a=(e.parentElement,void 0),s=e.searchMethod;s||(s="findBy"+r.firstToUpperCase(e.parentField)+"Eq");var o=i(n,{searchMethod:s,projection:e.projection,rowMapper:e.rowMapper});o.setParent=function(n){if(a=n){var r=t.extend({},e.searchDetails);r[e.parentField]={id:a.id},o.setDetails(r)}};var d=o.getPage;return o.getPage=function(e,n,r){if(a)return d(e,n,r);r(0,[]),t.Deferred().resolveWith({success:!0,currentPage:0,numberOfPages:0,rowsPerPage:n})},o}}}));