<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>上传</title>
</head>
<body>
		<h3>文件上传</h3>
		<div>
			<form action="/docs/upload" enctype="multipart/form-data" method="post">
				<input type="file" name="file"/>
				 <input type="hidden"
								name="${_csrf.parameterName}"
								value="${_csrf.token}"/>
				<button type="submit">上传</button>
			</form>
		</div>
</body>
</html>