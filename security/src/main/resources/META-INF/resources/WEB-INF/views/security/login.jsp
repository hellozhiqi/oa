<%@page import="java.util.Enumeration"%>
<%@page import="java.util.EnumMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录</title>
	 <!-- Custom styles for this template -->
    <link href="${ctx}/public/signin.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <form class="form-signin" method="post" action="${ctx}/security/do-login">
        <c:if test="${param.logout eq ''}">
        	<div class="alert alert-success glyphicon glyphicon-ok-circle" role="alert">成功退出登录</div>
        </c:if>
        <c:if test="${param.error eq '' }">
        	<div class="alert alert-danger glyphicon glyphicon-remove-circle" role="alert">
				<strong>登录失败</strong>
				${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message }
			</div>
        </c:if>
         <h3 class="form-signin-heading">Welcome to login</h3>
        <label for="account" class="sr-only">账户</label>
        <input type="text" id="inputAccount"
	         class="form-control" 
	         placeholder="Enter your account"
	         name="loginName"
	         required="required"
	         value="${sessionScope.loginName }"
        	 autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" 
	        id="inputPassword" 
	        class="form-control"
	        name="password"
	        required="required"
	        placeholder="Enter your password"
          >
          <input type="hidden"
				name="${_csrf.parameterName}"
				value="${_csrf.token}"/>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div>
    <!-- 获取session所有属性 -->
<%--     <% 
    	Enumeration<String> names=session.getAttributeNames();
    	while(names.hasMoreElements()){
    		
    		String name=names.nextElement();
    		Object value=session.getAttribute(name);
    		out.println(name);
    		out.println("===");
    		out.println(value+"<br/>");
    	}
    %>   --%>
  </body>
</html>
