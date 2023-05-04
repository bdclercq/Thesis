define(function () {

  function createComponent(name) {
    const component = {};
    const dataElements = {};
    let application;

    component.getName = function () {
      return name;
    };

    component.addDataElement = function (dataElement) {
      dataElement.setComponent(component);
      dataElements[dataElement.getName()] = dataElement;
      return component;
    };

    component.getDataElement = function (name) {
      return dataElements[name];
    }

    component.setApplication = function (anApplication) {
      application = anApplication;
    };

    component.getApplication = function () {
      return application;
    };

    return component;
  }

  return {
    createComponent: createComponent
  };
});
