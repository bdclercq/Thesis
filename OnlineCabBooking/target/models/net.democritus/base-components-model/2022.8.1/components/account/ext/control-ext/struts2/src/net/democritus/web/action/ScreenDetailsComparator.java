package net.democritus.web.action;

import net.democritus.usr.menu.ScreenDetails;

import java.util.Comparator;

// @feature:struts-navigation
public class ScreenDetailsComparator implements Comparator {

  public int compare(Object obj1, Object obj2) {
    ScreenDetails details1 = (ScreenDetails) obj1;
    ScreenDetails details2 = (ScreenDetails) obj2;
    int screenComp = 0;
    if ((details1.getSortOrder() != null) && (details2.getSortOrder() != null)) {
      screenComp = details1.getSortOrder().compareTo(details2.getSortOrder());
    }

    if (screenComp == 0) { // if same sortOrder, sort by name
      screenComp = details1.getName().compareTo(details2.getName());
    }

    return screenComp;
  }

}
