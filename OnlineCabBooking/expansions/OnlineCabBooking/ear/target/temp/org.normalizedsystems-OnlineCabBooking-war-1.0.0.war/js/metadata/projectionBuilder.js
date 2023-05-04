define(function () {

  function createProjection(name) {
    const projection = {};
    let dataElement;
    const fields = {};

    projection.getName = function () {
      return name;
    };

    projection.setDataElement = function (aDataElement) {
      dataElement = aDataElement;
    };

    projection.getDataElement = function () {
      return dataElement;
    };

    projection.addField = function (field) {
      fields[field.getName()] = field;

      return projection;
    };

    projection.getField = function (fieldName) {
      return fields[fieldName];
    };

    return projection;
  };

  return {
    create: createProjection
  };
});