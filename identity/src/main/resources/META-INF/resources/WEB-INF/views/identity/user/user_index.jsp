<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fk" tagdir="/WEB-INF/tags"%>
    <jsp:include page="./edit.jsp"></jsp:include>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<html>
<head>
<meta charset="UTF-8">
<title>用户管理</title> 
<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/css/identity/user.css">
<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx }/js/easy-ui/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/js/identity/user_index.js"></script>
</head>
<body>
<div class="container">
	<div class="panel panel-default ">
		  <div class="panel-heading  title-color"> 用户列表 
		  		<!-- 添加按钮 -->
			  	<div  class="btn-add">
			 			添加<span>
			 				<img class="add-img" alt="" src="${ctx}/images/add.png">
			 			</span>
				</div>
		  </div>
		  <table class="table  table-hover">
		  		<thead>
		  			<tr>
		  				<th class="col-sm-2">姓名</th>
		  				<th class="col-sm-2">登录名</th>
		  				<th class="col-sm-4">身份</th>
		  				<th class="col-sm-2">状态</th>
		  				<th class="col-sm-2">操作</th>
		  			</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${page.content}" var="user" varStatus="status">
		  				<c:set  value="${status.count }" var="index"/>
			  			<tr  id="showList"  class="${index%2==0?'':'info' }">
			  				<td class="col-sm-2">${user.name }</td>
			  				<td  class="col-sm-2">${user.loginName}</td>
			  				<td class="col-sm-4">
			  				<c:forEach items="${user.roles}" var="role">
			  					<c:if test="${not role.fixed}">
			  						<span class="label label-info">${role.roleName }</span>
			  					</c:if>
			  				</c:forEach>
			  				</td>
			  				<td class="col-sm-2">
			  					<c:choose>
			  						<c:when test="${user.status eq 'NORMAL'}">
			  							正常
			  						</c:when>
			  						<c:when test="${user.status eq 'DISABLE'}">
			  							禁用
			  						</c:when>
			  						<c:otherwise>
			  							过期
			  						</c:otherwise>
			  					</c:choose>
			  				</td>
			  				<td class="col-sm-2">
			  					<c:set var="id" value="${user.id}"></c:set>
			  					<a href="#" id="${user.id}" class="btn-edit" onclick="edit('${user.id}');">修改</a>
			  					<c:choose>
			  						<c:when test="${user.status eq 'DISABLE'}">
			  							<a href="${ctx }/identity/show/active/${user.id}">激活</a>
			  						</c:when>
			  						<c:when test="${user.status eq 'EXPIRED'}">
			  							<a href="${ctx }/identity/show/active/${user.id}">激活</a>
			  						</c:when>
			  						<c:otherwise>
			  							<a href="${ctx }/identity/show/disable/${user.id}">禁用</a>
			  						</c:otherwise>
			  					</c:choose>
			  				</td>
			  			</tr>
		  			</c:forEach>
		  		</tbody>
		  		 <tfoot>
		  			<tr>
		  				<!-- 分页处理 -->
		  				<td colspan="5"  style="text-align:center; ">
		  					<fk:page  url="/identity/show"  page="${page }" />
		  				</td>
		  			</tr>
		  		</tfoot>
		  </table>
	</div>
</div>
</body>
</html>
