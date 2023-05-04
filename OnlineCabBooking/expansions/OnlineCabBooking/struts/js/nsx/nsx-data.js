define(["require","exports","jquery","nsx/nsx-actions","nsx/nsx-request","nsx/nsx-struts-helper"],(function(e,t,n,o,i,r){"use strict";function s(e){var t={id:e.id,projection:e.projection},i=o.getElementAction(e.element,"getProjection-json");return n.post(i,t,e.callback)}function a(e){var t=e.element,n=e.projection,i=e.searchMethod,s=e.details,a=e.page,c=void 0===a?0:a,l=e.rowsPerPage,d=void 0===l?7:l,u=e.sortFields,p=void 0===u?[]:u,j=o.getElementAction(t,"search-json"),g={page:c||0,rowsPerPage:d||7,details:s,sortFields:p,projection:n,searchMethod:i},f=r.toStrutsObject(g);return $.get(j,f)}Object.defineProperty(t,"__esModule",{value:!0}),t.modifyWithProjection=t.findSingle=t.find=t.deleteMultipleDetails=t.deleteDetails=t.saveDetails=t.getWithProjection=t.getProjection=t.getDetails=void 0,t.getDetails=function(e,t,i){var r={};r[e.getElementName()+"Oid"]=t;var s=o.getElementAction(e,"detail-json");return n.get(s,r,i)},t.getProjection=s,t.getWithProjection=function(e,t){return s({element:e.getElement(),id:t.id,projection:e.getName()})},t.saveDetails=function(e,t,n){var r=o.getElementAction(e,"saveDetails-json"),s=JSON.stringify(t);return i.postSave({url:r,data:s,success:n,dataType:"json",contentType:"application/json; charset=UTF-8"})},t.deleteDetails=function(e,t,n){var r=o.getElementAction(e,"delete-json"),s={};return s[e.getElementName()+"Oid"]=t,i.postDelete({url:r,data:s,success:n,dataType:"json"})},t.deleteMultipleDetails=function(e,t,n){var r=o.getElementAction(e,"delete-multiple-json");return i.postDelete({url:r,data:JSON.stringify({targets:t}),success:n,dataType:"json",contentType:"application/json; charset=UTF-8"})},t.find=a,t.findSingle=function(e){var t=$.Deferred();return a({page:0,rowsPerPage:2,details:e.details||{},sortFields:[],projection:e.projection||"info",searchMethod:e.searchMethod,element:e.element}).done((function(e){if(e.success){var n=e.list;switch(n.length){case 0:t.resolve({found:!1});break;case 1:var o={found:!0,value:n[0]};t.resolve(o);break;default:t.reject()}}else t.rejectWith({error:"error occurred"})})),t.promise()},t.modifyWithProjection=function(e){var t=o.getElementAction(e.element,"modify-json");t+="?projection="+e.projection;var n=JSON.stringify({details:e.projectionData});return i.postModify({url:t,data:n,dataType:"json",contentType:"application/json; charset=UTF-8"})}}));