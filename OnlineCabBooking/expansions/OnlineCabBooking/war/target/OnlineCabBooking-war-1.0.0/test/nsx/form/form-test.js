define([
  "nsx/form/form-model",
  'nsx/field/field-model',
  "nsx/field/field-type",
  "view/src/ts/nsx/form/form-error-extender",
  "view/src/ts/nsx/form/form-layout-extender",
  "knockout"
], function (Form, Field, FieldType, FormError, FormLayout, ko) {
  describe("form-model", function () {
    var data;
    var form;

    beforeEach(function () {
      data = ko.observable();
      form = Form.defineFormModel({
        fields: [
          {
            fieldName: "alpha",
            key: "testComp.test.alpha",
            type: FieldType.VALUE
          },
          {
            fieldName: "beta",
            key: "testComp.test.beta",
            type: FieldType.VALUE
          }
        ],
        data: data
      }, {});
    });

    it("Should be able to receive new fields", function () {
      expect(form.alpha).toBeDefined();
      expect(form.beta).toBeDefined();

      expect(form.fields).toContain(form.alpha);
      expect(form.fields).toContain(form.beta);
    });

    it("Should be extendable with error model", function () {
      FormError.extendForm({
        model: form,
        fieldErrors: ko.observable()
      }, {});

      expect(form.alpha.errorMessages).toBeDefined();
      expect(form.alpha.hasErrors).toBeDefined();
      expect(form.beta.errorMessages).toBeDefined();
      expect(form.beta.hasErrors).toBeDefined();
    });

    it("Should be extendable with layout model", function () {
      FormLayout.extendForm({
        model: form,
        config: {
          alpha: {
            visible: true,
            enabled: false
          },
          beta: {
            visible: false
          }
        }
      }, {});

      expect(form.alpha.visible).toBeDefined();
      expect(form.alpha.enabled).toBeDefined();
      expect(form.alpha.enabled()).toBeFalsy();

      expect(form.beta.visible).toBeDefined();
      expect(form.beta.enabled).toBeDefined();
      expect(form.beta.visible()).toBeFalsy();
    });

    it("Should update on data change", function () {
      data({
        alpha: 1,
        beta: 2
      });


      expect(form.alpha.value()).toBe(1);
      expect(form.beta.value()).toBe(2);
    });
  })
});