<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部门管理</title>
	<!-- - 所有放到static、public、resources里面的文件，都是在根目录的 -->
	<link rel="stylesheet" href="${ ctx}/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${ctx }/static/css/bootstrap-selected.css">
	<link rel="stylesheet" href="${ctx }/static/css/hr.css">
	<link rel="stylesheet" href="${ctx }/css/commons.css">
	<link rel="stylesheet" href="${ctx }/static/css/bootstrap-select.min.css">
	
	<script src="${ctx }/static/js/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="${ctx}/zTree/js/jquery.ztree.all.min.js"></script>
</head>
<body>
<div class="container">
	<div class="panel panel-default ">
		  <div class="panel-heading  title-color"> 部门管理 
		  </div>
		  <div class="panel-body">
		  	<!-- ztree -->
			  <div class="col-xs-12 col-sm-4">
					<ul id="treeDemo" class="ztree"></ul>
			 </div>
			 <div class="col-xs-12 col-sm-8 form-container">
			 	<form class="form-horizontal" method="post" 
       	 		enctype="multipart/form-data">
       	 		  <input type="hidden"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/>
						
       	 		<!-- 修改时,需要使用  id="module_edit_id" -->
		       	 	<input  name="id"  id="id" value="${user.id}" type="hidden">
					  <div class="col-xm-12">
					  	  <div class="form-group">
							    <label for="inputName" class="col-sm-2">上级部门</label>
							    <div class="col-sm-6">
							   		  <span id="parentName"></span>
							   		  <input id="parentId" type="hidden" name="parent.id">
							    </div>
						  </div>
					  </div>
					  <div class="col-xm-12">
						   <div class="form-group">
							    <label for="name" class="col-sm-2">部门名称</label>
							    <div class="col-sm-6">
							       <input type="text" 
							       class="form-control" 
							       name="name"
							       id="inputName"
							       required="required"
							       placeholder="部门名称">
							    </div>
						    </div>
					  </div>
					  <div class="col-xm-12">
						   <div class="form-group">
							    <label for="name" class="col-sm-2">部门经理</label>
							  	<%-- <select name="manager.user.id" required="required" class="form-control select-width">
							  		<option value="">-- 请选择姓名 --</option>
							  		<c:forEach items="${page.content }" var="user">
							  			<option value="${user.id}">${user.name }</option>
							  		</c:forEach>
							  	</select> --%>
							  	<input type="text"   class="form-control select-width  selectpicker show-tick"  id="selectedManager">
							  	<input type="hidden" name="manager.user.id" id="managerId"  required="required"/>
						</div>
					  </div>
					   <div class="col-xm-12">
						   <div class="form-group">
							    <label for="name" class="col-sm-2">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话</label>
							    <div class="col-sm-6">
							       <input type="text" 
							       class="form-control" 
							       name="telephone"
							       id="inputTelephone"
							        required="required"
							       placeholder="部门电话">
							    </div>
						    </div>
					  </div>
					   <div class="col-xm-12">
						   <div class="form-group">
							    <label for="name" class="col-sm-2">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真</label>
							    <div class="col-sm-6">
							       <input type="text" 
							       class="form-control" 
							       name="fax"
							       id="inputFax"
							        required="required"
							       placeholder="部门传真">
							    </div>
						    </div>
					  </div>
					  <div class="col-xm-12">
						   <div class="form-group">
							    <label for="name" class="col-sm-2">部门职能</label>
							    <div class="col-sm-6">
							    <textarea
								    rows="5" 
								    cols="20"   
								    name="fun" 
								    class="form-control" 
								    id="inputFun"  
								    required="required"
								     placeholder="部门职能">
							    
							    </textarea>
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
<script type="text/javascript" src="${ctx }/webjars/devbridge-autocomplete/1.4.8/dist/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/zTree-dept.js"></script>
</body>
</html>