define(['nsx/triggers'], function (Trigger){
  describe("simple-trigger", function () {
    it("Should call subscribed functions when triggered once", function () {
      var trigger = Trigger.defineTrigger();
      var count = 0;

      function increment() {
        count++;
      }

      trigger.subscribe(increment);
      trigger.trigger();

      expect(count).toBe(1);
    });
    it("Should repeatedly call subscribed functions when triggered multiple times", function () {
      var trigger = Trigger.defineTrigger();
      var count = 0;

      function increment() {
        count++;
      }

      trigger.subscribe(increment);
      trigger.trigger();
      trigger.trigger();

      expect(count).toBe(2);
    });
    it("Should call ALL subscribed functions when triggered", function () {
      var trigger = Trigger.defineTrigger();
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
    })
  });
});
