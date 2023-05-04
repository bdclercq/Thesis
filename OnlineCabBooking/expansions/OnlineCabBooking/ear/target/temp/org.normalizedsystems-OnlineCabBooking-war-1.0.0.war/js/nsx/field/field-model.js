define(["require","exports","nsx/triggers","knockout","nsx/util/utils","nsx/field/field-type","nsx/model/dataref","nsx/nsx-knockout-utils"],(function(e,t,n,r,a,i,u){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.defineField=void 0,t.defineField=function(e,t){t=t||{};var o,f={},d=(o=e.type)===i.LIST?r.observableArray([]):o===i.DATE?r.observable().extend({nsxDate:!0}):o===i.DATAREF?r.observable().extend({nsxDataRef:!0}):r.observable(),l=n.defineTrigger();return f.value=d,f.extendWith=function(e){for(var t in e)e.hasOwnProperty(t)&&(f[t]=e[t]);return f},f.fieldName=e.fieldName,f.type=e.type,f.key=e.key,f.isValueType=e.isValueType,f.update=function(t){a.isDefined(t)?function(e,t,n){e(t)}(d,t,e.type):function(e,t){t===i.LIST?e([]):e(null)}(d,e.type),l.trigger()},f.asDataRef=r.pureComputed((function(){if(e.type!==i.DATAREF)throw new Error("Not a dataRef!");var t=d(),n=r.unwrap(t.value);return n?(0,u.createDataRef)(n.name,n.id):(0,u.createNullDataRef)()})),f.updateEvent=l,f}}));