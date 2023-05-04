var jasmineRoot = '../js/jasmine/lib/jasmine-2.5.0';

require.config({
  baseUrl: "../js",
  paths: {
    test: ['../test'],
    jasmine: [jasmineRoot + '/jasmine'],
    jasmine_html: [jasmineRoot + '/jasmine-html'],
    jasmine_boot: [jasmineRoot + '/boot'],
    jquery:                   'jquery/jquery-1.10.2.min',
    bootstrapjs:              '../styles/bootstrap/js/bootstrap.min',
    knockout:                 'lib/knockout-3.2.0',
    knockoutjs:               'nsx/knockout-redirect',
    knockoutamd:              'lib/knockout-amd-helpers',
    bindings:                 'nsx/ko-bindings',
    bootstrap_dateTimePicker: 'bootstrap-datetimepicker/bootstrap-datetimepicker.min',
    text:                     'require/text',
    dataElement:              'nsx/metadata/require-dataElement',
    templates:                '../templates',
    moment:                   'lib/moment',
    pubsub:                   'lib/pubsub',
    'pubsub-js':              'lib/pubsub',
    html:                     '../html',
    json:                     '../json'
  },
  shim: {
    jasmine_html: {
      deps : ['jasmine']
    },
    jasmine_boot: {
      deps : ['jasmine', 'jasmine_html']
    }
  }
});

require(['jasmine_boot', 'knockout', 'knockoutamd'], function () {
  require([
    'nsx/nsx-modules',
    'nsx/nsx-knockout-bindings',
    'test/nsx/trigger/simple-trigger-test',
    'test/nsx/trigger/continuing-trigger-test',
    'test/nsx/trigger/selection-trigger-test',
    'test/nsx/trigger/event-trigger-test',
    'test/nsx/trigger/trigger-syntax-test',
    'test/nsx/field/field-test',
    'test/nsx/form/form-test',
    'test/nsx/error/error-tests',
    'test/nsx/injectors/id-generator-test',
    'test/nsx/injectors/injector-test',
    'test/nsx/utils/text-utils-tests',
    'test/nsx/utils/utils-tests'
  ], function(){
    //trigger Jasmine
    window.onload();
  })
});
