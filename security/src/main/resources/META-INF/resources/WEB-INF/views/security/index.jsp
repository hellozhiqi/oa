<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <h2 class="form-signin-heading">Welcome to login</h2>
        <label for="account" class="sr-only">账户</label>
        <input type="text" id="inputAccount"
	         class="form-control" 
	         placeholder="Enter your account"
	         name="loginName"
	         required="required"
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
  </body>
</html>
