<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" scope="application"></c:set>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>智能办公自动化系统--<sitemesh:write property="title"/></title>
	<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx }/css/dashboard.css">
	<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js" ></script>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<sitemesh:write property="head"/>

  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header" >
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">隐藏或显示导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <button type="button" class="navbar-toggle collapsed" data-toggle="sidebar">
            <span class="sr-only">隐藏或显示菜单</span>
            <span class="glyphicon glyphicon-th-large" style="color: white; width: 20px;"></span>
          </button>
          <!-- logon位置 -->
          <a class="navbar-brand" href="#">智能办公自动化系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">首页</a></li>
            <li><a href="#">个人</a></li>
            <li><a href="#">设置</a></li>
            <li><a href="#">帮助</a></li>
            <li><a href="#" onclick="$('#logout-form').submit();">退出</a></li>
          </ul>
          <!-- 退出必须使用post表单 -->
          <form id="logout-form" action="${ctx}/security/do-logout" method="post"  style="display: none;">
          		<input type="hidden"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/>
          </form>
          <!-- 搜索框 -->
          <form class="navbar-form navbar-right">
          
          	  <input type="text" class="form-control" placeholder="请输入关键字，按回车键搜索哟"
          	   name="keyword" value="${param.keyword }">
            
          </form>
        </div>
      </div>
    </nav>
	<!-- 左侧一,二级菜单 -->
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
         	<sitemesh:write property="body"/>
        </div>
      </div>
    <script type="text/javascript" src="${ctx }/js/index.js" ></script>
  </body>
</html>
