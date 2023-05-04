// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var Trigger = require('nsx/triggers');

  var ListView;
  var ListModel = require('nsx/list/standard-paged-list');
  var MenuSorting = require('account/menu/menu-sorting');
  var MenuInfoModel = require('account/menu/menu-info-instance-model');
  var MenuEvents = require('account/menu/menu-events');

  // @anchor:variables:start
  ListView = require('nsx/list/standard-list-view');
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineTable(page, input) {
    var sorting = MenuSorting.defineSorting();
    var dataUpdate = MenuEvents.defineUpdateListener();

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
    // anchor:custom-options:end

    var listModel = ListModel.defineListModel({
      searchMethod: searchMethod,
      searchDetails: searchDetails
    }, options);

    var listView = ListView.defineListView({
      page: listModel.page,
      instanceView: MenuInfoModel
    });

    var infoListView = "account/menu-info-list"
    var viewModel = {
      heading: sorting,
      list: listView
    };
    // @anchor:list:start
    // @anchor:list:end
    // anchor:custom-list:start
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
    // anchor:custom-table-model:end
    return tableModel;
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineTable = defineTable;
  return exports;
});
