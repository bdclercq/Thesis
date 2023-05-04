define([
  'nsx/field/field-model',
  'view/src/ts/nsx/field/field-error-extender',
  'view/src/ts/nsx/field/field-layout-extender',
  'knockout'
], function (FieldModel, FieldError, FieldLayout, ko) {
  describe('field-model', function () {
    var field;

    beforeEach(function () {
      var options = {};
      field = FieldModel.defineField({
        fieldName: "test",
        key: "test.key",
        isValueType: false
      }, options);
    });

    it('Should be extendable', function () {
      var extender = {
        a: 15,
        b: "d",
        c: function () {}
      };

      var extendedModel = field.extendWith(extender);

      expect(extendedModel.a).toBe(15);
      expect(extendedModel.b).toBe("d");
      expect(extendedModel.c).toBe(extender.c);
    });

    describe('Error model extension', function () {
      it('Should add errorMessages and hasError', function () {
        var extendedModel = FieldError.extendField({
          model: field,
          fieldErrors: ko.observable()
        }, {});

        expect(extendedModel.errorMessages()).toEqual([]);
        expect(extendedModel.hasErrors()).toBeFalsy();
      });

      it('Should update errorMessages on new errors', function () {
        var options = {};
        var fieldErrors = ko.observable();

        var field = FieldModel.defineField({
          fieldName: "test",
          key: "test.key",
          isValueType: false
        }, options);
        var extendedModel = FieldError.extendField({
          model: field,
          fieldErrors: fieldErrors
        }, {});

        fieldErrors({
          "test": ["error1", "error2"]
        });

        expect(extendedModel.errorMessages()).toEqual(["error1", "error2"]);
        expect(extendedModel.hasErrors()).toBeTruthy();
      });

      it('Should clear errors on a value change', function () {
        var options = {};
        var fieldErrors = ko.observable();

        var field = FieldModel.defineField({
          fieldName: "test",
          key: "test.key",
          isValueType: false
        }, options);
        var extendedModel = FieldError.extendField({
          model: field,
          fieldErrors: fieldErrors
        }, {});

        extendedModel.errorMessages(["error", "error"]);
        extendedModel.hasErrors(false);

        field.value("newValue");

        expect(extendedModel.errorMessages()).toEqual([]);
        expect(extendedModel.hasErrors()).toBeFalsy();
      });
    });
    describe('Layout model extension', function () {
      it('Should add visible, hidden, enabled and disabled', function () {
        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {}
        }, {});

        expect(extendedModel.visible).toBeDefined();
        expect(extendedModel.hidden).toBeDefined();
        expect(extendedModel.enabled).toBeDefined();
        expect(extendedModel.disabled).toBeDefined();
      });
      it('Should be visible and enabled by default', function () {
        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {}
        }, {});

        expect(extendedModel.visible()).toBeTruthy();
        expect(extendedModel.enabled()).toBeTruthy();
        expect(extendedModel.hidden()).toBeFalsy();
        expect(extendedModel.disabled()).toBeFalsy();
      });
      it('Should be visible and enabled when configured', function () {
        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {
            visible: true,
            enabled: true
          }
        }, {});

        expect(extendedModel.visible()).toBeTruthy();
        expect(extendedModel.enabled()).toBeTruthy();
        expect(extendedModel.hidden()).toBeFalsy();
        expect(extendedModel.disabled()).toBeFalsy();
      });
      it('Should be hidden and disabled when configured', function () {
        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {
            hidden: true,
            disabled: true
          }
        }, {});

        expect(extendedModel.hidden()).toBeTruthy("hidden should be true");
        expect(extendedModel.disabled()).toBeTruthy("disabled should be true");
        expect(extendedModel.visible()).toBeFalsy("visible should be false");
        expect(extendedModel.enabled()).toBeFalsy("enabled should be false");
      });
      it('Should accept negation of visible and enabled', function () {
        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {
            visible: false,
            enabled: false
          }
        }, {});

        expect(extendedModel.hidden()).toBeTruthy("hidden should be true");
        expect(extendedModel.disabled()).toBeTruthy("disabled should be true");
        expect(extendedModel.visible()).toBeFalsy("visible should be false");
        expect(extendedModel.enabled()).toBeFalsy("enabled should be false");
      });
      it('Should accept negation of hidden and disabled', function () {
        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {
            hidden: false,
            disabled: false
          }
        }, {});

        expect(extendedModel.visible()).toBeTruthy("visible should be true");
        expect(extendedModel.enabled()).toBeTruthy("enabled should be true");
        expect(extendedModel.hidden()).toBeFalsy("hidden should be false");
        expect(extendedModel.disabled()).toBeFalsy("disabled should be false");
      });
      it('Should check for conflicts', function () {
        function messUpVisibleConfig() {
          FieldLayout.extendField({
            model: field,
            config: {
              visible: true,
              hidden: true
            }
          }, {});
        }

        function messUpEnableConfig() {
          FieldLayout.extendField({
            model: field,
            config: {
              enabled: true,
              disabled: true
            }
          }, {});
        }

        expect(messUpVisibleConfig).toThrowError(Error, /conflict/);
        expect(messUpEnableConfig).toThrowError(Error, /conflict/);
      });
      it('Should accept observables', function () {
        /**
         * @type {KnockoutObservable<boolean>}
         */
        var isVisible = ko.observable(false);

        var extendedModel = FieldLayout.extendField({
          model: field,
          config: {
            visible: isVisible
          }
        }, {});


        expect(extendedModel.visible()).toBeFalsy("visible should be false");
        expect(extendedModel.hidden()).toBeTruthy("hidden should be true");

        isVisible(true);

        expect(extendedModel.visible()).toBeTruthy("visible should be true");
        expect(extendedModel.hidden()).toBeFalsy("hidden should be false");
      });
    });
  });
});