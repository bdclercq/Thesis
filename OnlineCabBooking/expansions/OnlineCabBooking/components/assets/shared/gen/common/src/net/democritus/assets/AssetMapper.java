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
import net.democritus.assets.FileAssetDataRefConverter;
import net.democritus.assets.InternalAssetDataRefConverter;
import net.democritus.assets.ExternalAssetDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class AssetMapper implements IDataElementMapper<AssetDetails> {
  private static final Logger logger = LoggerFactory.getLogger(AssetMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private FileAssetDataRefConverter fileAssetDataRefConverter = new FileAssetDataRefConverter();
  private InternalAssetDataRefConverter internalAssetDataRefConverter = new InternalAssetDataRefConverter();
  private ExternalAssetDataRefConverter externalAssetDataRefConverter = new ExternalAssetDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<AssetDetails> parameter) {
    AssetDetails assetDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (assetDetails.getName() != null) {
        map.put("name", assetDetails.getName());
      }

      if (assetDetails.getType() != null) {
        map.put("type", assetDetails.getType());
      }

      if (assetDetails.getContentType() != null) {
        map.put("contentType", assetDetails.getContentType());
      }

      if (assetDetails.getByteSize() != null) {
        map.put("byteSize", String.valueOf(assetDetails.getByteSize()));
      }

      if (assetDetails.getFileId() != null) {
        map.put("fileId", assetDetails.getFileId());
      }

      if (assetDetails.getComplete() != null) {
        map.put("complete", String.valueOf(assetDetails.getComplete()));
      }
      if (DataRefValidation.isDataRefDefined(assetDetails.getFileAsset())) {
        Result<String> fileAsset = fileAssetDataRefConverter.asString(assetDetails.getFileAsset());
        if (fileAsset.isError()) {
          return Result.error("fileAsset: " + fileAsset.getMessage());
        }
        map.put("fileAsset", fileAsset.getValue());
      }

      if (DataRefValidation.isDataRefDefined(assetDetails.getInternalAsset())) {
        Result<String> internalAsset = internalAssetDataRefConverter.asString(assetDetails.getInternalAsset());
        if (internalAsset.isError()) {
          return Result.error("internalAsset: " + internalAsset.getMessage());
        }
        map.put("internalAsset", internalAsset.getValue());
      }

      if (DataRefValidation.isDataRefDefined(assetDetails.getExternalAsset())) {
        Result<String> externalAsset = externalAssetDataRefConverter.asString(assetDetails.getExternalAsset());
        if (externalAsset.isError()) {
          return Result.error("externalAsset: " + externalAsset.getMessage());
        }
        map.put("externalAsset", externalAsset.getValue());
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
          "Unexpected error while mapping instance " + assetDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<AssetDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      AssetDetails assetDetails = new AssetDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        assetDetails.setName(nameValueFromMap);
      }

      String typeValueFromMap = map.get("type");
      if (typeValueFromMap != null && !typeValueFromMap.isEmpty()) {
        assetDetails.setType(typeValueFromMap);
      }

      String contentTypeValueFromMap = map.get("contentType");
      if (contentTypeValueFromMap != null && !contentTypeValueFromMap.isEmpty()) {
        assetDetails.setContentType(contentTypeValueFromMap);
      }

      String byteSizeValueFromMap = map.get("byteSize");
      if (byteSizeValueFromMap != null && !byteSizeValueFromMap.isEmpty()) {
        try {
          Long byteSize = Long.valueOf(byteSizeValueFromMap);
          assetDetails.setByteSize(byteSize);
        } catch (NumberFormatException e) {
          return Result.error("byteSize: Expected number, but got '" + byteSizeValueFromMap + "'");
        }
      }

      String fileIdValueFromMap = map.get("fileId");
      if (fileIdValueFromMap != null && !fileIdValueFromMap.isEmpty()) {
        assetDetails.setFileId(fileIdValueFromMap);
      }

      String completeValueFromMap = map.get("complete");
      if (completeValueFromMap != null && !completeValueFromMap.isEmpty()) {
        Boolean complete = Boolean.valueOf(completeValueFromMap);
        assetDetails.setComplete(complete);
      }
      String fileAssetValueFromMap = map.get("fileAsset");
      Result<DataRef> fileAsset = fileAssetDataRefConverter.fromString(fileAssetValueFromMap);
      if (fileAsset.isError()) {
        return Result.error("fileAsset: " + fileAsset.getMessage());
      }
      assetDetails.setFileAsset(fileAsset.getValue());

      String internalAssetValueFromMap = map.get("internalAsset");
      Result<DataRef> internalAsset = internalAssetDataRefConverter.fromString(internalAssetValueFromMap);
      if (internalAsset.isError()) {
        return Result.error("internalAsset: " + internalAsset.getMessage());
      }
      assetDetails.setInternalAsset(internalAsset.getValue());

      String externalAssetValueFromMap = map.get("externalAsset");
      Result<DataRef> externalAsset = externalAssetDataRefConverter.fromString(externalAssetValueFromMap);
      if (externalAsset.isError()) {
        return Result.error("externalAsset: " + externalAsset.getMessage());
      }
      assetDetails.setExternalAsset(externalAsset.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(assetDetails);
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
