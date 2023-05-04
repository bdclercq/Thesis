package net.democritus.usr.menu;

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
import net.democritus.usr.menu.MenuDataRefConverter;
import net.democritus.usr.menu.ScreenDataRefConverter;
import net.democritus.usr.menu.MenuItemDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class MenuItemMapper implements IDataElementMapper<MenuItemDetails> {
  private static final Logger logger = LoggerFactory.getLogger(MenuItemMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private MenuDataRefConverter menuDataRefConverter = new MenuDataRefConverter();
  private ScreenDataRefConverter screenDataRefConverter = new ScreenDataRefConverter();
  private MenuItemDataRefConverter menuItemDataRefConverter = new MenuItemDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<MenuItemDetails> parameter) {
    MenuItemDetails menuItemDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (menuItemDetails.getSortOrder() != null) {
        map.put("sortOrder", String.valueOf(menuItemDetails.getSortOrder()));
      }
      if (DataRefValidation.isDataRefDefined(menuItemDetails.getMenu())) {
        Result<String> menu = menuDataRefConverter.asString(menuItemDetails.getMenu());
        if (menu.isError()) {
          return Result.error("menu: " + menu.getMessage());
        }
        map.put("menu", menu.getValue());
      }

      if (DataRefValidation.isDataRefDefined(menuItemDetails.getScreen())) {
        Result<String> screen = screenDataRefConverter.asString(menuItemDetails.getScreen());
        if (screen.isError()) {
          return Result.error("screen: " + screen.getMessage());
        }
        map.put("screen", screen.getValue());
      }

      if (DataRefValidation.isDataRefDefined(menuItemDetails.getMenuItem())) {
        Result<String> menuItem = menuItemDataRefConverter.asString(menuItemDetails.getMenuItem());
        if (menuItem.isError()) {
          return Result.error("menuItem: " + menuItem.getMessage());
        }
        map.put("menuItem", menuItem.getValue());
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
          "Unexpected error while mapping instance " + menuItemDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<MenuItemDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      MenuItemDetails menuItemDetails = new MenuItemDetails();

      // anchor:convert-map-to-fields:start
      String sortOrderValueFromMap = map.get("sortOrder");
      if (sortOrderValueFromMap != null && !sortOrderValueFromMap.isEmpty()) {
        try {
          Integer sortOrder = Integer.valueOf(sortOrderValueFromMap);
          menuItemDetails.setSortOrder(sortOrder);
        } catch (NumberFormatException e) {
          return Result.error("sortOrder: Expected number, but got '" + sortOrderValueFromMap + "'");
        }
      }
      String menuValueFromMap = map.get("menu");
      Result<DataRef> menu = menuDataRefConverter.fromString(menuValueFromMap);
      if (menu.isError()) {
        return Result.error("menu: " + menu.getMessage());
      }
      menuItemDetails.setMenu(menu.getValue());

      String screenValueFromMap = map.get("screen");
      Result<DataRef> screen = screenDataRefConverter.fromString(screenValueFromMap);
      if (screen.isError()) {
        return Result.error("screen: " + screen.getMessage());
      }
      menuItemDetails.setScreen(screen.getValue());

      String menuItemValueFromMap = map.get("menuItem");
      Result<DataRef> menuItem = menuItemDataRefConverter.fromString(menuItemValueFromMap);
      if (menuItem.isError()) {
        return Result.error("menuItem: " + menuItem.getMessage());
      }
      menuItemDetails.setMenuItem(menuItem.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(menuItemDetails);
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
