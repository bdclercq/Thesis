"use strict";define(["jquery","knockout","nsx/nsx-translation"],(function(n,e,t){e.bindingHandlers.fileUpload={init:function(e,t,i,a,c){!function(e,t){var i=n("<input type='file'>");i.hide();var a=n("<a href='#' class='btn'>Select ...</a>"),c=n("<label>"),l=n(e);l.append(i),l.append(a),l.append(c),a.click((function(){i.trigger("click")})),i.change((function(){var n=i.val(),e=n.lastIndexOf("\\");e>0&&(n=n.substr(e+1)),c.text(n);var a=i[0].files;a&&t(a[0])}))}(e,t())},update:function(n,e,t,i,a){}}}));