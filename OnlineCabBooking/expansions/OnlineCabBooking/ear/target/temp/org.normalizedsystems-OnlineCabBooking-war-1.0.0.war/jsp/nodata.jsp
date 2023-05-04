<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp" %>
<%String styleVar = (String) session.getAttribute("style");%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="header.jsp" %>
<jsp:include page="<%=styleVar+\"-styles.jsp\"%>" />
</head>

<jsp:include page="<%=styleVar+\"-heading.jsp\"%>" />

<p><span class="tit"><s:property value="getText('ns.noData')"/></span></p><br/>

<jsp:include page="<%=styleVar+\"-footing.jsp\"%>" />
