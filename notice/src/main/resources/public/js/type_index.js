var chckedTypeName=function chckedTypeName(obj){

		if(obj!=""){
			$.ajax({
				  type: "post",
				  url: "/notice/checked",
				  data: $(".form-horizontal").serialize(),
				  success:function(msg){
				    /**边框样式*/
					$('#noticeTypeName').toggleClass("LoginNameSuccess",msg.status==1);
					$('#noticeTypeName').toggleClass("LoginNameError",msg.status==2);
				 
					/** 对错图标颜色的样式*/
					if(msg.status==2){
						$("#noticeTypeTig").html("<font color='red' style='font-size:16px'>类型名称已被占用!</font>");
						//禁用保存按钮
						$("#btn_save").prop("disabled","disabled");//禁用按钮
					}  else{
						$("#noticeTypeTig").text(" ");
						//恢复按钮
						$("#btn_save").prop("disabled","");//禁用按钮
					} 
				  },error:function(msg){
					  alert("网速不达,请检查网络!");
				  }
				});
		}else{
			$('#loginName_icon').addClass("badge-icon");
		}
	}
$(function(){
	
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
		
		$(".list-group-item").click(function(){
			var data=$(this);
			var id=data.attr("data-id");
			var name=data.attr("data-name");
			
			$("#noticeTypeId").val(id);
			$("#noticeTypeName").val(name);
			
			//检查类型名称是否唯一
			chckedTypeName(name);
		});
		
		$(".role_x").hover(function(){
			var span = $("span[title='x']", this);
			// 事件发生的时候，会删除hide类
			// 鼠标移开以后，会自动重新加上hide类
			span.toggleClass("hide");
		});
		
		$(".remove_btn").click(function(event){
			//组织时间传播
			event.stopPropagation();
			var div = $(this).parent();
			var id = div.attr("data-id");
			var url="/notice/"+id;
			
			// 发送DELETE请求删除数据
			$.ajax({
				url:url,
				method:'DELETE',
				success:function(data,status,xhr){
					document.location.href="/notice";
				},
				error:function(data,status,xhr){
					// responseJSON表示返回的JSON对象
					// message是错误信息
					alert(data.responseJSON.message);
				}
			});
		});
	})