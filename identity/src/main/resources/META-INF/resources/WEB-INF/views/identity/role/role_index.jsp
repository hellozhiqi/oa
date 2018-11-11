<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>角色管理</title> 
<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/css/identity/role.css">
<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx }/js/identity/role_index.js"></script>
</head>
<body>
<div class="container">
	<div class="panel panel-default ">
		  <div class="panel-heading  title-color"> 角色管理
		  		<!-- 添加按钮 -->
			  	<div  class="btn-add" data-toggle="modal" data-target="#myModal">
			 			添加<span>
			 				<img class="add-img" alt="" src="${ctx}/images/add.png">
			 			</span>
				</div>
		  </div>
		  <div  class="panel-body">
		  		<div class="col-sm-12 col-md-6">
		  			<div class="list-group">
						  <a href="#" class="list-group-item active">
						   	  <span id="role">角 色</span>
						   	  <span id="key">KEY</span>
						  </a>
						  <c:forEach items="${roles }" var="role" varStatus="status">
		  					<c:set value="${status.count}" var="index" />
						  	<div  class="list-group-item role_x    ${index%2==0? 'liCss ' : ' '}"
						  			data-id="${ role.id}"
						  			data-name="${role.roleName }"
						  			data-key="${role.roleKey }"
						  			>
						  			<span class="roleNameSpan">${role.roleName}</span> 
						  			<span class="roleKeySpan" >[ ${ role.roleKey} ]</span>
						  			<span class="close_x  hide  remove_btn" title="x">
						  				<img alt="" src="/images/X.png"  class="x_icon">
						  			</span>
						  		</div>
						  </c:forEach>
						</div>
			  </div>
			  <div class="col-sm-12 col-md-6" >
			  		<form class="form-horizontal" method="post" action="${ctx}/identity/role">
			  			<input type="hidden" name="id" id="roleId">
					  <div class="form-group">
						    <label for="exampleInputEmail1">角色名称</label>
						    <input type="text" 
						    class="form-control" 
						    id="roleName"
						    name="roleName"
						    required="required" 
						    placeholder="角色名称">
					  </div>
					  <div class="form-group">
						    <label for="exampleInputPassword1">角色KEY</label>
						    <span id="roleKeyTig"></span>
						    <input type="text"
						     class="form-control"
						     id="roleKey"
						     name="roleKey"
						     required="required"
						     onblur="chckedRoleKey(this.value)"
						     placeholder="角色KEY">
					  </div>
					  	<button id="btn_save" type="submit" class="btn btn-primary">保存</button>
					</form>
			  </div>
		  </div>
	</div>
</div>
</body>
</html>
