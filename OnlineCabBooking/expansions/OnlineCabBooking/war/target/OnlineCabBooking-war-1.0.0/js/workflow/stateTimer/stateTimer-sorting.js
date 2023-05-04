// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var SortableColumn = require('nsx/list/sortable-column');
  var Trigger = require('nsx/triggers');
  var utils = require('nsx/util/utils');
  var StateTimerEvents = require('workflow/stateTimer/stateTimer-events');

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

    viewModel.processor = SortableColumn.defineSortableColumn({
      fieldName: "processor"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "processor";
        }
      })
    });

    viewModel.implementation = SortableColumn.defineSortableColumn({
      fieldName: "implementation"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "implementation";
        }
      })
    });

    viewModel.params = SortableColumn.defineSortableColumn({
      fieldName: "params"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "params";
        }
      })
    });

    viewModel.beginState = SortableColumn.defineSortableColumn({
      fieldName: "beginState"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "beginState";
        }
      })
    });

    viewModel.targetState = SortableColumn.defineSortableColumn({
      fieldName: "targetState"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "targetState";
        }
      })
    });

    viewModel.alteredState = SortableColumn.defineSortableColumn({
      fieldName: "alteredState"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "alteredState";
        }
      })
    });

    viewModel.allowedPeriod = SortableColumn.defineSortableColumn({
      fieldName: "allowedPeriod"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "allowedPeriod";
        }
      })
    });

    viewModel.requiredAction = SortableColumn.defineSortableColumn({
      fieldName: "requiredAction"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "requiredAction";
        }
      })
    });

    // anchor:sorting-columns:end
    // anchor:sorting-triggers:start
    selectSorting.addTrigger(viewModel.id.select);
    selectSorting.addTrigger(viewModel.name.select);
    selectSorting.addTrigger(viewModel.processor.select);
    selectSorting.addTrigger(viewModel.implementation.select);
    selectSorting.addTrigger(viewModel.params.select);
    selectSorting.addTrigger(viewModel.beginState.select);
    selectSorting.addTrigger(viewModel.targetState.select);
    selectSorting.addTrigger(viewModel.alteredState.select);
    selectSorting.addTrigger(viewModel.allowedPeriod.select);
    selectSorting.addTrigger(viewModel.requiredAction.select);
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

    var finderUpdate = StateTimerEvents.defineFinderUpdateTrigger();
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
