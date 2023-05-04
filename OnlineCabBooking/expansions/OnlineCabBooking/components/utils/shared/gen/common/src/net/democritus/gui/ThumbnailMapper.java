package net.democritus.gui;

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

public class ThumbnailMapper implements IDataElementMapper<ThumbnailDetails> {
  private static final Logger logger = LoggerFactory.getLogger(ThumbnailMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<ThumbnailDetails> parameter) {
    ThumbnailDetails thumbnailDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (thumbnailDetails.getName() != null) {
        map.put("name", thumbnailDetails.getName());
      }

      if (thumbnailDetails.getFullName() != null) {
        map.put("fullName", thumbnailDetails.getFullName());
      }

      if (thumbnailDetails.getUri() != null) {
        map.put("uri", thumbnailDetails.getUri());
      }

      if (thumbnailDetails.getDepth() != null) {
        map.put("depth", String.valueOf(thumbnailDetails.getDepth()));
      }

      if (thumbnailDetails.getBorder() != null) {
        map.put("border", String.valueOf(thumbnailDetails.getBorder()));
      }

      if (thumbnailDetails.getThumbType() != null) {
        map.put("thumbType", thumbnailDetails.getThumbType());
      }

      if (thumbnailDetails.getThumbName() != null) {
        map.put("thumbName", thumbnailDetails.getThumbName());
      }

      if (thumbnailDetails.getTargetType() != null) {
        map.put("targetType", thumbnailDetails.getTargetType());
      }

      if (thumbnailDetails.getTargetName() != null) {
        map.put("targetName", thumbnailDetails.getTargetName());
      }

      if (thumbnailDetails.getTargetId() != null) {
        map.put("targetId", String.valueOf(thumbnailDetails.getTargetId()));
      }

      if (thumbnailDetails.getLeftX() != null) {
        map.put("leftX", String.valueOf(thumbnailDetails.getLeftX()));
      }

      if (thumbnailDetails.getTopY() != null) {
        map.put("topY", String.valueOf(thumbnailDetails.getTopY()));
      }

      if (thumbnailDetails.getWidth() != null) {
        map.put("width", String.valueOf(thumbnailDetails.getWidth()));
      }

      if (thumbnailDetails.getHeight() != null) {
        map.put("height", String.valueOf(thumbnailDetails.getHeight()));
      }

      if (thumbnailDetails.getClickAction() != null) {
        map.put("clickAction", thumbnailDetails.getClickAction());
      }

      if (thumbnailDetails.getHooverAction() != null) {
        map.put("hooverAction", thumbnailDetails.getHooverAction());
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
          "Unexpected error while mapping instance " + thumbnailDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<ThumbnailDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      ThumbnailDetails thumbnailDetails = new ThumbnailDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        thumbnailDetails.setName(nameValueFromMap);
      }

      String fullNameValueFromMap = map.get("fullName");
      if (fullNameValueFromMap != null && !fullNameValueFromMap.isEmpty()) {
        thumbnailDetails.setFullName(fullNameValueFromMap);
      }

      String uriValueFromMap = map.get("uri");
      if (uriValueFromMap != null && !uriValueFromMap.isEmpty()) {
        thumbnailDetails.setUri(uriValueFromMap);
      }

      String depthValueFromMap = map.get("depth");
      if (depthValueFromMap != null && !depthValueFromMap.isEmpty()) {
        try {
          Integer depth = Integer.valueOf(depthValueFromMap);
          thumbnailDetails.setDepth(depth);
        } catch (NumberFormatException e) {
          return Result.error("depth: Expected number, but got '" + depthValueFromMap + "'");
        }
      }

      String borderValueFromMap = map.get("border");
      if (borderValueFromMap != null && !borderValueFromMap.isEmpty()) {
        try {
          Integer border = Integer.valueOf(borderValueFromMap);
          thumbnailDetails.setBorder(border);
        } catch (NumberFormatException e) {
          return Result.error("border: Expected number, but got '" + borderValueFromMap + "'");
        }
      }

      String thumbTypeValueFromMap = map.get("thumbType");
      if (thumbTypeValueFromMap != null && !thumbTypeValueFromMap.isEmpty()) {
        thumbnailDetails.setThumbType(thumbTypeValueFromMap);
      }

      String thumbNameValueFromMap = map.get("thumbName");
      if (thumbNameValueFromMap != null && !thumbNameValueFromMap.isEmpty()) {
        thumbnailDetails.setThumbName(thumbNameValueFromMap);
      }

      String targetTypeValueFromMap = map.get("targetType");
      if (targetTypeValueFromMap != null && !targetTypeValueFromMap.isEmpty()) {
        thumbnailDetails.setTargetType(targetTypeValueFromMap);
      }

      String targetNameValueFromMap = map.get("targetName");
      if (targetNameValueFromMap != null && !targetNameValueFromMap.isEmpty()) {
        thumbnailDetails.setTargetName(targetNameValueFromMap);
      }

      String targetIdValueFromMap = map.get("targetId");
      if (targetIdValueFromMap != null && !targetIdValueFromMap.isEmpty()) {
        try {
          Long targetId = Long.valueOf(targetIdValueFromMap);
          thumbnailDetails.setTargetId(targetId);
        } catch (NumberFormatException e) {
          return Result.error("targetId: Expected number, but got '" + targetIdValueFromMap + "'");
        }
      }

      String leftXValueFromMap = map.get("leftX");
      if (leftXValueFromMap != null && !leftXValueFromMap.isEmpty()) {
        try {
          Integer leftX = Integer.valueOf(leftXValueFromMap);
          thumbnailDetails.setLeftX(leftX);
        } catch (NumberFormatException e) {
          return Result.error("leftX: Expected number, but got '" + leftXValueFromMap + "'");
        }
      }

      String topYValueFromMap = map.get("topY");
      if (topYValueFromMap != null && !topYValueFromMap.isEmpty()) {
        try {
          Integer topY = Integer.valueOf(topYValueFromMap);
          thumbnailDetails.setTopY(topY);
        } catch (NumberFormatException e) {
          return Result.error("topY: Expected number, but got '" + topYValueFromMap + "'");
        }
      }

      String widthValueFromMap = map.get("width");
      if (widthValueFromMap != null && !widthValueFromMap.isEmpty()) {
        try {
          Integer width = Integer.valueOf(widthValueFromMap);
          thumbnailDetails.setWidth(width);
        } catch (NumberFormatException e) {
          return Result.error("width: Expected number, but got '" + widthValueFromMap + "'");
        }
      }

      String heightValueFromMap = map.get("height");
      if (heightValueFromMap != null && !heightValueFromMap.isEmpty()) {
        try {
          Integer height = Integer.valueOf(heightValueFromMap);
          thumbnailDetails.setHeight(height);
        } catch (NumberFormatException e) {
          return Result.error("height: Expected number, but got '" + heightValueFromMap + "'");
        }
      }

      String clickActionValueFromMap = map.get("clickAction");
      if (clickActionValueFromMap != null && !clickActionValueFromMap.isEmpty()) {
        thumbnailDetails.setClickAction(clickActionValueFromMap);
      }

      String hooverActionValueFromMap = map.get("hooverAction");
      if (hooverActionValueFromMap != null && !hooverActionValueFromMap.isEmpty()) {
        thumbnailDetails.setHooverAction(hooverActionValueFromMap);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(thumbnailDetails);
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
