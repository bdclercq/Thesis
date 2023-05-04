define(function () {

  function createApplication(name) {
    const application = {};
    const components = {};

    application.getName = function () {
      return name;
    };

    application.addComponent = function (component) {
      component.setApplication(application);
      components[component.getName()] = component;
      return application;
    };

    application.getComponent = function (componentName) {
      return components[componentName];
    };

    application.findDataElement = function (componentName, elementName) {
      const component = application.getComponent(componentName);
      if (component) {
        return component.getDataElement(elementName);
      }
      return null;
    };

    return application;
  }

  return {
    createApplication: createApplication
  }
});