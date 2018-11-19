<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告</title>
</head>
<body>
	<div class="container-fluid">
	<div class="panel panel-default">
		<div class="panel-heading">
			查看公告
		</div>
		<div class="panel-body">
			<h1>${notice.title }</h1>
			作者:${notice.author.name } 发布时间:<fmt:formatDate value="${notice.releaseTime}" pattern="yyyy-MM-dd HH:mm:sss"/>
			<hr>
			${notice.content}
		</div>
		<div class="panel-footer" style="float: right;">
			<a class="btn btn-primary" href="javascript:readedNotice('${notice.id}');">已阅</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var readedNotice=function(id){
		/**
		 * 解决AJAX访问的时候CSRF验证的问题
		 */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		// 在所有的AJAX请求发送之前，统一设置请求头
		// 这种方式设置的，只能处理通过jQuery发送的AJAX请求
		if (token && header) {
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
		}
		
		$.ajax({
			url:"/notice/readed/"+id,
			type:"post",
			dataType: "JSON",
			success:function(){
				//重定向
				window.location.href="/notice";
			}
		})
	}
</script>
</body>
</html>