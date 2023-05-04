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
import net.democritus.assets.InternalAssetDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class InternalAssetChunkMapper implements IDataElementMapper<InternalAssetChunkDetails> {
  private static final Logger logger = LoggerFactory.getLogger(InternalAssetChunkMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private InternalAssetDataRefConverter internalAssetDataRefConverter = new InternalAssetDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<InternalAssetChunkDetails> parameter) {
    InternalAssetChunkDetails internalAssetChunkDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (internalAssetChunkDetails.getContent() != null) {
        String content = Base64.encode(internalAssetChunkDetails.getContent());
        map.put("content", content);
      }

      if (internalAssetChunkDetails.getByteSize() != null) {
        map.put("byteSize", String.valueOf(internalAssetChunkDetails.getByteSize()));
      }

      if (internalAssetChunkDetails.getPosition() != null) {
        map.put("position", String.valueOf(internalAssetChunkDetails.getPosition()));
      }

      if (internalAssetChunkDetails.getIsLast() != null) {
        map.put("isLast", String.valueOf(internalAssetChunkDetails.getIsLast()));
      }
      if (DataRefValidation.isDataRefDefined(internalAssetChunkDetails.getInternalAsset())) {
        Result<String> internalAsset = internalAssetDataRefConverter.asString(internalAssetChunkDetails.getInternalAsset());
        if (internalAsset.isError()) {
          return Result.error("internalAsset: " + internalAsset.getMessage());
        }
        map.put("internalAsset", internalAsset.getValue());
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
          "Unexpected error while mapping instance " + internalAssetChunkDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<InternalAssetChunkDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      InternalAssetChunkDetails internalAssetChunkDetails = new InternalAssetChunkDetails();

      // anchor:convert-map-to-fields:start
      String contentValueFromMap = map.get("content");
      if (contentValueFromMap != null && !contentValueFromMap.isEmpty()) {
        try {
          byte[] content = Base64.decode(contentValueFromMap);
          internalAssetChunkDetails.setContent(content);
        } catch (IllegalArgumentException e) {
          return Result.error("content: " + e.getMessage());
        }
      }

      String byteSizeValueFromMap = map.get("byteSize");
      if (byteSizeValueFromMap != null && !byteSizeValueFromMap.isEmpty()) {
        try {
          Integer byteSize = Integer.valueOf(byteSizeValueFromMap);
          internalAssetChunkDetails.setByteSize(byteSize);
        } catch (NumberFormatException e) {
          return Result.error("byteSize: Expected number, but got '" + byteSizeValueFromMap + "'");
        }
      }

      String positionValueFromMap = map.get("position");
      if (positionValueFromMap != null && !positionValueFromMap.isEmpty()) {
        try {
          Integer position = Integer.valueOf(positionValueFromMap);
          internalAssetChunkDetails.setPosition(position);
        } catch (NumberFormatException e) {
          return Result.error("position: Expected number, but got '" + positionValueFromMap + "'");
        }
      }

      String isLastValueFromMap = map.get("isLast");
      if (isLastValueFromMap != null && !isLastValueFromMap.isEmpty()) {
        Boolean isLast = Boolean.valueOf(isLastValueFromMap);
        internalAssetChunkDetails.setIsLast(isLast);
      }
      String internalAssetValueFromMap = map.get("internalAsset");
      Result<DataRef> internalAsset = internalAssetDataRefConverter.fromString(internalAssetValueFromMap);
      if (internalAsset.isError()) {
        return Result.error("internalAsset: " + internalAsset.getMessage());
      }
      internalAssetChunkDetails.setInternalAsset(internalAsset.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(internalAssetChunkDetails);
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
