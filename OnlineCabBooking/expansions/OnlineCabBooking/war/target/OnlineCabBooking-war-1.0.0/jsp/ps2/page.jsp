<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title data-bind="text: title"></title>

  <link rel="icon" href="../../images/favicon.ico">

  <script type="text/javascript" src="../../js/require/require.min.js?v=20230411111249"></script>
  <script type="text/javascript" src="../../js/require/requireConfig.js?v=20230411111249"></script>
  <script type="text/javascript" src="../../js/nsx/nsx-page.js?v=20230411111249"></script>
</head>
<body data-page-model="${param.pageModel}">
  <span id="nsx-menu-bar">
    <navigation/>
  </span>
  <div class="container-fluid" style="margin-top: 10px">
    <div class="row-fluid">
      <div class="span12">
        <page/>
      </div>
    </div>
  </div>
  <span id="dynamicContent"></span>
  <footer>
    <div style="position: absolute; bottom: 0; right: 0;">
      <appInfo/>
    </div>
  </footer>
</body>
</html>