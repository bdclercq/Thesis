var __spreadArray=this&&this.__spreadArray||function(e,i,n){if(n||2===arguments.length)for(var t,r=0,s=i.length;r<s;r++)!t&&r in i||(t||(t=Array.prototype.slice.call(i,0,r)),t[r]=i[r]);return e.concat(t||Array.prototype.slice.call(i))};define(["require","exports","knockout","nsx/triggers","nsx/util/text-utils","nsx/simple-switch","nsx/nsx-agent","nsx/paging/standard-pagination","nsx/injectors/popup","nsx/list/standard-paged-list","nsx/list/standard-list-view","nsx/finder/finder-model"],(function(e,i,n,t,r,s,a,l,o,d,c,u){"use strict";function f(e,i){var t=n.unwrap(i.finderOptions).filter((function(i){return i.name===e}));if(0===t.length)throw"Cannot find Finder "+e+" in Finder options list";return t[0]}Object.defineProperty(i,"__esModule",{value:!0}),i.createLinkFieldMultiPicklist=void 0,i.createLinkFieldMultiPicklist=function(e,i){var p=(0,t.defineTrigger)(),g=(0,t.defineTrigger)(),h=(0,t.defineTrigger)(),v=function(e,i){var n=[e.picklist.finder];e.picklist.finderOptions&&e.picklist.finderOptions.forEach((function(e){-1===n.indexOf(e)&&n.push(e)}));return(0,u.defineFinders)({element:e.dataElement,defaultFinder:e.picklist.finder,defaultDetails:e.picklist.finderDetails,finders:n,resetOn:i})}(i,g),m=function(e){var i=e.params,r=e.showPicklist,s=e.finderDataModel,a=n.observable(!1),o=(0,t.defineTrigger)(),u=(0,d.defineListModel)({searchMethod:s.searchMethod,searchDetails:s.searchDetails},{reloadOn:r,pageSize:i.picklist.pageSize,sorting:{sortFields:i.picklist.sortFields}}),f=l.definePagination(u);f.toggleFinder=o;var p=(0,c.defineListView)({instanceView:{defineInstanceView:function(e){var n=e.details,t={};return i.picklist.displayFields.forEach((function(e){var i=n[e];t[e]=function(e){return e?"string"==typeof e?e:e.functionalKeyAsString?e.functionalKeyAsString:e.name?e.name:e.value?e.value:"".concat(e):"".concat(e)}(i)})),t}},page:u.page});return p.displayFields=i.picklist.displayFields,(0,t.when)(s.updated).thenDo(u.reloadPage),(0,t.when)(r).thenDo((function(){return f.pageNb("0")})),(0,t.when)(o).thenDo((function(){a(!a())})),{element:i.element,showFinders:a,finderModel:s,pagination:f,list:p}}({params:i,showPicklist:p,finderDataModel:v}),k=function(e){var i=e.params,r=e.selectedOption,o=e.showPicklist,d=n.observableArray([]),u=function(e,i){var t=n.pureComputed((function(){return i().length})),r=n.observable(e.picklist.pageSize),l=n.pureComputed((function(){return Math.max(1,Math.ceil(t()/r()))})),o=(0,s.defineSwitch)({defaultValue:1}),d=o.value,c=n.observable([]),u=(0,a.createAgent)(e.dataElement),f={};function p(){var e=r()*(d()-1),n=e+r();return i().slice(e,n)}function g(){for(var e=[],i=0,n=p();i<n.length;i++){var t=n[i];f[t.id]||e.push(u.getProjection(t,"info").then((function(e){e.success&&(f[e.value.id]=e.value)})))}0===e.length?h():Promise.all(e).then((function(){h()}))}function h(){var e=p();c(e.map((function(e){return f[e.id]})))}return d.subscribe(g),i.subscribe(g),g(),{numberOfPages:l,gotoPage:o,page:c,pageNb:d,reloadPage:g,rowsPerPage:r,totalNumberOfItems:t}}(i,d),f=l.definePagination(u),p=(0,c.defineListView)({instanceView:{defineInstanceView:function(e){return e.details}},page:u.page});return p.displayFields=i.picklist.displayFields,(0,t.when)(o).thenDo((function(){return d(__spreadArray([],i.value(),!0))})),(0,t.when)(o).thenDo((function(){return f.pageNb("0")})),r.subscribe((function(e){e&&(d().some((function(i){return i.id===e.id}))||d.push(e))})),p.selection.subscribe((function(e){e&&d.remove((function(i){return i.id===e.id}))})),{element:i.element,pagination:f,list:p,selection:d}}({params:i,selectedOption:m.list.selection,showPicklist:p});(0,o.definePopup)({trigger:p,title:(0,r.translate)(i.dataElement.getElementName()),view:"nsx/knockout/linkfield-multi-picklist",viewModel:{leftSide:m,rightSide:k}},{onConfirm:h,size:"large"}),h.subscribe((function(){i.value(k.selection())})),e.addEventListener("show-picklist",(function(e){if(e.detail){var n=f(e.detail.finder,v);v.setFinder(n,e.detail.finderDetails)}else{n=f(i.picklist.finder,v);v.setFinder(n,i.picklist.finderDetails),g.trigger()}p.trigger()}))}}));