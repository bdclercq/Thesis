define(['nsx/triggers'], function (Trigger) {
  describe("continuing-trigger", function () {
    it("Should call subscribed functions when triggered once", function () {
      var trigger = Trigger.defineContinuingTrigger();
      var count = 0;

      function increment() {
        count++;
      }

      trigger.subscribe(increment);
      trigger.trigger();

      expect(count).toBe(1);
    });
    it("Should not call subscribed functions if not triggered", function () {
      var trigger = Trigger.defineContinuingTrigger();
      var count = 0;

      function increment() {
        count++;
      }

      trigger.subscribe(increment);

      expect(count).toBe(0);
    });

    it("Should subscribed functions once when triggered multiple times", function () {
      var trigger = Trigger.defineContinuingTrigger();
      var count = 0;

      function increment() {
        count++;
      }

      trigger.subscribe(increment);
      trigger.trigger();
      trigger.trigger();

      expect(count).toBe(1);
    });

    it("Should call ALL subscribed functions when triggered", function () {
      var trigger = Trigger.defineContinuingTrigger();
      var c1 = 0;
      var c2 = 0;

      function incrementC1() {
        c1++;
      }


      function incrementC2() {
        c2++;
      }

      trigger.subscribe(incrementC1);
      trigger.subscribe(incrementC2);
      trigger.trigger();

      expect(c1).toBe(1);
      expect(c2).toBe(1);
    });

    it("Should call subscribed functions even if subscribed after triggering", function () {
      var trigger = Trigger.defineContinuingTrigger();
      var count = 0;

      function increment() {
        count++;
      }

      trigger.trigger();
      trigger.subscribe(increment);

      expect(count).toBe(1);
    });
  });
});
