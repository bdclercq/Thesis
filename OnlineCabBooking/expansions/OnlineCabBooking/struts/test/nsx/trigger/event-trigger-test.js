define([
    'nsx/triggers'],
  function (Triggers){
    describe("event-trigger", function () {
      var listener1Result;
      var listener2Result;

      beforeAll(function (done) {
        var key = "TEST";
        listener1Result = null;
        listener2Result = null;

        var trigger = Triggers.defineEventTrigger({ key: key, source: function () { return "test123"} });
        var listener1 = Triggers.defineEventListener({ key: key });
        var listener2 = Triggers.defineEventListener({ key: "something else" });

        function register1(param) {
          listener1Result = param;
        }

        function register2(param) {
          listener2Result = param;
        }

        listener1.subscribe(register1);
        listener2.subscribe(register2);
        trigger.trigger();

        setTimeout(function () {
          done();
        }, 10);
      });

      it("Should push events to triggers with the same key", function () {
        expect(listener1Result).toEqual("test123");
      });

      it("Should not push events to triggers with a different key", function () {
        expect(listener2Result).toBeNull();
      });
    });
  });
