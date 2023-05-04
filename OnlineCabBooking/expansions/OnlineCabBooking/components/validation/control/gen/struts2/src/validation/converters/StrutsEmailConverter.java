package validation.converters;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Map;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;
import net.democritus.validation.IConverter;
import net.democritus.validation.Result;
public class StrutsEmailConverter extends StrutsTypeConverter {

  public Object convertFromString(Map context, String[] values, Class toClass) {
    Result result = getConverter().fromString(values[0]);
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      throw new TypeConversionException(result.getMessage());
    }
  }

  public String convertToString(Map context, Object obj) {
    Result<String> result = getConverter().asString(obj);
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      throw new TypeConversionException(result.getMessage());
    }
  }

  private IConverter getConverter() {
    return new net.democritus.valuetype.basic.EmailConverter();
  }

}
