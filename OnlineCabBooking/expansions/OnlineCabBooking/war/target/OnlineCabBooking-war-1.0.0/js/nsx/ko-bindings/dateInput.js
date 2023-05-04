"use strict";define(["jquery","knockout","moment","text!html/nsx/knockout/dateInput.html","bootstrap_dateTimePicker"],(function(t,e,i,a){var n=!1;function r(e){var i=t(e);return i.html(a),i.find(".datePicker")}function o(e,i){t(e).on("changeDate",i,(function(e){var i=t(e.target).data("datetimepicker"),a=e.data,r=i.getDate();i.timezoneOffset,r=function(t,e){return t}(r),n=!0,a(r),n=!1}))}function d(a,r,o){if(!n){var d=t(a).find(".datePicker"),f=d.data("datetimepicker"),s=e.unwrap(r());s?("string"==typeof s&&(s=i(s).toDate()),f.setDate(s),f.timezoneOffset=s.getTimezoneOffset()):(f.setDate(void 0),f.timezoneOffset=0),function(t,e,i){if(void 0===e().enable)return;var a=t.find("input").prop("disabled");e().enable?a&&i.enable():a||i.disable()}(d,o,f)}}e.bindingHandlers.dateInput={init:function(a,n,d,f,s){var u=n(),m=e.utils.unwrapObservable(u);"string"==typeof m&&(m=i(m).toDate());var c=r(a);c.datetimepicker({orientation:"left",pickTime:!1,format:"dd-MM-yyyy"}),m&&c.data("datetimepicker").setDate(m),o(a,u),e.utils.domNodeDisposal.addDisposeCallback(a,(function(){t(a).datetimepicker("destroy")}))},update:d},e.bindingHandlers.dateTimeInput={init:function(t,a,n,d,f){var s=r(t),u=a(),m=e.utils.unwrapObservable(u);"string"==typeof m&&(m=i(m).toDate()),s.datetimepicker({orientation:"left",pickTime:!0,format:"dd-MM-yyyy hh:mm:ss"});var c=s.data("datetimepicker");m?(c.setDate(m),c.timezoneOffset=m.getTimezoneOffset()):c.timezoneOffset=0,o(t,u)},update:d};return{}}));