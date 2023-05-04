<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/jsp/knockout/header.jspf" %>
<html>
<head>
  <title>welcome</title>
<!-- styles:start -->
<%@ include file="/jsp/knockout/styles.jspf" %>
<!-- styles:end -->

<!-- start: scripts loading before body -->
<%@ include file="/jsp/knockout/scripts-before.jspf" %>

<!-- header-scripts:start -->
<!-- header-scripts:end -->
</head>
<body class="opt-padding">

<%@ include file="/jsp/knockout/ko-heading.jspf" %>


<%@ include file="/html/welcomeInclude.html" %>

<%@ include file="/jsp/knockout/ko-footing.jspf" %>

<script type="text/javascript">
require(['jquery', 'knockoutjs', 'nsx/nsx-menu', 'bootstrapjs'], function($, ko, nsxMenu) {
  var applicationName = '<%=contextPath.substring(1)%>';
  nsxMenu.loadAndAttachMenu(applicationName, 'menu');
  ko.applyBindings({title: "Welkom"}, $(".brand")[0]);
});
</script>
</body>

</html>
