"use strict";define(["jquery","knockout","nsx/nsx-translation"],(function(n,t,a){t.bindingHandlers.translate={init:function(e,i){var u=t.utils.unwrapObservable(i());function s(t){n(e).text(t)}function r(n,t){a.getTranslation(n,s,t)}"string"==typeof u?r(u,null):(r(u.key,t.unwrap(u.language)),t.isSubscribable(u.language)&&u.language.subscribe((function(n){r(u.key,n)})))}}}));