define([
  "nsx/error/error-trigger",
  "nsx/error/error-levels",
  "nsx/error/error-logging"
], function (ErrorTrigger, ErrorLevel, ErrorLogging) {
  describe("Errors", function () {
    describe("error-trigger", function () {
      describe("error levels", function () {
        var warnMessages;
        var warnOnlyMessages;
        var errorMessages;
        var fatalMessages;
        var allMessages;

        beforeAll(function (done) {
          warnMessages = [];
          warnOnlyMessages = [];
          errorMessages = [];
          fatalMessages = [];
          allMessages = [];

          var trigger = ErrorTrigger.defineTrigger({});

          ErrorLogging.defineLogging({
            level: ErrorLevel.all
          });

          ErrorTrigger.defineListener({
            level: ErrorLevel.warn
          }).subscribe(function (message) {
            warnMessages.push(message);
          });
          ErrorTrigger.defineListener({
            level: ErrorLevel.warnOnly
          }).subscribe(function (message) {
            warnOnlyMessages.push(message);
          });
          ErrorTrigger.defineListener({
            level: ErrorLevel.error
          }).subscribe(function (message) {
            errorMessages.push(message);
          });
          ErrorTrigger.defineListener({
            level: ErrorLevel.fatal
          }).subscribe(function (message) {
            fatalMessages.push(message);
          });
          ErrorTrigger.defineListener({
            level: ErrorLevel.all
          }).subscribe(function (message) {
            allMessages.push(message);
          });

          trigger.warn("warning message");
          trigger.error("error message");
          trigger.fatal("fatal message");

          setTimeout(function () {
            done();
          }, 50);
        });

        it("should trigger warn, error, fatal messages on warn", function () {
          expect(warnMessages).toContain("warning message");
          expect(warnMessages).toContain("error message");
          expect(warnMessages).toContain("fatal message");
        });
        it("should trigger only warn messages on warnOnly", function () {
          expect(warnOnlyMessages).toContain("warning message");
          expect(warnOnlyMessages).not.toContain("error message");
          expect(warnOnlyMessages).not.toContain("fatal message");
        });
        it("should trigger error, fatal messages on error", function () {
          expect(errorMessages).not.toContain("warning message");
          expect(errorMessages).toContain("error message");
          expect(errorMessages).toContain("fatal message");
        });
        it("should trigger fatal messages on fatal", function () {
          expect(fatalMessages).not.toContain("warning message");
          expect(fatalMessages).not.toContain("error message");
          expect(fatalMessages).toContain("fatal message");
        });
        it("should trigger all messages on all", function () {
          expect(allMessages).toContain("warning message");
          expect(allMessages).toContain("error message");
          expect(allMessages).toContain("fatal message");
          expect(allMessages.length).toBe(3);
        });
        it("should not trigger duplicates", function () {
          expect(warnMessages.length).toBe(3);
          expect(allMessages.length).toBe(3);
          expect(errorMessages.length).toBe(2);
          expect(warnOnlyMessages.length).toBe(1);
          expect(fatalMessages.length).toBe(1);
        });
      });
      describe("propagation", function () {
        var handledBy;

        beforeAll(function (done) {
          var level = ErrorLevel.info;
          handledBy = "none";

          ErrorTrigger.clearHandlers();
          var trigger = ErrorTrigger.defineTrigger({});

          ErrorTrigger.defineHandler({
            level: level,
            priority: 1
          }).subscribe(function () {
            handledBy = "prio 1";
            return false;
          });

          ErrorTrigger.defineHandler({
            level: level,
            priority: 2
          }).subscribe(function () {
            handledBy = "prio 2";
            return true;
          });

          ErrorTrigger.defineHandler({
            level: level,
            priority: 3
          }).subscribe(function () {
            handledBy = "prio 3";
            return true;
          });

          trigger.info("info message");

          setTimeout(function () {
            done();
          }, 50);
        });

        it("should stop propagation after a message has been handled", function () {
          expect(handledBy).toBe("prio 2");
        });
      });
      describe("listener filter", function () {
        var messagesNoFilter = [];
        var messagesPartialFilter = [];
        var messagesFullFilter = [];

        beforeAll(function (done) {
          ErrorTrigger.defineListener({
            level: ErrorLevel.all
          }).subscribe(function (message) {
            messagesNoFilter.push(message);
          });

          ErrorTrigger.defineListener({
            level: ErrorLevel.all
          }, {
            filter: "hello"
          }).subscribe(function (message) {
            messagesPartialFilter.push(message);
          });

          ErrorTrigger.defineListener({
            level: ErrorLevel.all
          }, {
            filter: "hello.world"
          }).subscribe(function (message) {
            messagesFullFilter.push(message);
          });

          ErrorTrigger.defineTrigger({
            subject: "hello.world"
          }).info("hello world");

          ErrorTrigger.defineTrigger({
            subject: "hello.mars"
          }).info("hello mars");

          ErrorTrigger.defineTrigger().info("hi everyone");

          setTimeout(function () {
            done();
          }, 50);
        });

        it("should send all messages with any subject to a trigger without filter", function () {
          expect(messagesNoFilter).toContain("hello world");
          expect(messagesNoFilter).toContain("hello mars");
          expect(messagesNoFilter).toContain("hi everyone");
        });

        it("should send all messages within a subject to a trigger with a filter of a super-subject", function () {
          expect(messagesPartialFilter).toContain("hello world");
          expect(messagesPartialFilter).toContain("hello mars");
          expect(messagesPartialFilter).not.toContain("hi everyone");
        });

        it("should send all messages of a subject to a trigger with a filter for that specific subject", function () {
          expect(messagesFullFilter).toContain("hello world");
          expect(messagesFullFilter).not.toContain("hello mars");
          expect(messagesFullFilter).not.toContain("hi everyone");
        });
      });
      describe("handler filter", function () {
        var messagesHandledByGlobal = [];
        var messagesHandledBySpecific = [];

        beforeAll(function (done) {
          ErrorTrigger.clearHandlers();
          ErrorTrigger.defineHandler({
            level: ErrorLevel.all,
            priority: 2
          }).subscribe(function (message) {
            messagesHandledByGlobal.push(message);
            return true;
          });

          ErrorTrigger.defineHandler({
            level: ErrorLevel.all,
            priority: 1
          }, {
            filter: "hello.world"
          }).subscribe(function (message) {
            messagesHandledBySpecific.push(message);
            return true;
          });


          ErrorTrigger.defineTrigger({
            subject: "hello.world"
          }).info("hello world");

          ErrorTrigger.defineTrigger({
            subject: "hello.mars"
          }).info("hello mars");

          setTimeout(function () {
            done();
          }, 50);
        });

        it("should only handle messages within the specific filter", function () {
          expect(messagesHandledBySpecific).toContain("hello world");
        });

        it("should not handle messages outside the specific filter", function () {
          expect(messagesHandledBySpecific).not.toContain("hello mars");
        });

        it("should not pass handled specific messages to other handlers", function () {
          expect(messagesHandledByGlobal).not.toContain("hello world");
        });

        it("should pass unhandled messages to other handlers", function () {
          expect(messagesHandledByGlobal).toContain("hello mars");
        });

      });
    });
  });
});