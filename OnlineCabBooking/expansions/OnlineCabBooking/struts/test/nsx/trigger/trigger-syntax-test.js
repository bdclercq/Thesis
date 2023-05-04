define([
  'nsx/triggers',
  'knockout'
], function (Trigger, ko) {
  var when = Trigger.when;

  describe("trigger-syntax", function () {
    describe("when X then do Y", function () {
      it("should activate the callback", function () {
        var trigger = Trigger.defineTrigger();
        var success = false;

        when(trigger).thenDo(function () {
          success = true;
        });

        trigger.trigger();

        expect(success).toBeTruthy();
      });
    });
    describe("when X then trigger Y", function () {
      it("should trigger another trigger", function () {
        var trigger = Trigger.defineTrigger();
        var otherTrigger = Trigger.defineTrigger();
        var success = false;

        when(trigger).thenTrigger(otherTrigger);
        when(otherTrigger).thenDo(function () {
          success = true;
        });

        trigger.trigger();

        expect(success).toBeTruthy();
      });
    });
    describe("when X then update Y", function () {
      it("should update a knockout observable", function () {
        var source = ko.observable();
        var destination = ko.observable("not updated");

        var trigger = Trigger.defineSelectionTrigger({
          source: source
        });
        when(trigger).thenUpdate(destination);

        source("updated");
        trigger.trigger();

        expect(destination()).toBe("updated");
      });
    });
    describe("when X or Y then do Z", function () {
      describe("after triggering X", function () {
        var success = false;
        beforeAll(function (done) {
          var triggerX = Trigger.defineTrigger();
          var triggerY = Trigger.defineTrigger();

          when(triggerX).or(triggerY).thenDo(function () {
            success = true;
          });

          triggerX.trigger();

          setTimeout(function () {
            done();
          }, 10);
        });
        it("should activate the callback", function () {
          expect(success).toBeTruthy();
        });
      });
      describe("after triggering Y", function () {
        var success = false;
        beforeAll(function (done) {
          var triggerX = Trigger.defineTrigger();
          var triggerY = Trigger.defineTrigger();

          when(triggerX).or(triggerY).thenDo(function () {
            success = true;
          });

          triggerY.trigger();

          setTimeout(function () {
            done();
          }, 10);
        });
        it("should activate the callback", function () {
          expect(success).toBeTruthy();
        });
      });
    });
    describe("when X and Y then do Z", function () {
      describe("after triggering X", function () {
        var success = false;
        beforeAll(function (done) {
          var triggerX = Trigger.defineTrigger();
          var triggerY = Trigger.defineTrigger();

          when(triggerX).and(triggerY).thenDo(function () {
            success = true;
          });

          triggerX.trigger();

          setTimeout(function () {
            done();
          }, 10);
        });
        it("should not activate the callback", function () {
          expect(success).toBeFalsy();
        });
      });
      describe("after triggering Y", function () {
        var success = false;
        beforeAll(function (done) {
          var triggerX = Trigger.defineTrigger();
          var triggerY = Trigger.defineTrigger();

          when(triggerX).and(triggerY).thenDo(function () {
            success = true;
          });

          triggerY.trigger();

          setTimeout(function () {
            done();
          }, 10);
        });
        it("should not activate the callback", function () {
          expect(success).toBeFalsy();
        });
      });
      describe("after triggering X AND Y", function () {
        var success = false;
        beforeAll(function (done) {
          var triggerX = Trigger.defineTrigger();
          var triggerY = Trigger.defineTrigger();

          when(triggerX).and(triggerY).thenDo(function () {
            success = true;
          });

          triggerX.trigger();
          triggerY.trigger();

          setTimeout(function () {
            done();
          }, 10);
        });
        it("should activate the callback", function () {
          expect(success).toBeTruthy();
        });
      });
    });

  });
});