<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib prefix="fk" tagdir="/WEB-INF/tags"%>
    <%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告管理</title> 
<link rel="stylesheet" href="/css/type.css">
<script type="text/javascript" src="${ctx }/js/type_index.js"></script>
</head>
<body>
<div class="container">
	<div class="panel panel-default ">
		  <div class="panel-heading  title-color"> 公告管理
		  		<!-- 添加按钮 -->
			  	<div  class="btn-add">
			  		<a  href="${ctx }/notice/add">
			 			添加<span>
			 				<img class="add-img" alt="" src="${ctx}/images/add.png">
			 			</span>
			 		</a>
				</div>
		  </div>
		 <div class="panel-body">
		 <table class="table">
		 	<thead>
		 		<th class="col-sm-5">标题</th>
		 		<th class="col-sm-1">作者</th>
		 		<th class="col-sm-2">撰写时间</th>
		 		<th class="col-sm-1">状态</th>
		 		<th class="col-sm-2">操作</th>
		 	</thead>
		 	<tbody>
			<c:forEach items="${page.content}" var="rr">
				<tr class="${(rr.notice.status eq 'RELEASE' and empty rr.readTime) ? 'unread':''}">
					<div>
						<td class="col-sm-5"><span>${rr.notice.title }</span></td>
						<td  class="col-sm-1"><span>${rr.notice.author.name }</span></td>
						<td  class="col-sm-2"><span><fmt:formatDate value="${rr.notice.writeTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
						<td  class="col-sm-1">
							<c:choose>
								<c:when test="${rr.notice.status eq 'DRAFT' }">
									<span>草稿</span>
								</c:when>
								<c:when test="${rr.notice.status eq 'RECALL'}">
									<span>撤回</span>
								</c:when>
								<c:when test="${rr.notice.status eq 'RELEASE'}">
									<span>已发布</span>
								</c:when>
							</c:choose>
						</td>
						<td  class="col-sm-2">
								<%--  	处于草稿状态,仅作者可见可编辑、发布 --%>
								<c:if test="${rr.notice.status eq 'DRAFT' }">
									<a href="javascript:deleteNotice('${rr.notice.id}');" class="btn btn-danger btn-xs glyphicon glyphicon-trash">删除</a>
									<a href="javascript:editNotice('${rr.notice.id}');;" class="btn btn-default btn-xs glyphicon glyphicon-edit">编辑</a>
									<a href="javascript:publishNotice('${rr.notice.id}');;" class="btn btn-primary btn-xs  glyphicon glyphicon-send">发布</a>
								</c:if>
								<%-- 处于已发布,其他用户能阅读 --%> 
								<c:if test="${rr.notice.status eq 'RELEASE'}">
										<%-- -- 作者可撤回,删除 --%>   
										<c:if test="${rr.notice.author.id eq sessionScope['SPRING_SECURITY_CONTEXT'].authentication.principal.id }">
											<a href="javascript:recallNotice('${rr.notice.id}');;" class="btn btn-warning btn-xs glyphicon glyphicon-log-in">撤回</a>
										</c:if>
										<a href="javascript:readedNotice('${rr.notice.id}');" class="btn btn-info btn-xs glyphicon glyphicon-info-sign"> 阅读</a>
								</c:if>
						</td>
					</div>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
		  			<tr>
		  				<!-- 分页处理 -->
		  				<td colspan="5"  style="text-align:center; ">
		  					<fk:page  url="/notice?keyword=${param.keyword}"  page="${page }" />
		  				</td>
		  			</tr>
		  </tfoot>
		</table>
		</div>
	</div>
</div>
<script type="text/javascript">
//删除
var deleteNotice=function(id){
	
	$.ajax({
		url:"/notice/delete/"+id,
		method:"delete",
		success:function(){
			//加载页面
			location.reload();
		}
	});
}
//编辑
var editNotice=function(id){
	window.location.href="/notice/edit/"+id;
}
//发布
var publishNotice=function(id){
	window.location.href="/notice/publish/"+id;
}
//撤回
var recallNotice=function(id){
	window.location.href="/notice/recall/"+id;
}
//阅读
var readedNotice=function(id){
	window.location.href="/notice/read/"+id;
}
</script>
</body>
</html>
