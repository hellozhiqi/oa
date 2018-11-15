<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fk" tagdir="/WEB-INF/tags"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<html>
<head>
<meta charset="UTF-8">
<title>用户管理</title> 
<link rel="stylesheet" href="${ctx}/css/identity/user.css">
<link rel="stylesheet" href="${ctx }/css/easy-ui/easyui.css">
<script type="text/javascript" src="${ctx }/js/easy-ui/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="${ctx}/js/identity/user_index.js"></script>
	<%-- 把CSRF的验证码放到HTML头里面保存起来 --%>
	<%-- 使用AJAX的时候，必须要设置请求头，请求头的内容从HTML头里面获取 --%>
</head>
<body>
<div class="container ">
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
		  					<fk:page  url="/identity/show?keyword=${param.keyword}"  page="${page }" />
		  				</td>
		  			</tr>
		  		</tfoot>
		  </table>
	</div>
</div>

<div id="w" class="easyui-window" title="编辑用户信息" 
		data-options="modal:true,closed:true,iconCls:'icon-save'"  
		style="width:700px;height:500px ;padding:10px; display: none;" > 
	
  		<form class="form-horizontal" method="post" 
       	 		enctype="multipart/form-data" action="${ctx }/identity/show">
       	 		<!-- 修改时,需要使用  id="module_edit_id" -->
       	 	<input  name="id" value="${user.id}" type="hidden">
			  <div class="col-xm-12">
			  	  <div class="form-group">
					    <label for="inputName" class="col-sm-2">姓名</label>
					    <div class="col-sm-8">
					   		   <input type="text" 
					   		   class="form-control" 
					   		   id="inputName"
					   		   name="name"
					   		   required="required"
					   		   ${not empty user ? ' readonly="readonly" ' : ' ' }
					   		   onblur="loginNameIcon(this.value)"
					   		   value="${user.name }"
					   		   placeholder="用户真实姓名">
					    </div>
					    <!-- 图标 -->
					   <div class="nameIcon">
						    <span  id="name_icon"  class="badge  badge-icon1">
						    	<span id="ok_error1"></span>
						    </span>
					    </div>
					     <!-- 提示信息 -->
					    <span id="nameTig"></span>
		  			</div>
			  </div>
			  <div class="col-xm-12">
				   <div class="form-group  loginName">
					    <label for="inputLoginName" class="col-sm-2">登录名</label>
					    <div class="col-sm-8">
					       <input type="text" 
					       class="form-control" 
					       id="inputLoginName" 
					       name="loginName"
					       onblur="checkLoginName(this.value)"
					      ${empty user ? '  required="required" ':' ' }
					       value="${user.loginName }"
					       placeholder="用于系统登录的帐号${not empty user?',不修改则不用填': '' }">
					    </div>
					    <!-- 图标 -->
					    <div class="LoginNameIcon">
						    <span  id="loginName_icon" class="badge  badge-icon">
						    	<span id="ok_error"></span>
						    </span>
					    </div>
					    <!-- 提示信息 -->
					    <span id="loginNameTig"></span>
				    </div>
			  </div>
			  <div class="col-xm-12 ">
			  	  <div class="form-group">
					    <label for="inputPassword" class="col-sm-2">密码</label>
					    <div class="col-sm-8">
					    	  <input type="password" 
					    	  class="form-control"
					    	  id="inputPassword" 
					    	  name="password"
					    	  required="required"
					    	  placeholder="用于系统登录的密码">
					    	  <input type="hidden"
								name="${_csrf.parameterName}"
								value="${_csrf.token}"/>
					    </div>
				    </div>
			  </div>
			   <div class="col-sm-12 ">
			   			<div  class="col-sm-5">
			   				<div class="list-group ">
								  <div class="list-group-item active">
								    	已选择角色
								  </div>
								  <div class="roles selected-roles selected">
										<ul></ul>
						    	  </div>
							</div>
			   			</div>
			   			<div  class="col-sm-2">
			   					<div  class="btn-group">
						    		<a class="btn btn-default add-selected">添加所选</a>
						    		<a class="btn btn-default add-all">添加所有</a><br>
						    		<a class="btn btn-default remove-selected">移除所选</a><br>
						    		<a class="btn btn-default remove-all">移除全部</a><br>
					    		</div>
			   			</div>
			   			<div  class="col-sm-5">
			   				<div class="list-group ">
							  <div  class="list-group-item active">
							    	未选择角色
							  </div>
							   <div class="roles unselected-roles">
										<ul></ul>
						       </div>
							</div>
			   			</div>
			  </div>
			   <div class="modal-footer">
			        <button type="button" id="btn_cancel"  class="btn btn-default" data-dismiss="modal">取消</button>&nbsp;
			       &nbsp;&nbsp; <button id="btn_save"  type="submit" class="btn btn-primary" >保存</button>
		      </div>
		</form>
</div>  
</body>
</html>
