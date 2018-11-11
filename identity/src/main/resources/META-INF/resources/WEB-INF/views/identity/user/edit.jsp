<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑用户信息</title>
<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/css/easy-ui/easyui.css">
<link rel="stylesheet" href="${ctx}/css/identity/user.css">
<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx }/js/easy-ui/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/js/identity/user_index.js"></script>
</head>
<body>
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