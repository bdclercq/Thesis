// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:web-styles:2022.4.1
(function () {
  // work around for internet explorer
  if (!window.console) {
    window.console = {
      log: function () {
      }
    }
  }

  var config = {
    urlArgs: 'v=${build.cache.version}',
    baseUrl: '/OnlineCabBooking/js/',
    paths: {
      // anchor:paths:start
      'html': '../html',
      'json': '../json',
      'templates': '../templates',
      'text': 'require/text',
      // anchor:paths:end
      // anchor:paths-technologies:start
      'knockout': 'lib/knockout/build/output/knockout-latest',
      'knockoutjs': 'nsx/knockout-redirect',
      'knockoutamd': 'lib/knockout-amd-helpers',
      'jquery': 'lib/jquery/dist/jquery.min',
      'bootstrapjs': '../styles/bootstrap/js/bootstrap.min',
      'moment': 'lib/moment/min/moment.min',
      'pubsub': 'lib/pubsub-js/src/pubsub',
      'mousetrap': 'lib/mousetrap/mousetrap.min',
      'mousetrap-global': 'lib/mousetrap/plugins/global-bind/mousetrap-global-bind.min',
      'bootstrap_dateTimePicker': 'bootstrap-datetimepicker/bootstrap-datetimepicker.min',
      'pubsub-js': 'lib/pubsub-js/src/pubsub',
      // anchor:paths-technologies:end
      // anchor:paths-knockout:start
      'bindings': 'nsx/ko-bindings/',
      'dataElement': 'nsx/metadata/require-dataElement',
      // anchor:paths-knockout:end
      // @anchor:paths:start
      // @anchor:paths:end
      // anchor:custom-paths:start
      // anchor:custom-paths:end
     },

    shim: {
      'bootstrapjs': {
        deps: ['jquery'],
        exports: '$.fn.popover'
      },
      'bootstrap_dateTimePicker': {
        deps: ['jquery'],
        exports: '$.fn.datetimepicker'
      },
      'mousetrap-global': {
        deps: ['mousetrap']
      }
    }
  };

  // @anchor:config:start
  // @anchor:config:end
  // anchor:custom-config:start
  // anchor:custom-config:end

  require.config(config);
}());
