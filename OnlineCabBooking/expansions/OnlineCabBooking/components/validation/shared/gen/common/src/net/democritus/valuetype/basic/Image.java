package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import java.io.Serializable;

// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class Image implements Serializable {

  private static final long serialVersionUID = 1L;

  private String image;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Image() {
    // anchor:custom-default-constructor:start
    this("");
    // anchor:custom-default-constructor:end
  }

  public Image(String image) {
    this.image = image;

    // anchor:custom-value-constructor:start
    if (image == null) {
      this.image = "";
    }
    // anchor:custom-value-constructor:end
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Image)) {
      return false;
    }

    Image other = (Image) obj;

    boolean isEqual = super.equals(other);
    // anchor:custom-equality:start
    if (this == obj)
      return true;

    if (image == null) {
      isEqual = other.image == null;
    } else {
      isEqual = image.equals(other.image);
    }
    // anchor:custom-equality:end
    return isEqual;
  }

  public String getValue() {
    // anchor:custom-getValue:start
    // anchor:custom-getValue:end

    return image;
  }

  public void setValue(String value) {
    // anchor:custom-setValue:start
    // anchor:custom-setValue:end

    image = value;
  }

  // anchor:custom-methods:start
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((image == null) ? 0 : image.hashCode());
    return result;
  }
  // anchor:custom-methods:end

}
