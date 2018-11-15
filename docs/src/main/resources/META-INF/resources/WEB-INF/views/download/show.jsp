<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
   <%@ taglib prefix="fk" tagdir="/WEB-INF/tags"%>
<c:set  value="${pageContext.request.contextPath}" var="ctx"/>
<html>
<head>
<link rel="stylesheet" href="${ctx}/css/identity/user.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载</title>
</head>
<body>
	<div class="container">
		<div class="panel-heading  title-color">	<span class="download-text">下载专区 </span>
		  		<!-- 添加按钮 -->
	  			<a class="btn-add" data-toggle="modal" data-target=".file-modal-dialog"> 
	 			添加 
	 				<img class="add-img" alt="" src="${ctx}/images/add.png">
	 			</a>
		  </div>
		<table  class="table  table-hover">
			<thead>
				<tr>
					<th  class="col-sm-4">文件名</th>
					<th class="col-sm-2">文件大小</th>
					<th class="col-sm-2">文件类型</th>
					<th class="col-sm-1">贡献者</th>
					<th class="col-sm-2">上传时间</th>
					<th class="col-sm-1">操作</th>
				</tr>
			</thead>	
			<tbody>
				<c:forEach items="${page.content}" var="fi">
					<tr>
						<td class="col-sm-2">${fi.name}</td>
						<td class="col-sm-2">
							<fmt:formatNumber pattern="#.##"> ${(fi.fileSize)/1024/1024}</fmt:formatNumber>MB
						</td >
						<td class="col-sm-2">${fi.contentType}</td>
						<td class="col-sm-1">${fi.user.loginName}</td>
						<td class="col-sm-2">
							<fmt:formatDate value="${fi.uploadTime}" pattern="yyyy-MM-dd"/>
						</td>
						<%-- 文件的id，是一个参数，根据此参数到数据库找到文件信息，文件信息里面会包含文件的存储名称/路径 --%>
						<%-- 此参数，竟然不是问号传的！而是一个/，这种就是传说中的RESTful风格WEB服务器 --%>
						<%-- REST约定：每个资源有个唯一的URL --%>
						<%-- /storage/ 表示所有文件 --%>
						<%-- /storage/id 表示id对应的文件 --%>
						<%-- <td><a href="${ctx}/storage/show/${fi.id}">下载</a></td> --%>
						<td class="col-sm-1"><a href="${ctx}/docs/download/${fi.id}">下载</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
	  			<tr>
	  				<!-- 分页处理 -->
	  				<td colspan="6"  style="text-align:center; ">
	  					<fk:page  url="/docs/show"  page="${page}" />
	  				</td>
	  			</tr>
		  		</tfoot>
		</table>
	</div>
	<!-- 模态框：文件上传 -->
		<div class="modal fade  file-modal-dialog" tabindex="-1" role="dialog">
		  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">文件上传</h4>
			      </div>
			      <div class="modal-body">
			        	<form action="/docs/upload" enctype="multipart/form-data" method="post">
			        	 	<p>请选择要上传的文件，文件大小不能超过2G。</p>
							<input type="file" name="file" required="required"/>
							 <p class="help-block">自己上传的文件，只有自己能够看到。</p>
							 <input type="hidden"
											name="${_csrf.parameterName}"
											value="${_csrf.token}"/>
							 <div class="modal-footer">
						        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						        <button type="submit" class="btn btn-primary">保存</button>
					      </div>
						</form>
			      </div>
			    </div> 
		  </div> 
		</div> 
		<!-- 模态框结束 -->
</body>
</html>