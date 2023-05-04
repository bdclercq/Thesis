define(function () {

  function createField(name) {
    const field = {};
    let readOnly = false;
    let element;

    field.getName = function () {
      return name;
    };

    field.isReadOnly = function () {
      return readOnly;
    };

    field.setReadOnly = function () {
      readOnly = true;
    };

    field.setElement = function (anElement) {
      element = anElement;
    };

    field.getElement = function () {
      return element;
    };

    return field;
  }

  function createValueField(name, type) {
    const field = createField(name);

    field.getType = function () {
      return type;
    };

    return field;
  }

  function createLinkField(name, linkedElement, linkType) {
    const field = createField(name);

    field.getLinkType = function () {
      return linkType;
    };

    field.getLinkedELement = function () {
      return linkedElement;
    }

    return field;
  }

  function createCalculatedField(name, type) {
    const field = createField(name);

    field.setReadOnly();

    field.getType = function () {
      return type;
    };

    return field;
  }

  const fieldBuilder = {

    createLong: function (name) {
      return createValueField(name, 'long');
    },
    createString: function (name) {
      return createValueField(name, 'string');
    },
    createLn01: function (name, elementReference) {
      return createLinkField(name, elementReference, 'ln01');
    },
    createLn02: function (name, elementReference) {
      return createLinkField(name, elementReference, 'ln02');
    },

  };

  return fieldBuilder;

});