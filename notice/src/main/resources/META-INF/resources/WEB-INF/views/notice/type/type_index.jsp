<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告类型</title> 
<link rel="stylesheet" href="/css/type.css">
<script type="text/javascript" src="${ctx }/js/type_index.js"></script>
</head>
<body>
<div class="container">
	<div class="panel panel-default ">
		  <div class="panel-heading  title-color"> 公告类型
		  </div>
		  <div  class="panel-body">
		  		<div class="col-sm-12 col-md-6">
		  			<div class="list-group">
						  <a href="#" class="list-group-item active">
						   	  <span id="role">类型名称</span>
						  </a>
						  <c:forEach items="${types }" var="type" varStatus="status">
		  					<c:set value="${status.count}" var="index" />
						  	<div  class="list-group-item role_x    ${index%2==0? 'liCss ' : ' '}"
						  			data-id="${ type.id}"
						  			data-name="${type.name }"
						  			>
						  			<span class="roleNameSpan">${type.name}</span> 
						  			<span class="close_x  hide  remove_btn" title="x">
						  				<img alt="" src="/images/X.png"  class="x_icon">
						  			</span>
						  		</div>
						  </c:forEach>
						</div>
			  </div>
			  <div class="col-sm-12 col-md-6" >
			  		<form class="form-horizontal" method="post" action="${ctx}/notice/type">
			  			<input type="hidden" name="id" id="noticeTypeId">
					  <div class="form-group">
						    <label for="exampleInputEmail1">类型名称</label>
						    <span id="noticeTypeTig"></span>
						    <input type="text" 
						    class="form-control" 
						    id="noticeTypeName"
						    name="name"
						    onblur="chckedTypeName(this.value)"
						    required="required" 
						    placeholder="类型名称">
					  </div>
					  <input type="hidden"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/>
					  	<button id="btn_save" type="submit" class="btn btn-primary">保存</button>
					</form>
			  </div>
		  </div>
	</div>
</div>
</body>
</html>
