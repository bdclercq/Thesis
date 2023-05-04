define([
  "knockout",
  "jquery",
  "nsx/injectors/page-segment",
  "nsx/injectors/tabs",
  "nsx/injectors/plot",
  "nsx/plot/pie-chart",
  "nsx/triggers"
], function (ko, $, Page, Tabs, Plot, PieChart, Trigger) {
  var maxRenderTime = 100;

  function clearContent() {
    $("div#content").replaceWith("<content/>");
  }

  describe("Injectors", function () {
    describe("Page segment injector", function () {
      describe("root injector", function () {
        beforeEach(function () {
          clearContent();
        });
        describe("view input", function () {
          beforeEach(function (done) {
            Page.definePageSegment({
              view: "nsx/templates/classic",
              viewModel: {},
              selector: "content"
            });

            setTimeout(function () {
              done();
            }, maxRenderTime);
          });
          it("should inject a html template into the correct tag", function () {
            expect($("div#content list").length).toBe(1);
          });
        });
        describe("viewModel input", function () {
          beforeEach(function (done) {
            var text = ko.observable();
            var pageSegment = Page.definePageSegment({
              view: "nsx/knockout/simpleText",
              viewModel: {
                text: text
              },
              selector: "content"
            });
            text("Hello World!");

            pageSegment.rendered.subscribe(function () {
              done();
            });
          });
          it("should bind the viewmodel to the widget", function () {
            expect($("div#content [data-bind='text: text']").text()).toBe("Hello World!");
          });
        });
        describe("wrapWith option", function () {
          beforeEach(function (done) {
            var pageSegment = Page.definePageSegment({
              view: "nsx/templates/classic",
              viewModel: {},
              selector: "content"
            }, {
              wrapWith: "nsx/bootstrap/well"
            });

            pageSegment.rendered.subscribe(function () {
              done();
            });
          });
          it("should wrap the widget with the wrapper template", function () {
            expect($("div#content .well list").length).toBe(1);
          });
        });
        describe("visible option - false", function () {
          beforeEach(function (done) {
            var visible = ko.observable(true);
            var pageSegment = Page.definePageSegment({
              view: "nsx/templates/classic",
              viewModel: {},
              selector: "content"
            }, {
              visible: visible
            });

            visible(false);
            pageSegment.rendered.subscribe(function () {
              done();
            });
          });
          it("should hide the widget when visible=false", function () {
            expect($("div#content [style*='display: none']").length).toBe(1);
          });
        });
        describe("visible option - true", function () {
          beforeEach(function (done) {
            var visible = ko.observable(true);
            var pageSegment = Page.definePageSegment({
              view: "nsx/templates/classic",
              viewModel: {},
              selector: "content"
            }, {
              visible: visible
            });

            visible(true);
            pageSegment.rendered.subscribe(function () {
              done();
            });
          });
          it("should show the widget when visible=true", function () {
            expect($("div#content [style*='display: none']").length).toBe(0);
          });
        });
      });
      describe("nested injector", function () {
        var content;
        beforeEach(function (done) {
          clearContent();

          content = Page.definePageSegment({
            view: "nsx/templates/classic",
            viewModel: {},
            selector: "content"
          });

          setTimeout(function () {
            done();
          }, maxRenderTime);
        });
        describe("view input", function () {
          beforeEach(function (done) {
            var pageSegment = content.definePageSegment({
              view: "nsx/templates/classic",
              viewModel: {},
              selector: "list"
            });

            pageSegment.rendered.subscribe(function () {
              done();
            });
          });
          it("should generate a unique Id for the injected widget", function () {
            expect($("div#content div#content-list").length).toBe(1);
          });
          it("should inject a html template into the correct tag", function () {
            expect($("div#content-list list").length).toBe(1);
          });
        });
        describe("viewModel input", function () {
          beforeEach(function (done) {
            var text = ko.observable();
            var pageSegment = content.definePageSegment({
              view: "nsx/knockout/simpleText",
              viewModel: {
                text: text
              },
              selector: "details"
            });
            text("Hello World!");

            pageSegment.rendered.subscribe(function () {
              done();
            });
          });
          it("should bind the viewmodel to the widget", function () {
            expect($("div#content div#content-details [data-bind='text: text']").text()).toBe("Hello World!");
          });
        });
      })
    });
    describe("Tabs injector", function () {
      describe("root tabs", function () {
        beforeEach(function () {
          clearContent();
        });
        describe("single tab", function () {
          beforeEach(function (done) {
            var tabs = Tabs.defineTabs({
              selector: "content"
            });
            var tab = tabs.defineTab({
              name: "First Tab",
              view: "nsx/templates/classic",
              viewModel: {}
            });
            tab.rendered.subscribe(function () {
              done();
            });
          });
          it("should render a single tab", function () {
            expect($("div#content .nsx-tab").length).toBe(1);
          });
          it("should generate a unique Id for the rooted tab", function () {
            expect($("div#content div#content-tab1.nsx-tab").length).toBe(1);
          });
          it("should render the html within that tab", function () {
            expect($("div#content-tab1 list").length).toBe(1);
          });
        });
        describe("multiple tabs", function () {
          beforeEach(function (done) {
            var tabs = Tabs.defineTabs({
              selector: "content"
            });
            var tab1 = tabs.defineTab({
              name: "First Tab",
              view: "nsx/templates/classic",
              viewModel: {}
            });
            var tab2 = tabs.defineTab({
              name: "Second Tab",
              view: "nsx/knockout/simpleText",
              viewModel: {
                text: "Ok!"
              }
            });
            Trigger
              .when(tab1.rendered)
              .and(tab2.rendered)
              .thenDo(function () {
                done();
              })
          });
          it("should render two tab", function () {
            expect($("div#content .nsx-tab").length).toBe(2);
          });
          it("should generate a unique Id for each rooted tab", function () {
            expect($("div#content div#content-tab1.nsx-tab").length).toBe(1);
            expect($("div#content div#content-tab2.nsx-tab").length).toBe(1);
          });
          it("should render the html within those tabs", function () {
            expect($("div#content-tab1 list").length).toBe(1);
            expect($("div#content-tab2 div[data-bind='text: text']").length).toBe(1);
          });
        });
      });

      describe("tabs inside page segment", function () {
        beforeAll(function (done) {
          clearContent();
          var content = Page.definePageSegment({
            view: "nsx/templates/classic",
            viewModel: {},
            selector: "content"
          });

          var tabs = content.defineTabs({
            selector: "details"
          });
          var tab = tabs.defineTab({
            name: "First Tab",
            view: "nsx/templates/classic",
            viewModel: {}
          });
          tab.rendered.subscribe(function () {
            done();
          });
        });

        it("should render a single tab", function () {
          expect($("div#content-details .nsx-tab").length).toBe(1);
        });
        it("should generate a unique Id for the nested tab", function () {
          expect($("div#content div#content-details-tab1.nsx-tab").length).toBe(1);
        });
        it("should render the html within that tab", function () {
          expect($("div#content-details-tab1 list").length).toBe(1);
        });
      });

      describe("page segment within tabs", function () {
        beforeAll(function (done) {
          clearContent();
          var tabs = Tabs.defineTabs({
            selector: "content"
          });
          var firstTab = tabs.defineTab({
            name: "First Tab",
            view: "nsx/templates/classic",
            viewModel: {}
          });

          var details = firstTab.definePageSegment({
            view: "nsx/knockout/simpleText",
            viewModel: {
              text: "This is deep"
            },
            selector: "details"
          });

          details.rendered.subscribe(function () {
            done();
          });
        });

        it("should generate a unique Id for the nested page-segment", function () {
          expect($("div#content-tab1 div#content-tab1-details").length).toBe(1);
        });
        it("should render the widget within that tab", function () {
          expect($("div#content-tab1-details [data-bind='text: text']").text()).toBe("This is deep");
        });
      });
    });
    describe("Plot injector", function () {
      var data = ko.observable();

      var piechart = PieChart.definePlot({
        data: data
      });

      data({
        x: ["A", "B", "C"],
        y: [45, 35, 20],
        success: true
      });

      describe("root injector", function () {
        beforeEach(function (done) {
          clearContent();
          Plot.definePlotSegment({
            plotModel: piechart.plot,
            selector: "content"
          });

          setTimeout(function () {
            done();
          }, maxRenderTime);
        });
        it("should inject the jqplot into the correct tag", function () {
          expect($("div#content .jqplot-target").length).toBe(1);
        });
      });
      describe("title option", function () {
        beforeEach(function (done) {
          clearContent();
          Plot.definePlotSegment({
            plotModel: piechart.plot,
            selector: "content"
          }, {
            title: "Awesome plot"
          });

          setTimeout(function () {
            done();
          }, maxRenderTime);
        });
        it("should show correct title", function () {
          expect($("div#content .nsx-plot-title").text()).toBe("Awesome plot");
          expect($("div#content .jqplot-target").length).toBe(1);
        });
      });
      describe("nested injector", function () {
        beforeEach(function (done) {
          clearContent();
          var content = Page.definePageSegment({
            view: "nsx/templates/classic",
            viewModel: {},
            selector: "content"
          });

          content.definePlotSegment({
            plotModel: piechart.plot,
            selector: "details"
          });

          setTimeout(function () {
            done();
          }, maxRenderTime);
        });
        it("should inject the jqplot into the correct nested tag", function () {
          expect($("div#content-details .jqplot-target").length).toBe(1);
        });
      });
    });
  });
});