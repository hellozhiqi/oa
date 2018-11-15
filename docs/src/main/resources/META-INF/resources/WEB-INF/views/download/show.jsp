<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
   <%@ taglib prefix="fk" tagdir="/WEB-INF/tags"%>
<c:set  value="${pageContext.request.contextPath}" var="ctx"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载</title>
</head>
<body>
	<h3>下载专区</h3>
	<div class="container">
		<table  class="table  table-hover">
			<thead>
				<tr>
					<th  class="col-sm-2">文件名</th>
					<th class="col-sm-2">文件大小</th>
					<th class="col-sm-2">文件类型</th>
					<th class="col-sm-2">贡献者</th>
					<th class="col-sm-2">上传时间</th>
					<th class="col-sm-2">操作</th>
				</tr>
			</thead>	
			<tbody>
				<c:forEach items="${page.content}" var="fi">
					<tr>
						<td>${fi.name}</td>
						<td>
							<fmt:formatNumber pattern="#.##"> ${(fi.fileSize)/1024/1024}</fmt:formatNumber>MB
						</td>
						<td>${fi.contentType}</td>
						<td>${fi.user.loginName}</td>
						<td>
							<fmt:formatDate value="${fi.uploadTime}" pattern="yyyy-MM-dd"/>
						</td>
						<%-- 文件的id，是一个参数，根据此参数到数据库找到文件信息，文件信息里面会包含文件的存储名称/路径 --%>
						<%-- 此参数，竟然不是问号传的！而是一个/，这种就是传说中的RESTful风格WEB服务器 --%>
						<%-- REST约定：每个资源有个唯一的URL --%>
						<%-- /storage/ 表示所有文件 --%>
						<%-- /storage/id 表示id对应的文件 --%>
						<%-- <td><a href="${ctx}/storage/show/${fi.id}">下载</a></td> --%>
						<td><a href="${ctx}/docs/download/${fi.id}">下载</a></td>
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
</body>
</html>