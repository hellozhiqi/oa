<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单管理</title>
	<!-- - 所有放到static、public、resources里面的文件，都是在根目录的 -->
	<link rel="stylesheet" href="${ ctx}/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="/css/menu/menu.css">
	<script type="text/javascript" src="${ctx}/zTree/js/jquery.ztree.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/menu/menu.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/zTree-menu.js"></script>
</head>
<body>
<div class="container">
	<div class="panel panel-default ">
		  <div class="panel-heading  title-color"> 菜单管理 
		  </div>
		  <div class="panel-body">
		  	<!-- ztree -->
			  <div class="col-xs-12 col-sm-4">
					<ul id="treeDemo" class="ztree"></ul>
			 </div>
			 <div class="col-xs-12 col-sm-8 form-container">
			 	<form class="form-horizontal" method="post" 
       	 		enctype="multipart/form-data">
       	 		<!-- 修改时,需要使用  id="module_edit_id" -->
		       	 	<input  name="id"  id="id" value="${user.id}" type="hidden">
					  <div class="col-xm-12">
					  	  <div class="form-group">
							    <label for="inputName" class="col-sm-2">上级菜单</label>
							    <div class="col-sm-6">
							   		  <span id="parentName"></span>
							   		  <input id="parentId" type="hidden" name="parent.id">
							    </div>
						  </div>
					  </div>
					  <div class="col-xm-12">
						   <div class="form-group">
							    <label for="name" class="col-sm-2">菜单名称</label>
							    <div class="col-sm-6">
							       <input type="text" 
							       class="form-control" 
							       name="name"
							       id="inputName"
							       placeholder="菜单">
							    </div>
						    </div>
					  </div>
					   <input type="hidden"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/>
					   <div class="col-xm-12">
						   <div class="form-group  url">
							    <label for="url" class="col-sm-2">URL</label>
							    <div class="col-sm-6">
							       <input type="text" 
							       class="form-control" 
							       name="url"
							       id="inputURL"
							       placeholder="访问菜单时的URL">
							    </div>
						    </div>
					  </div>
					  <div class="col-xm-12">
						<div class="form-group">
						    <label for="selectType" class="col-sm-2">类型</label>
						    <div class="col-sm-8">
						        <div class="radio">
									<label>
										<input type="radio" name="type" value="LINK"/>
										链接类型的菜单，用于显示出来给用户点击使用的
									</label>
								</div>
						        <div class="radio">
									<label>
										<input type="radio" name="type" value="BUTTON"/>
										用于在主页面中，作为用户是否有权限操作此按钮
									</label>
								</div>
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
												<ul>
													<c:forEach items="${roles}" var="role">
														<li>
															<label class="list-group-item">
																	<input type="checkbox" name="roles[0]" value="${role.id}">
																	${role.roleName}
															</label>
														</li>
													</c:forEach>
												</ul>
								       </div>
									</div>
					   			</div>
					  </div>
					   <div class="modal-footer">
					        <button type="button" id="btn_cancel"  class="btn btn-default" data-dismiss="modal">取消</button>&nbsp;
					       &nbsp;&nbsp; <button id="btn_save"  type="submit" class="btn" >保存</button>
				      </div>
				</form>
			 </div>
		  </div>
	</div>
</div>
</body>
</html>