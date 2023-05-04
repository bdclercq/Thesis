define([
  'nsx/injectors/id-generator'
], function (IdGen) {
  describe("Id Generator", function () {
    var root;

    beforeEach(function () {
      root = IdGen.defineDomain({
        name: "test"
      })
    });

    it("should generate an id with the given key", function () {
      var id = root.generateId("alpha");

      expect(id).toContain("test");
      expect(id).toContain("alpha");
    });

    it("should keep apart subdomains", function () {
      var jsDom = root.subDomain("javascript");
      var htmlDom = root.subDomain("html");

      var alphaJs = jsDom.generateId("alpha");
      var alphaHtml = htmlDom.generateId("alpha");

      expect(alphaJs).not.toBe(alphaHtml);
    })
  })
});