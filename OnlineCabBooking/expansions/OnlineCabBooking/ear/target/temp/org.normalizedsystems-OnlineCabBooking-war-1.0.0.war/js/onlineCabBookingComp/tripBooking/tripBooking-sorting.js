// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var SortableColumn = require('nsx/list/sortable-column');
  var Trigger = require('nsx/triggers');
  var utils = require('nsx/util/utils');
  var TripBookingEvents = require('onlineCabBookingComp/tripBooking/tripBooking-events');

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  function defineSorting(input, options) {
    options = options || {};

    var viewModel = {};
    var selectSorting = Trigger.defineBundledTrigger();
    var selectedSorting = ko.observable();

    // @anchor:sorting-columns:start
    // @anchor:sorting-columns:end
    // anchor:sorting-columns:start
    viewModel.id = SortableColumn.defineSortableColumn({
      fieldName: "id"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "id";
        }
      })
    });

    viewModel.name = SortableColumn.defineSortableColumn({
      fieldName: "name"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "name";
        }
      })
    });

    viewModel.fromDateTime = SortableColumn.defineSortableColumn({
      fieldName: "fromDateTime"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "fromDateTime";
        }
      })
    });

    viewModel.toDateTime = SortableColumn.defineSortableColumn({
      fieldName: "toDateTime"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "toDateTime";
        }
      })
    });

    viewModel.km = SortableColumn.defineSortableColumn({
      fieldName: "km"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "km";
        }
      })
    });

    viewModel.status = SortableColumn.defineSortableColumn({
      fieldName: "status"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "status";
        }
      })
    });

    // anchor:sorting-columns:end
    // anchor:sorting-triggers:start
    selectSorting.addTrigger(viewModel.id.select);
    selectSorting.addTrigger(viewModel.name.select);
    selectSorting.addTrigger(viewModel.fromDateTime.select);
    selectSorting.addTrigger(viewModel.toDateTime.select);
    selectSorting.addTrigger(viewModel.km.select);
    selectSorting.addTrigger(viewModel.status.select);
    // anchor:sorting-triggers:end
    // anchor:custom-sorting-columns:start
    // anchor:custom-sorting-columns:end

    selectSorting.subscribe(selectedSorting);

    function getSortFields() {
      // anchor:custom-get-sort-fields:start
      // anchor:custom-get-sort-fields:end
      // @anchor:get-sort-fields:start
      // @anchor:get-sort-fields:end
      return selectedSorting() ? [selectedSorting()]:[];
    }

    viewModel.sortFields = ko.pureComputed(getSortFields);
    viewModel.select = selectSorting;

    var finderUpdate = TripBookingEvents.defineFinderUpdateTrigger();
    Trigger.when(selectedSorting).thenTrigger(finderUpdate);

    // @anchor:after:start
    // @anchor:after:end
    // anchor:custom-after:start
    // anchor:custom-after:end

    return viewModel;
  }

  /** @type {Object<string,any>} */
  var exports = {};
  exports.defineSorting = defineSorting;
  return exports;
});
