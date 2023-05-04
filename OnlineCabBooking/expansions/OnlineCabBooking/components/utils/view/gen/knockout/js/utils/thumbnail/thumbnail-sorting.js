// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var SortableColumn = require('nsx/list/sortable-column');
  var Trigger = require('nsx/triggers');
  var utils = require('nsx/util/utils');
  var ThumbnailEvents = require('utils/thumbnail/thumbnail-events');

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

    viewModel.fullName = SortableColumn.defineSortableColumn({
      fieldName: "fullName"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "fullName";
        }
      })
    });

    viewModel.uri = SortableColumn.defineSortableColumn({
      fieldName: "uri"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "uri";
        }
      })
    });

    viewModel.depth = SortableColumn.defineSortableColumn({
      fieldName: "depth"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "depth";
        }
      })
    });

    viewModel.border = SortableColumn.defineSortableColumn({
      fieldName: "border"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "border";
        }
      })
    });

    viewModel.thumbType = SortableColumn.defineSortableColumn({
      fieldName: "thumbType"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "thumbType";
        }
      })
    });

    viewModel.thumbName = SortableColumn.defineSortableColumn({
      fieldName: "thumbName"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "thumbName";
        }
      })
    });

    viewModel.targetType = SortableColumn.defineSortableColumn({
      fieldName: "targetType"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "targetType";
        }
      })
    });

    viewModel.targetName = SortableColumn.defineSortableColumn({
      fieldName: "targetName"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "targetName";
        }
      })
    });

    viewModel.targetId = SortableColumn.defineSortableColumn({
      fieldName: "targetId"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "targetId";
        }
      })
    });

    viewModel.leftX = SortableColumn.defineSortableColumn({
      fieldName: "leftX"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "leftX";
        }
      })
    });

    viewModel.topY = SortableColumn.defineSortableColumn({
      fieldName: "topY"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "topY";
        }
      })
    });

    viewModel.width = SortableColumn.defineSortableColumn({
      fieldName: "width"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "width";
        }
      })
    });

    viewModel.height = SortableColumn.defineSortableColumn({
      fieldName: "height"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "height";
        }
      })
    });

    viewModel.clickAction = SortableColumn.defineSortableColumn({
      fieldName: "clickAction"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "clickAction";
        }
      })
    });

    viewModel.hooverAction = SortableColumn.defineSortableColumn({
      fieldName: "hooverAction"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "hooverAction";
        }
      })
    });

    // anchor:sorting-columns:end
    // anchor:sorting-triggers:start
    selectSorting.addTrigger(viewModel.id.select);
    selectSorting.addTrigger(viewModel.name.select);
    selectSorting.addTrigger(viewModel.fullName.select);
    selectSorting.addTrigger(viewModel.uri.select);
    selectSorting.addTrigger(viewModel.depth.select);
    selectSorting.addTrigger(viewModel.border.select);
    selectSorting.addTrigger(viewModel.thumbType.select);
    selectSorting.addTrigger(viewModel.thumbName.select);
    selectSorting.addTrigger(viewModel.targetType.select);
    selectSorting.addTrigger(viewModel.targetName.select);
    selectSorting.addTrigger(viewModel.targetId.select);
    selectSorting.addTrigger(viewModel.leftX.select);
    selectSorting.addTrigger(viewModel.topY.select);
    selectSorting.addTrigger(viewModel.width.select);
    selectSorting.addTrigger(viewModel.height.select);
    selectSorting.addTrigger(viewModel.clickAction.select);
    selectSorting.addTrigger(viewModel.hooverAction.select);
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

    var finderUpdate = ThumbnailEvents.defineFinderUpdateTrigger();
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
