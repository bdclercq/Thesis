// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Trigger = require('nsx/triggers');

  var ListView;
  var ListModel = require('nsx/list/standard-paged-list');
  var EngineServiceSorting = require('workflow/engineService/engineService-sorting');
  var EngineServiceInfoModel = require('workflow/engineService/engineService-info-instance-model');
  var EngineServiceEvents = require('workflow/engineService/engineService-events');

  // @anchor:variables:start
  ListView = require('nsx/list/standard-list-view');
  // @anchor:variables:end
  // anchor:custom-variables:start
  EngineServiceInfoModel = require('workflow/engineService/engineService-runningState-instance-model');
  // anchor:custom-variables:end

  function defineTable(page, input) {
    var sorting = EngineServiceSorting.defineSorting();
    var dataUpdate = EngineServiceEvents.defineUpdateListener();

    var options = {
      pageSize: 7,
      reloadOn: dataUpdate,
      sorting: sorting
    };
    var pageSegmentOptions = {
      wrapWith: "nsx/bootstrap/table",
      visible: true
    };
    var searchMethod = input.searchMethod;
    var searchDetails = input.searchDetails;
    // @anchor:options:start
    // @anchor:options:end
    // anchor:custom-options:start
    options.projection = "runningState";
    // anchor:custom-options:end

    var listModel = ListModel.defineListModel({
      searchMethod: searchMethod,
      searchDetails: searchDetails
    }, options);

    var listView = ListView.defineListView({
      page: listModel.page,
      instanceView: EngineServiceInfoModel
    });

    var infoListView = "workflow/engineService-info-list"
    var viewModel = {
      heading: sorting,
      list: listView
    };
    // @anchor:list:start
    // @anchor:list:end
    // anchor:custom-list:start
    infoListView = "workflow/engineService-runningState-list";
    // anchor:custom-list:end

    page.definePageSegment({
      selector: "list",
      view: infoListView,
      viewModel: viewModel
    }, pageSegmentOptions);

    var tableModel = {
      listModel: listModel,
      selection: listView.selection,
      links: listView.links
    };
    // @anchor:table-model:start
    // @anchor:table-model:end
    // anchor:custom-table-model:start
    listView.items.subscribe(function (items) {
      if (items.length === 1) {
        var item = items[0];
        if (!item.isSelected()) {
          item.select.trigger();
        }
      }
    });
    // anchor:custom-table-model:end
    return tableModel;
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineTable = defineTable;
  return exports;
});
