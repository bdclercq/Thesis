"use strict";define(["knockout","jquery","moment"],(function(e,t,n){function r(e){if(!e||e.hasOwnProperty("value")&&!e.value)return null;var t;if("string"==typeof(t=e.value?e.value:e)){var r=t.length;r>0&&"Z"!==t[r-1]&&(t+="Z")}return n(t).toDate()}function u(e){return e?e.toISOString():null}return Date.prototype.toISOString||function(){function e(e){var t=String(e);return 1===t.length&&(t="0"+t),t}Date.prototype.toISOString=function(){return isNaN(this)?null:this.getUTCFullYear()+"-"+e(this.getUTCMonth()+1)+"-"+e(this.getUTCDate())+"T"+e(this.getUTCHours())+":"+e(this.getUTCMinutes())+":"+e(this.getUTCSeconds())+"."+String((this.getUTCMilliseconds()/1e3).toFixed(3)).slice(2,5)+"Z"}}(),e.extenders.nsxDate=function(t,n){var i=e.pureComputed({read:function(){return u(t())},write:function(e){t(r(e))}});return i(t()),i},e.extenders.nsxDataRef=function(t,n){var r=e.pureComputed({read:function(){var e=t();return{value:e,name:e?e.name:"",id:e?e.id:0}},write:function(e){t(e)}});return r(t()),r},{asObservableDate:function(t){if(!t)return e.observable(null);var n=r(t);return e.observable(n)},fromObservableDate:function(e){return e()?u(e()):null},valueFieldAsObservable:function(t){return t?e.observable(t.value):e.observable()}}}));