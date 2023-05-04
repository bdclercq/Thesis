define(["knockout", "nsx/util/utils"], function (ko, utils) {
  describe("Utils", function () {
    describe("arrayContainsAll()", function () {
      it("should return true if all items are present", function () {
        var result = utils.arrayContainsAll(["A", "B", "C"], ["A", "B", "C"]);
        expect(result).toBeTruthy();
      });
      it("should return true if no items are given", function () {
        var result = utils.arrayContainsAll(["A", "B", "C"], []);
        expect(result).toBeTruthy();
      });
      it("should return true if more items are present in the array", function () {
        var result = utils.arrayContainsAll(["A", "B", "C", "D"], ["A", "B", "C"]);
        expect(result).toBeTruthy();
      });
      it("should return true if the order is different", function () {
        var result = utils.arrayContainsAll(["A", "B", "C"], ["B", "C", "A"]);
        expect(result).toBeTruthy();
      });
      it("should return false if an item is missing", function () {
        var result = utils.arrayContainsAll(["A", "B", "C"], ["A", "C"]);
        expect(result).toBeTruthy();
      });
    });

    describe("findMax()", function () {
      it("should return null if no item is found", function () {
        var max = utils.findMax([], function (a) {
          return 5
        });
        expect(max).toBeNull();
      })
      it("should return the largest item", function () {
        var max = utils.findMax(["a", "aaa", "aa"], function (a) {
          return a.length
        });
        expect(max).toBe("aaa");
      })
    })

    describe("findMin()", function () {
      it("should return null if no item is found", function () {
        var min = utils.findMin([], function (a) {
          return 5
        });
        expect(min).toBeNull();
      })
      it("should return the smallest item", function () {
        var min = utils.findMin(["a", "aaa", "aa"], function (a) {
          return a.length
        });
        expect(min).toBe("a");
      })
    })
  });
});