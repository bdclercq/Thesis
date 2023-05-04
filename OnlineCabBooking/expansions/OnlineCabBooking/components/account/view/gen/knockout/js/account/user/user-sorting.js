// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
define(function (require) {
  var ko = require('knockout');
  var SortableColumn = require('nsx/list/sortable-column');
  var Trigger = require('nsx/triggers');
  var utils = require('nsx/util/utils');
  var UserEvents = require('account/user/user-events');

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

    viewModel.password = SortableColumn.defineSortableColumn({
      fieldName: "password"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "password";
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

    viewModel.email = SortableColumn.defineSortableColumn({
      fieldName: "email"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "email";
        }
      })
    });

    viewModel.mobile = SortableColumn.defineSortableColumn({
      fieldName: "mobile"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "mobile";
        }
      })
    });

    viewModel.language = SortableColumn.defineSortableColumn({
      fieldName: "language"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "language";
        }
      })
    });

    viewModel.firstName = SortableColumn.defineSortableColumn({
      fieldName: "firstName"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "firstName";
        }
      })
    });

    viewModel.lastName = SortableColumn.defineSortableColumn({
      fieldName: "lastName"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "lastName";
        }
      })
    });

    viewModel.persNr = SortableColumn.defineSortableColumn({
      fieldName: "persNr"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "persNr";
        }
      })
    });

    viewModel.lastModifiedAt = SortableColumn.defineSortableColumn({
      fieldName: "lastModifiedAt"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "lastModifiedAt";
        }
      })
    });

    viewModel.enteredAt = SortableColumn.defineSortableColumn({
      fieldName: "enteredAt"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "enteredAt";
        }
      })
    });

    viewModel.disabled = SortableColumn.defineSortableColumn({
      fieldName: "disabled"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "disabled";
        }
      })
    });

    viewModel.timeout = SortableColumn.defineSortableColumn({
      fieldName: "timeout"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "timeout";
        }
      })
    });

    viewModel.encryptedPassword = SortableColumn.defineSortableColumn({
      fieldName: "encryptedPassword"
    }, {
      resetOn: Trigger.defineFilter({
        trigger: selectSorting,
        condition: function (sortField) {
          return sortField === null || sortField.fieldName !== "encryptedPassword";
        }
      })
    });

    // anchor:sorting-columns:end
    // anchor:sorting-triggers:start
    selectSorting.addTrigger(viewModel.id.select);
    selectSorting.addTrigger(viewModel.name.select);
    selectSorting.addTrigger(viewModel.password.select);
    selectSorting.addTrigger(viewModel.fullName.select);
    selectSorting.addTrigger(viewModel.email.select);
    selectSorting.addTrigger(viewModel.mobile.select);
    selectSorting.addTrigger(viewModel.language.select);
    selectSorting.addTrigger(viewModel.firstName.select);
    selectSorting.addTrigger(viewModel.lastName.select);
    selectSorting.addTrigger(viewModel.persNr.select);
    selectSorting.addTrigger(viewModel.lastModifiedAt.select);
    selectSorting.addTrigger(viewModel.enteredAt.select);
    selectSorting.addTrigger(viewModel.disabled.select);
    selectSorting.addTrigger(viewModel.timeout.select);
    selectSorting.addTrigger(viewModel.encryptedPassword.select);
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

    var finderUpdate = UserEvents.defineFinderUpdateTrigger();
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
