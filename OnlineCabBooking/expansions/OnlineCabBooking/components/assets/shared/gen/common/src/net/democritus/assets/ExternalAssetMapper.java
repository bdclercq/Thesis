package net.democritus.assets;

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
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class ExternalAssetMapper implements IDataElementMapper<ExternalAssetDetails> {
  private static final Logger logger = LoggerFactory.getLogger(ExternalAssetMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<ExternalAssetDetails> parameter) {
    ExternalAssetDetails externalAssetDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (externalAssetDetails.getName() != null) {
        map.put("name", externalAssetDetails.getName());
      }

      if (externalAssetDetails.getUri() != null) {
        map.put("uri", externalAssetDetails.getUri());
      }

      if (externalAssetDetails.getByteSize() != null) {
        map.put("byteSize", String.valueOf(externalAssetDetails.getByteSize()));
      }

      if (externalAssetDetails.getContentType() != null) {
        map.put("contentType", externalAssetDetails.getContentType());
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
          "Unexpected error while mapping instance " + externalAssetDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<ExternalAssetDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      ExternalAssetDetails externalAssetDetails = new ExternalAssetDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        externalAssetDetails.setName(nameValueFromMap);
      }

      String uriValueFromMap = map.get("uri");
      if (uriValueFromMap != null && !uriValueFromMap.isEmpty()) {
        externalAssetDetails.setUri(uriValueFromMap);
      }

      String byteSizeValueFromMap = map.get("byteSize");
      if (byteSizeValueFromMap != null && !byteSizeValueFromMap.isEmpty()) {
        try {
          Long byteSize = Long.valueOf(byteSizeValueFromMap);
          externalAssetDetails.setByteSize(byteSize);
        } catch (NumberFormatException e) {
          return Result.error("byteSize: Expected number, but got '" + byteSizeValueFromMap + "'");
        }
      }

      String contentTypeValueFromMap = map.get("contentType");
      if (contentTypeValueFromMap != null && !contentTypeValueFromMap.isEmpty()) {
        externalAssetDetails.setContentType(contentTypeValueFromMap);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(externalAssetDetails);
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
