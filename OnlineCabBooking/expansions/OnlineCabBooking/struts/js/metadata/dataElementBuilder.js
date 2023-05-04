define(function () {

  function createElement(name) {
    const element = {};
    let component;
    const fields = {};
    const projections = {};

    element.getName = function () {
      return name;
    };

    element.setComponent = function (aComponent) {
      component = aComponent;
    }

    element.getQualifiedName = function () {
      return component.getName() + "." + element.getName();
    };

    element.getField = function (name) {
      return fields[name];
    };

    element.addField = function (field) {
      fields[field.getName()] = field;
      field.setElement(element);

      return element;
    };

    element.addProjection = function (projection) {
      projection.setDataElement(element);
      projections[projection.getName()] = projection;

      return element;
    };

    element.findProjection = function (projectionName) {
      return projections[projectionName];
    };

    return element;
  }

  return {
    create: createElement
  };
});