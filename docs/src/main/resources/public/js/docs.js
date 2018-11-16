function deleteFile(id){

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
 		url: "/docs/delete/"+id,
 		method: "DELETE",
 		success: function(){
 			// 重新加载页面
 			window.location.reload();
 			//window.location.href = "${ctx}/storage/file";
 			// 还可以根据id删除一行记录
 		}
 	});
}