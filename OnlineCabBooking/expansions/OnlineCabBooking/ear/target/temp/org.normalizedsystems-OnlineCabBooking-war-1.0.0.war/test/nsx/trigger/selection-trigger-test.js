define(['nsx/triggers', 'knockout'], function (Trigger, ko){
  describe("selection-trigger", function () {
    it("Should pass source value when triggered", function () {
      var source = ko.observable(5);
      var trigger = Trigger.defineSelectionTrigger({ source: source });
      var value = 0;


      function setValue(newValue) {
        value = newValue;
      }

      trigger.subscribe(setValue);
      trigger.trigger();

      expect(value).toBe(5);
    });

    it("Should pass CURRENT source value when triggered", function () {
      var source = ko.observable(5);
      var trigger = Trigger.defineSelectionTrigger({ source: source });
      var value = 0;


      function setValue(newValue) {
        value = newValue;
      }

      trigger.subscribe(setValue);
      source(10);
      trigger.trigger();

      expect(value).toBe(10);
    });
  });
});
