// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var SortableColumn = require('nsx/list/sortable-column');
  var Trigger = require('nsx/triggers');
  var utils = require('nsx/util/utils');
  var AddressEvents = require('onlineCabBookingComp/address/address-events');

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

    viewModel.state = SortableColumn.defineSortableColumn({
      fieldName: "state"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "state";
        }
      })
    });

    viewModel.city = SortableColumn.defineSortableColumn({
      fieldName: "city"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "city";
        }
      })
    });

    viewModel.pincode = SortableColumn.defineSortableColumn({
      fieldName: "pincode"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "pincode";
        }
      })
    });

    viewModel.street = SortableColumn.defineSortableColumn({
      fieldName: "street"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "street";
        }
      })
    });

    viewModel.houseNumber = SortableColumn.defineSortableColumn({
      fieldName: "houseNumber"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "houseNumber";
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

    // anchor:sorting-columns:end
    // anchor:sorting-triggers:start
    selectSorting.addTrigger(viewModel.id.select);
    selectSorting.addTrigger(viewModel.state.select);
    selectSorting.addTrigger(viewModel.city.select);
    selectSorting.addTrigger(viewModel.pincode.select);
    selectSorting.addTrigger(viewModel.street.select);
    selectSorting.addTrigger(viewModel.houseNumber.select);
    selectSorting.addTrigger(viewModel.name.select);
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

    var finderUpdate = AddressEvents.defineFinderUpdateTrigger();
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
