define(["knockout", "nsx/util/text-utils"], function (ko, textUtils) {
  describe("Text Utils", function () {
    describe("format()", function () {
      it("should fill in placeholders", function () {
        var result = textUtils.format("Hello {name}!", {
          name: "world"
        });
        expect(result()).toBe("Hello world!");
      });

      it("should replace ALL placeholders", function () {
        var result = textUtils.format("{action1}, {action1}, {action1}, {action2}", {
          action1: "test",
          action2: "repeat"
        });
        expect(result()).toBe("test, test, test, repeat");
      });

      it("should throw a fit on an unknown placeholder", function () {
        function useUnknownPlaceholder() {
          textUtils.format("{first}, {second}, {third}", {
            first: "apples",
            second: "pears"
          })();
        }
        expect(useUnknownPlaceholder).toThrowError(new RegExp("third"));
      });

      it("should allow array notation", function () {
        var result = textUtils.format("{0}, {1}, {2}", ["apples", "pears", "oranges"]);
        expect(result()).toBe("apples, pears, oranges");
      });

      it("should react to changes in observables", function () {
        var name = ko.observable("world");

        var result = textUtils.format("Hello {name}!", {
          name: name
        });

        name("universe")

        expect(result()).toBe("Hello universe!");
      });
    });
  });
});