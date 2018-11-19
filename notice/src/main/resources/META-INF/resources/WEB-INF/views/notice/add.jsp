<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑公告</title>
<link rel="stylesheet" href="/css/edit.css">
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-default">
		<div class="panel-heading">
			编辑公告
		</div>
		<div class="panel-body">
			<form action="/notice/send" class="form-edit" method="post">
			 	<input type="hidden"name="${_csrf.parameterName}"value="${_csrf.token}"/>
			 	
				<input type="hidden" name="id" value="${notice.id}"/>
				<!-- 标题 -->
				<label for="title">主题</label>
				<input type="text" name="title" class="titleText" required="required" value="${notice.title}"><br>
				<label for="title">类型</label>
				<select  class="titleType" name="type.id">
					<option value="">==请选择==</option>
					<c:forEach items="${types }" var="type">
						<option value="${type.id }" ${notice.type.id eq type.id ? 'selected="selected" ' :' '}>
							${type.name }
						</option>
					</c:forEach>
				</select>
				<br>
				<!-- 编辑的内容 -->
				<label for="text">正文</label>
				<div id="noticeContentEditor">${notice.content }</div>
				<textarea   type="hidden" name="content" id="noticeContent"  style="display: none;">${notice.content }</textarea>
				<div id="toolbar" class="clear">
					<div class="toolbg toolbgline">
						<a name="sendbtn" tabindex="5"  class="btn btn-default"  href="javascript:publish();">发布</a>
						<a name="timeSendbtn" tabindex="6" class="btn btn-default" hidefocus="" href="javascript:;" >定时发布</a>
						<a name="savebtn" title="存草稿后，您可以在草稿箱中找回" tabindex="7" class="btn btn-default"   href="javascript:toDraft();">存草稿</a>
						<a name="closebtn" tabindex="8" class="btn btn-default" href="javascript:close();">关闭</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
 <script type="text/javascript" src="${ctx }/webjars/wangEditor/3.1.1/release/wangEditor.min.js"></script>
 <script type="text/javascript">
 
       $(function(){
    	   var E = window.wangEditor;
           var editor = new E('#noticeContentEditor');
           //接收改变的内容更新到  noticeContent里面
           editor.customConfig.onchange = function (html) {
               // 监控变化，同步更新到 textarea
              $("#noticeContent").val(html);
           };
           // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
           editor.customConfig.uploadImgServer = '${ctx}/docs/upload/wangEditor' ; // 上传图片到服务器
           // 上传的时候，文件的字段名
           editor.customConfig.uploadFileName = 'file';

           editor.customConfig.uploadImgHeaders = {
              	    '${_csrf.headerName}': '${_csrf.token}'
           };
           //粘贴的样式
           editor.customConfig.pasteFilterStyle = false;
          // 是否忽略粘贴内容中的图片
           editor.customConfig.pasteIgnoreImg = false;
           editor.create();
       })
        //发送公告
        function publish(){
    	  var content= $("#noticeContent").val().trim();
    	  if(content===""){
    		  alert("正文内容不能为空!");
    	  }else{
    		  //手动提交表单
    		  $(".form-edit").submit();
    	  }
        }
       // TODO  定时发布
       //存草稿 toDraft()
       function toDraft(){
    	  var content= $("#noticeContent").val().trim();
    	  if(content===""){
    		  alert("正文内容不能为空!");
    	  }else{
    		  //手动提交表单
    		  $(".form-edit").prop("action","/notice/toDraft");
    		  $(".form-edit").submit();
    	  }
        }
       //关闭,跳转到公告列表
       function close(){
    	   window.location.href="/notice";
       }
    </script>
</body>
</html>