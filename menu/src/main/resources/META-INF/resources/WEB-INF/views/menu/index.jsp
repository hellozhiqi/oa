<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <c:set value="${pageContext.request.contextPath}" var="ctx" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单管理</title>
<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css">
	<!-- - 所有放到static、public、resources里面的文件，都是在根目录的 -->
	<link rel="stylesheet" href="/zTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/zTree/js/jquery.ztree.all.min.js"></script>
	
</head>
<body>
<!-- ztree -->
			  <div class="col-xs-12 col-sm-4 tree-container">
					<div class="zTreeDemoBackground left">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
			 </div>
</body>
<script type="text/javascript">

var addHoverDom=function(treeId, treeNode) {
	
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加菜单' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		//zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
		return false;
	});
};
var setting = {
		async: {
			// 激活异步请求
			enable: true,
			// 异步请求的URL，默认POST方式发送请求
			url: "${ctx}/menu",
			// 使用GET方式发送请求
			type: "GET",
			// 要求返回JSON，数据类型参考jQuery的dataType
			dataType: "JSON"
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		enable: true,
		showRenameBtn:false
	}
};
var removeHoverDom=function(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
});

</script>
</html>