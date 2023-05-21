package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.palver.util.StringUtil;
import net.democritus.mapping.DateStringMapper;
import net.democritus.mapping.Base64;
import net.democritus.mapping.IDataElementMapper;
import net.democritus.sys.DataRef;
import net.democritus.sys.DataRefWithFunctionalKey;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.ParameterContext;
import net.democritus.validation.Result;
import net.democritus.validation.ValidationResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:dataRef-imports:start
import cabBookingCore.PaymentDataRefConverter;
import cabBookingCore.CustomerDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class InvoiceMapper implements IDataElementMapper<InvoiceDetails> {
  private static final Logger logger = LoggerFactory.getLogger(InvoiceMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private PaymentDataRefConverter paymentDataRefConverter = new PaymentDataRefConverter();
  private CustomerDataRefConverter customerDataRefConverter = new CustomerDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<InvoiceDetails> parameter) {
    InvoiceDetails invoiceDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (DataRefValidation.isDataRefDefined(invoiceDetails.getPayment())) {
        Result<String> payment = paymentDataRefConverter.asString(invoiceDetails.getPayment());
        if (payment.isError()) {
          return Result.error("payment: " + payment.getMessage());
        }
        map.put("payment", payment.getValue());
      }

      if (DataRefValidation.isDataRefDefined(invoiceDetails.getCustomer())) {
        Result<String> customer = customerDataRefConverter.asString(invoiceDetails.getCustomer());
        if (customer.isError()) {
          return Result.error("customer: " + customer.getMessage());
        }
        map.put("customer", customer.getValue());
      }
      // anchor:convert-fields-to-map:end

      // @anchor:fields-to-map:start
      // @anchor:fields-to-map:end
      // anchor:custom-fields-to-map:start
      // anchor:custom-fields-to-map:end

      return Result.success(map);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance " + invoiceDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<InvoiceDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      InvoiceDetails invoiceDetails = new InvoiceDetails();

      // anchor:convert-map-to-fields:start
      String paymentValueFromMap = map.get("payment");
      Result<DataRef> payment = paymentDataRefConverter.fromString(paymentValueFromMap);
      if (payment.isError()) {
        return Result.error("payment: " + payment.getMessage());
      }
      invoiceDetails.setPayment(payment.getValue());

      String customerValueFromMap = map.get("customer");
      Result<DataRef> customer = customerDataRefConverter.fromString(customerValueFromMap);
      if (customer.isError()) {
        return Result.error("customer: " + customer.getMessage());
      }
      invoiceDetails.setCustomer(customer.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(invoiceDetails);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance to details", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public void setListSeparator(String listSeparator) {
    this.listSeparator = listSeparator;
  }

}
