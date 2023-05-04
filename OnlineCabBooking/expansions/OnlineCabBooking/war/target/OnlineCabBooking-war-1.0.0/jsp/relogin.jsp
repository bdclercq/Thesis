<!DOCTYPE html>
<!-- expanded with nsx-expanders:5.12.1, expansionResource net.democritus:web-styles:2022.4.1 -->
<%@ include file="/jsp/knockout/header.jspf" %>
<html>
<head>
  <title>OnlineCabBooking</title>

  <!-- styles:start -->
  <link rel="stylesheet" href="<%=contextPath%>/styles/nsx-login/stars.css">
  <link rel="stylesheet" href="<%=contextPath%>/styles/nsx-login/styles.css">
  <!-- styles:end -->
  <!-- @anchor:styles:start -->
  <!-- @anchor:styles:end -->
  <!-- anchor:custom-styles:start -->
  <!-- anchor:custom-styles:end -->

  <!-- header-scripts:start -->
  <!-- header-scripts:end -->
  <!-- @anchor:header-scripts:start -->
  <!-- @anchor:header-scripts:end -->
  <!-- anchor:custom-header-scripts:start -->
  <!-- anchor:custom-header-scripts:end -->
</head>

<body class="nsx-login">
  <section class="nsx-login-panel">

    <div class="nsx-login-logo"></div>

    <div class="nsx-box">

      <h1>OnlineCabBooking</h1>

      <form name="loginForm" method="post" action="welcome">

        <!-- @anchor:before-loginForm-controls:start -->
        <!-- @anchor:before-loginForm-controls:end -->
        <!-- anchor:custom-before-loginForm-controls:start -->
        <!-- anchor:custom-before-loginForm-controls:end -->

        <!-- Username -->
        <div class="nsx-input-field">
          <input type="text" name="username" placeholder='Username' autocomplete="off">
          <label for="username">
            <img src="images/icons/person-outline.svg" />
            <span>username</span>
          </label>
        </div>

        <!-- Password -->
        <div class="nsx-input-field">
          <input type="password" name='password' placeholder="Password">
          <label for="password">
            <img src="images/icons/lock-outline.svg" />
            <span>password</span>
          </label>
        </div>

        <!-- @anchor:after-loginForm-fields:start -->
        <!-- @anchor:after-loginForm-fields:end -->
        <!-- anchor:custom-after-loginForm-fields:start -->
        <!-- anchor:custom-after-loginForm-fields:end -->

        <!-- Submit -->
        <input type="submit" value="Login" class="submit-button nsx-submit-button"/>

        <!-- @anchor:after-loginForm-controls:start -->
        <!-- @anchor:after-loginForm-controls:end -->
        <!-- anchor:custom-after-loginForm-controls:start -->
        <!-- anchor:custom-after-loginForm-controls:end -->

        <div class="clear"></div>
      </form>

    </div>

  </section>

  <section class="nsx-applications-brand">
    <img src="images/ns-applications.svg" />
  </section>

  <!-- Stars -->
  <div id='stars1' class="stars"></div>
  <div id='stars2' class="stars"></div>
  <div id='stars3' class="stars"></div>
</body>

<!-- @anchor:scripts:start -->
<!-- @anchor:scripts:end -->
<!-- anchor:custom-scripts:start -->
<!-- anchor:custom-scripts:end -->

</html>
